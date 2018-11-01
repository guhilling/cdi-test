# cdi-test

[![Build Status](https://travis-ci.org/guhilling/cdi-test.svg?branch=master)](https://travis-ci.org/guhilling/cdi-test)
[![Coverage (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=de.hilling.junit.cdi%3Acdi-test&metric=coverage)](https://sonarcloud.io/dashboard?id=de.hilling.junit.cdi%3Acdi-test)
[![Status (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=de.hilling.junit.cdi%3Acdi-test&metric=alert_status)](https://sonarcloud.io/dashboard?id=de.hilling.junit.cdi%3Acdi-test)
[![Maintainability (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=de.hilling.junit.cdi%3Acdi-test&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=de.hilling.junit.cdi%3Acdi-test)
[![Maven Central](https://img.shields.io/maven-central/v/de.hilling.junit.cdi/cdi-test.svg)](http://search.maven.org/#search|gav|1|g:"de.hilling.junit.cdi"%20AND%20a:"cdi-test")

cdi-test is a junit 5 extension for unit testing cdi or jee projects.

cdi-test can use different versions of [Weld](http://weld.cdi-spec.org) for the tests, so you should
be able to use the same cdi implementation in your tests as in your production container.

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

When testing projects that are based on cdi there are different approaches available. I'll discuss this topic
briefly to give you an idea what this library is all about.

### Run the tests in a production container

Nowadays as we package everything as a docker container it's possible to setup integration testing solutions using docker and
[Testcontainers](https://www.testcontainers.org) without too much fuss.

However this approach is not well suited for unit tests.

### Run the tests in a production-like container

This approach is taken by the [Arquillian](http://arquillian.org) framework. The advantages are:
* The application can be tested in the container.
* REST-endpoints can be tested via https. The same is possible for any remoting calls.
* Furthermore arquillian can be used to run tests from _inside the test container_, which makes it quite unique afaik.

However this approach also hase some disadvantages:
* Every test run requires an archive to be built and deployed which makes the testing slower.
* Creating the deployments, especially resolving the required artifacts and classes, is not trivial.
* Setup tends to be complicated.

### Run unit and module tests in a test container

This is what this library does and it is all about running light-weight tests with easy to define boundaries.
I want my unit and module tests to start quick (startup/creation time) and to run fast even if there are hundreds of 
tests in my project.
Indeed this is what this library was developed for in the first place.

Why is there no testing support readily available with cdi as there is with spring? Well, hm ... Actually because
spring is a product (one implementation) and this product has junit test support. cdi is just a standard in the first
place and as it is an api it doesn't define any testing hooks. Maybe it should require them.

[Apache DeltaSpike](https://deltaspike.apache.org) has a similar test runner as cdi-test but it's - at least imho - 
not quite the same and not as easy to use and extend as cdi-test.

### Conclusion

cdi-test shouldn't do everything and it's not the right tool if you want to create integration tests. In this case take
a look at [Arquillian](http://arquillian.org) or [Testcontainers](https://www.testcontainers.org).

If you want to unit test your cdi application read on! I'll refer to the "test tests" (sorry ...) contained in the
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
* You need an actual cdi implementation for your tests. There is no predefined implementation of cdi and cdi-test 
should work with any cdi-1.2 or 2.0 compliant implementation. However I'm only using [Weld](http://weld.cdi-spec.org)
for my integration tests at the moment.
* As cdi-test uses [Apache DeltaSpike](https://deltaspike.apache.org) to boot and control the actual cdi-container you need a matching deltaspike-cdictrl 
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

First the (not so)obvious: Don't forget to include a ``beans.xml`` for your tests or cdi won't find any of your testing 
components. However the test class itself is not a cdi bean but is created by junit. This is different from the version
1.x of cdi-test when the test case was created using cdi.

The junit engine is extended with ``CdiTestJunitExtension``. The extension takes care of injecting cdi beans into
the test. Again: The test class itself _is not a cdi bean_, so it is not possible to use any cdi feature like 
creating an event listener in it.

In the example below we let the extension resolve and inject the ``SampleService`` which is under test, into the test.

```java
@ExtendWith(CdiTestJunitExtension.class)
public class SampleServiceTest {

    @Inject
    private SampleService sampleService;

    @Test
    public void createPerson() {
        Person person = new Person();
        sampleService.storePerson(person);
    }
}
```

The Service is resolved by the cdi implementation as usual. In the above test no cdi-test magic is done.

Well ... there is one thing: All standard scopes are created and destroyed just before any single test that is run.
This way it is possible to run the tests with decent performance and have them isolated from each other anyway.

The only beans that survive the test are the special ``@TestScoped`` and ``@TestSuiteScoped`` beans. These are used in
cdi-test internally but you are certainly free to use them in your test support classes.

## Mocking beans

Taking a lot at the first example we might be tempted to look at ``SampleService`` closer:

```java
public class SampleService {

    @Inject
    private BackendService backendService;

    public void storePerson(Person person) {
        backendService.storePerson(person);
    }
}
```

Maybe we need to mock the ``BackendService`` for our unit test. This is easily done by just creating the required
mock object in the test class:

```java
@ExtendWith(CdiTestJunitExtension.class)
public class MockProxyTest {

    @Mock
    private BackendService backendService;

    @Inject
    private SampleService sampleService;

    @Test
    public void createPerson() {
        Person person = new Person();
        sampleService.storePerson(person);
        verify(backendService).storePerson(person);
    }

    @Test
    public void doNothing() {
        verifyZeroInteractions(backendService);
    }
}
```

By just defining the ``BackendService`` as a mock using the standard mockito annotation ``@Mock`` it is automatically 
used when calling the bean of type ``BackendService``.

### How is this done?

Actually it is quite simple. During testing every bean call runs via an additional proxy object that dispatches the calls. This 
proxy is configured by the ``CdiTestJunitExtension`` that analyzes the test class for mock definitions.

So there is one fixed "method routing" defined per test class. In another module test you are free to use the actual
``BackendService`` together with ``SampleService``.

## Test implementations

Mocks are not always the easiest way to create a certain test behaviour. Maybe you want to create a special
implementation of ``BackendService`` and use this in your unit test.

This is actually easy to accomplish with cdi-test. First you annotate your test implementation as
``@ActivatableTestImplementation``:

```java
@ActivatableTestImplementation
public class BackendServiceTestImplementation extends BackendService {
[...]
```

This implementation is by default not used when "routing" the method calls. You can however enable it in your tests by
just injecting the test implementation in your test. This should be quite natural as you probably have to set up and 
verify the test implementation:

```java
@ExtendWith(CdiTestJunitExtension.class)
public class ActivateAlternativeForRegularBeanTest {
    @Inject
    private SampleService sampleService;
    @Inject
    private BackendServiceTestImplementation testBackendService;

    @Test
    public void callTestActivatedService() {
        sampleService.storePerson(new Person());
        assertEquals(1, testBackendService.getInvocations());
    }

}
```

Technically it works quite the same way as using mocks.

## Test implementations part II

If there is an ``@ActivatableTestImplementation`` there should also be something that isn't "activatable"?

Yes! If you want to override a certain service in all of your tests you can use ``@GlobalTestImplementation``. This
will _almost always_ be used, except if you override it with a mock.

## Extending cdi-test

For extensions based on cdi-test you mainly need the following classes:

* Choose the scope for the extension classes:
    * ``@TestScoped`` per test class.
    * ``@TestSuiteScoped`` is global per test run. It is actually a static scope.
* You probably want to annotate classes for extensions with ``@BypassTestInterceptor`` so they
will not be accidendtly routed to mocks or test implementations.
* Often it it necessary to keep track of tests being started and shutdown. This can be done with
cdi-events:
    * ``@TestEvent`` is fired when tests are started and finished, see ``EventType``for details.
    * The junit ``ExtensionContext`` is delivered as the object with the event.

[cdi-test-microprofile](https://github.com/guhilling/cdi-test-microprofile) should be a nice example
for a small but hopefully useful extension.

## Feedback and future development

Feedback is always welcome. Feel free to ask for extensions an support for building your own!

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
