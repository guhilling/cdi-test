package de.hilling.junit.cdi.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionsUtils {

	public static List<Field> getAllFields(Class<?> clazz) {
		List<Field> result = new ArrayList<>();
		result.addAll(Arrays.asList(clazz.getDeclaredFields()));
		Class<?> superClass = clazz.getSuperclass();
		if (!superClass.equals(Object.class)) {
			result.addAll(getAllFields(superClass));
		}
		return result;
	}

	public static Class<? extends Object> getOriginalClass(
			Class<? extends Object> clazz) {
		String canonicalName = clazz.getCanonicalName();
		if (canonicalName.contains("$")) {
			try {
				return Class.forName(canonicalName.substring(0,
						canonicalName.indexOf("$")));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("unable to find original class", e);
			}
		} else {
			return clazz;
		}
	}
}
