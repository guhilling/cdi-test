cdi-test [![Build Status](https://travis-ci.org/guhilling/cdi-test.svg?branch=master)](https://travis-ci.org/guhilling/cdi-test)
========

junit-addon for easy and quick testing of cdi projects.

cdi-test is available under the [The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)


## Main features:

* Plain cdi test, no classpath magic.
* Custom scopes for testing.
* Now uses interceptors for on-the-fly switching between mockito-mocks and real implementations.
* Basic jdbc support.

## Usage

### Dependencies

Use maven to pull dependency on basic features:

```xml
    <dependency>
        <groupId>de.hilling.junit.cdi</groupId>
        <artifactId>cdi-test-core</artifactId>
        <version>0.10.0</version>
    </dependency>
```

### Writing Tests

In the following example, `ApplicationBean` will automatically be replaced by a mockito mock in all cdi
beans, see the full example in the code for details.

```java
@RunWith(CdiUnitRunner.class)
public class RequestScopeMockTest extends BaseTest {

    private static final String SAMPLE = "sample";

    @Mock
    private ApplicationBean applicationBean;

    @Inject
    private RequestBean requestBean;

    @Test
    public void setAttributeTransitive() {
        requestBean.setAttribute(SAMPLE);
        verify(applicationBean).setAttribute(SAMPLE);
    }

}

```

## LICENSE

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


## TODO

* add database cleanup support.
* add jpa support.
* add ejb support at least for some features.
* allow switchable test implementations instead of mocks.