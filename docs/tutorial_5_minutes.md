## Usage

I'm assuming you are using maven 3.0.5 for this tutorial as I am using it for my integration testing.

Furthermore a jdk >= 9 should be used. If in doubt check the testing matrix on
[travis](https://travis-ci.org/guhilling/cdi-test).


### Dependencies

Define dependencies on basic cdi-test features:

```xml
<dependencies>
   [...] 
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.3.1</version>
        <scope>test</scope>
    </dependency>
  [...] 
    <dependency>
        <groupId>de.hilling.junit.cdi</groupId>
        <artifactId>cdi-test-core</artifactId>
        <version>2.0.0</version>
        <scope>test</scope>
    </dependency>
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
</dependencies>
```

Again check [travis](https://travis-ci.org/guhilling/cdi-test) for tested combintations of weld and jdk.

### Writing Tests

In the following example, `ApplicationBean` will automatically be replaced by a mockito mock in all cdi
beans when _this test is run_, see the full example in the code for details.

It is possible to select different mockito or test implementations in each test class (see the full documentation for details).

```java
@ExtendWith(CdiTestJunitExtension.class)
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

* More extensions:
    * dbunit
    * test data generator
* switchable Mock-Providers

