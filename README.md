cdi-test [![Build Status](https://travis-ci.org/guhilling/cdi-test.svg?branch=master)](https://travis-ci.org/guhilling/cdi-test) [![Coverage Status](https://coveralls.io/repos/guhilling/cdi-test/badge.svg?branch=master)](https://coveralls.io/r/guhilling/cdi-test?branch=master) [![Maven Central](https://img.shields.io/maven-central/v/de.hilling.junit.cdi/cdi-test.svg)](http://search.maven.org/#search|gav|1|g:"de.hilling.junit.cdi"%20AND%20a:"cdi-test")
========

cdi-test is a junit-addon for easy and quick testing of cdi projects.

cdi-test is available under the [The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)


## Main features:

* Plain cdi test, no classpath magic.
* Custom scopes for testing.
* Uses interceptors for on-the-fly switching between mockito-mocks, test implementation and production implementations.
* Support for some ejb features to test jee application components:
    * Inject EntityManager via ``@Inject`` or ``@PersistenceContext``
    * Injection of Stateless Beans

## Usage

See the [Documentation]().

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

## Sponsors

JProfiler supports open source projects with its full-featured Java Profiler. Click the JProfiler logo below to learn more.

<a href="http://www.ej-technologies.com/products/jprofiler/overview.html" target="_blank" title="File Management">
  <img src="http://www.ej-technologies.com/images/product_banners/jprofiler_large.png" alt="File Management">
</a>
