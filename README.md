[![Build CDI Test](https://github.com/guhilling/cdi-test/actions/workflows/maven.yml/badge.svg)](https://github.com/guhilling/cdi-test/actions/workflows/maven.yml)
[![CodeQL](https://github.com/guhilling/cdi-test/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/guhilling/cdi-test/actions/workflows/codeql-analysis.yml)
[![Coverage (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=de.hilling.junit.cdi%3Acdi-test&metric=coverage)](https://sonarcloud.io/dashboard?id=de.hilling.junit.cdi%3Acdi-test)
[![Status (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=de.hilling.junit.cdi%3Acdi-test&metric=alert_status)](https://sonarcloud.io/dashboard?id=de.hilling.junit.cdi%3Acdi-test)
[![Maintainability (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=de.hilling.junit.cdi%3Acdi-test&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=de.hilling.junit.cdi%3Acdi-test)
[![Maven Central](https://img.shields.io/maven-central/v/de.hilling.junit.cdi/cdi-test.svg)](http://search.maven.org/#search|gav|1|g:"de.hilling.junit.cdi"%20AND%20a:"cdi-test")

# cdi-test

## A test framework for CDI based Java applications

cdi-test is a junit-addon for easy and quick testing of cdi projects.

cdi-test is available as a JUnit 4 TestRunner in the 1.x Versions.
Starting with 2.0 cdi-test is used as an Extension with JUnit 5.

cdi-test is available under the [The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)


## Main features:

* Fast because the CDI-Container is started only once for the whole scenario.
* Well tested.
* Supports mocks and test alternatives for CDI beans. These can be activated per test class.
* Support for Eclipse Microprofile Config via https://github.com/guhilling/cdi-test-microprofile.
* Support for some EJB features to test JEE application components:
    * Inject EntityManager via ``@Inject`` or ``@PersistenceContext``
    * Injection of Stateless Beans

## Usage

See the [Documentation on github pages](http://guhilling.github.io/cdi-test/).

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

<a href="https://www.ej-technologies.com/products/jprofiler/overview.html" target="_blank" title="JProfiler">
  <img src="https://www.ej-technologies.com/images/product_banners/jprofiler_large.png" alt="JProfiler">
</a>
<a href="https://www.jetbrains.com/?from=cdi-test" target="_blank" title="JetBrains">
  <img src=".logos/jetbrains-variant-2.svg">
</a>
