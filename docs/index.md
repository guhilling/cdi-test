# cdi-test [![Build Status](https://travis-ci.org/guhilling/cdi-test.svg?branch=master)](https://travis-ci.org/guhilling/cdi-test) [![Coverage Status](https://coveralls.io/repos/guhilling/cdi-test/badge.svg?branch=master)](https://coveralls.io/r/guhilling/cdi-test?branch=master) [![Maven Central](https://img.shields.io/maven-central/v/de.hilling.junit.cdi/cdi-test.svg)](http://search.maven.org/#search|gav|1|g:"de.hilling.junit.cdi"%20AND%20a:"cdi-test")

cdi-test is a junit 4 runner for unit testing cdi or jee projects.

cdi-test can use different versions of [Weld](http://weld.cdi-spec.org) for the tests, so you should
be able to use the same cdi implementation in you tests as in your container.

## Main features:

* Plain cdi driven test, no classpath magic.
* Custom scopes for testing.
* Uses interceptors for on-the-fly switching between mockito-mocks, test implementation and production implementations.
* Support for some ejb features to test jee application components:
    * Inject EntityManager via ``@Inject`` or ``@PersistenceContext``
    * Injection of Stateless Beans
* Provides mapping between annotations: You can map ``@Stateless`` beans in production to ``@RequestScoped``
in your tests.


## Tutorial

A quick tutorial is [also available](tutorial_5_minutes.md)

## License

cdi-test is available under the [The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)
