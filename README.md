[![Build CDI Test](https://github.com/guhilling/cdi-test/actions/workflows/maven.yml/badge.svg)](https://github.com/guhilling/cdi-test/actions/workflows/maven.yml)
[![CodeQL](https://github.com/guhilling/cdi-test/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/guhilling/cdi-test/actions/workflows/codeql-analysis.yml)
[![Coverage (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=de.hilling.junit.cdi%3Acdi-test&metric=coverage)](https://sonarcloud.io/dashboard?id=de.hilling.junit.cdi%3Acdi-test)
[![Status (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=de.hilling.junit.cdi%3Acdi-test&metric=alert_status)](https://sonarcloud.io/dashboard?id=de.hilling.junit.cdi%3Acdi-test)
[![Maintainability (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=de.hilling.junit.cdi%3Acdi-test&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=de.hilling.junit.cdi%3Acdi-test)
[![Maven Central](https://img.shields.io/maven-central/v/de.hilling.junit.cdi/cdi-test.svg)](http://search.maven.org/#search|gav|1|g:"de.hilling.junit.cdi"%20AND%20a:"cdi-test")

# cdi-test: A JUnit extension for easy and efficient testing of CDI components

cdi-test is available under the [The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)

## Why should you use it?

If you're developing  [Jakarta EE (Java EE)](https://jakarta.ee) or just plain [CDI](http://weld.cdi-spec.org)
applications or libraries you'll probably want to unit test your code. If you don't you _really_ should.
With cdi-test there's no excuse not to do it ;-)

As CDI doesn't come with any "standard" unit test capabilities you need some way to test your components in your
[JUnit 5](https://junit.org/junit5/) tests. This is not easily accomplished especially when compared with the junit
integration of [Spring Boot](https://spring.io/projects/spring-boot).

## Highlights

cdi-test is targeted at running unit, component and integration tests at scale. It accomplishes this with:
- Only booting the cdi container once for all unit tests. This allows for running a huge number of tests even 
  in big projects where booting might take some time.
- cdi-test uses [Weld (the cdi reference implementation)](http://weld.cdi-spec.org) as cdi container. So you can 
  use the exact same cdi container as in your application runtime in case you're running e.g.
  [Wildfly](https://www.wildfly.org),
  [JBoss EAP](https://www.redhat.com/en/technologies/jboss-middleware/application-platform),
  [GlassFish](https://javaee.github.io/glassfish/) or
  [Oracle WebLogic](https://www.oracle.com/middleware/technologies/weblogic.html).
- cdi-test supports mocks and test alternatives for CDI beans. These can be activated per test class. So you can 
  freely choose what you want to test and need to mock test-by-test.
- Well tested and maintained and used in real projects with thousands of unit tests.


## Compatibility

- CDI 2 and CDI 3 with the relevant releases of [Weld](http://weld.cdi-spec.org)
- Java 8+
- [Jakarta EE 8](https://jakarta.ee/specifications/platform/8/)
  and [Jakarta EE 9](https://jakarta.ee/release/9/) (The new namespace introduced with Jakarta EE 9
  will be supported starting with release 4.x. Pre-releases are already available.)
  - Includes using EJBs with some restrictions. Injection and creation of ``@Stateful`` and ``@Stateless`` ejbs.
  - Supports JPA even with multiple persistence units. Inject EntityManager
   via ``@Inject`` or ``@PersistenceContext``.
- [JUnit 5](https://junit.org/junit5/) (starting with cdi-test 3.x)
- [Microprofile Config](https://github.com/eclipse/microprofile-config) 2.x and 3.x
  - Supports injecting test specific properties using annotations.
- [Mockito](https://site.mockito.org) is supported and used internally.

## Short example, please!
Most basic:

https://github.com/guhilling/cdi-test/blob/36a3302fa4cae9e66aa0c6fea369a2e92efba70c/cdi-test-core/src/test/java/de/hilling/junit/cdi/SimpleTest.java#L11-L22

Using mocks:

https://github.com/guhilling/cdi-test/blob/36a3302fa4cae9e66aa0c6fea369a2e92efba70c/cdi-test-core/src/test/java/de/hilling/junit/cdi/MockProxyTest.java#L16-L30

# In-depth Documentation
See the [Documentation on github pages](https://cdi-test.hilling.de).

# Contributing
[Contribution guidelines for this project](CONTRIBUTING.md)

# LICENSE
 Copyright 2015 Gunnar Hilling

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

# Sponsors

JProfiler supports open source projects with its full-featured Java Profiler. Click the JProfiler logo below to learn more.

<a href="https://www.ej-technologies.com/products/jprofiler/overview.html" target="_blank" title="JProfiler">
  <img src="https://www.ej-technologies.com/images/product_banners/jprofiler_large.png" alt="JProfiler">
</a>
<a href="https://www.jetbrains.com/?from=cdi-test" target="_blank" title="JetBrains">
  <img src=".logos/jetbrains-variant-2.svg" alt="JetBrains Logo">
</a>
