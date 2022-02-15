## Usage

I'm assuming you are using at least maven 3.6.x for this tutorial as I am using it for my integration testing.

Furthermore a jdk >= 8 should be used. If in doubt check the testing matrix on
[GitHub Actions](https://github.com/guhilling/cdi-test/blob/main/.github/workflows/maven.yml).


### Dependencies

Define dependencies on basic cdi-test features as in the the integration-tests [pom.xml](../integration-tests/pom.xml):

https://github.com/guhilling/cdi-test/blob/82e6e4c8df5a952798c9f4e91558baec473ecbc9/integration-tests/pom.xml#L24-L45

### Writing Tests

In the following example, `ApplicationBean` will automatically be replaced by a mockito mock in all cdi
beans when _this test is run_, see the full example in the code for details.

It is possible to select different mockito or test implementations in each test class (see the full documentation for details).

```java
@ExtendWith(CdiTestJunitExtension.class)
public class RequestScopeMockTest {

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

 Copyright 2019 Gunnar Hilling

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
