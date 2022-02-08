package de.hilling.junit.cdi.junit;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import de.hilling.junit.cdi.scope.TestScoped;
import de.hilling.junit.cdi.util.ReflectionsUtils;

public class CdiTestExecutionLister implements TestExecutionListener {

    private static final Logger LOG = Logger.getLogger(CdiTestExecutionLister.class.getName());

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        TestIdentifier next = testPlan.getRoots().iterator().next();
        LOG.info(next.toString());
        String uniqueId = next.getUniqueId();
        Set<TestIdentifier> children = testPlan.getChildren(uniqueId);
        children.stream().forEach(this::printTestIdentifier);
    }

    private void printTestIdentifier(TestIdentifier identifier) {
        Optional<TestSource> source = identifier.getSource();
        if(source.isPresent()) {
            TestSource testSource = source.get();
            if(testSource instanceof ClassSource) {
                ClassSource classSource = (ClassSource) testSource;
                generateCompanionClass(classSource.getJavaClass());
                LOG.info("found test class: " + classSource.getClassName());
            }
        }
    }

    private void generateCompanionClass(Class<?> javaClass) {
        AnnotationLiteral<TestScoped> testScopedAnnotationLiteral = new AnnotationLiteral<TestScoped>() {
        };
        DynamicType.Builder<Object> classBuilder = new ByteBuddy()
                .subclass(Object.class)
                .annotateType(testScopedAnnotationLiteral);
        for (Field field : ReflectionsUtils.getAllFields(javaClass)) {
            if (field.isAnnotationPresent(Inject.class)) {
                classBuilder.define(field);
            }
        }
        DynamicType.Unloaded<Object> unloadedClass = classBuilder.make();
        DynamicType.Loaded<Object> companionClass = unloadedClass.load(Thread.currentThread().getContextClassLoader());
        Class<?> companionClassLoaded = companionClass.getLoaded();
        CompanionClassResolver.putCompanionClass(javaClass.getCanonicalName(), companionClassLoaded);
    }
}
