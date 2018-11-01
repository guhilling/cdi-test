# cdi-test [![Build Status](https://travis-ci.org/guhilling/cdi-test.svg?branch=master)](https://travis-ci.org/guhilling/cdi-test) [![Coverage Status](https://coveralls.io/repos/guhilling/cdi-test/badge.svg?branch=master)](https://coveralls.io/r/guhilling/cdi-test?branch=master) [![Maven Central](https://img.shields.io/maven-central/v/de.hilling.junit.cdi/cdi-test.svg)](http://search.maven.org/#search|gav|1|g:"de.hilling.junit.cdi"%20AND%20a:"cdi-test")

cdi-test is a junit 5 extension for unit testing cdi or jee projects.

cdi-test can use different versions of [Weld](http://weld.cdi-spec.org) for the tests, so you should
be able to use the same cdi implementation in your tests as in your container.

## Main features:

* Fast test execution. Even with hundreds of unit tests.
* Short test start-up time that is basically dependent on the size of your project.
* Supports junit 5. So it's easy to combine cdi-test with other junit extensions.
* Plain cdi driven test, no classpath magic.
* Uses interceptors for on-the-fly switching between mockito-mocks, test implementation and production implementations.
* Extensible to support frameworks built with cdi.
* Examples for extensions:
    * JPA/JEE extension.
    * Microprofile-Config support.

## Tutorial

A quick tutorial is [also available](tutorial_5_minutes.md). Or just read on.

## Introduction

When unit testing software that is based on cdi there are different approaches available. I'll discuss this topic
briefly to give you an idea what this library is all about.

### Run the tests in a production container

Nowadays as we package everything as a container it's possible to setup integration testing solutions using docker and
[Testcontainers](https://www.testcontainers.org) without too much fuss.

However this approach is not well suited for unit tests.

### Run the tests in a production-like container

This approach is taken by the [Arquillian](http://arquillian.org) framework. The advantages are:
* The application can be tested in the container.
* REST-endpoints can be tested via https. The same is possible for any remoting.
* Furthermore arquillian can be used to run tests from _inside the test container_.

However this approach also hase some disadvantages:
* Every test run requires an archive to be built and deployed which makes the testing slower.
* Creating the deployments, especially resolving the required artifacts and classes, is not trivial.
* Setup tends to be complicated.

### Run unit and module tests in a test container

This is what this library does and it is all about running light-weight tests with easy to define boundaries.
I want my unit and module tests to start quick (startup/creation time) and to run fast even if there are hundreds of 
tests in my project.
Indeed this is what this library was developed for in the first place.

[Apache DeltaSpike](https://deltaspike.apache.org) has a similar test runner but it's - at least imho - not quite the
same and not as easy to use and extend as cdi-test.

### Conclusion

cdi-test shouldn't do everything and it's not the right tool if you want to create integration tests. In this case take
a look at [Arquillian](http://arquillian.org) or [Testcontainers](https://www.testcontainers.org).

If you want to unit test your cdi application read on! I'll refer to the test tests (sorry ...) contained in the
cdi-test-core project. Maybe it's best to clone [cdi-test](https://github.com/guhilling/cdi-test.git) to play around
with the tests.

## Project setup

Additional to the junit 5 library you need the following dependencies:

* Obviously cdi-test:
```xml
    <dependency>
        <groupId>de.hilling.junit.cdi</groupId>
        <artifactId>cdi-test-core</artifactId>
        <version>2.0.0</version>
        <scope>test</scope>
    </dependency>
```
* And you need an actual cdi implementation for your tests. There is no predefined implementation of cdi an cdi-test 
should work with any cdi-1.2 or 2.0 compliant implementation. However I'm only using weld for my integration tests
a the moment.
* As cdi-test uses deltaspike to boot and control the actual cdi-container you need a matching deltaspike-cdictrl 
library.
```xml
    <dependency>
        <groupId>org.jboss.weld.se</groupId>
        <artifactId>weld-se-core</artifactId>
        <version>3.0.5.Final</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.apache.deltaspike.cdictrl</groupId>
        <artifactId>deltaspike-cdictrl-weld</artifactId>
        <version>1.9.0</version>
        <scope>test</scope>
    </dependency>
```
* Remember that many libraries you are using in a jee or microprofile application are provided by the runtime
environment, so you might have to pull them into the test scope manually. One example would be jpa.



## First test

First the obvious: Don't forget to include a ``beans.xml`` for your tests or cdi won't find any of your testing 
components. However the test class itself is not a cdi bean but is created by junit. This is different from the version
1.x of cdi-test when the test case was created using cdi.

```java


```

## LICENSE

 Copyright 2018 Gunnar Hilling

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
