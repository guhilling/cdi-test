cdi-test [![Build Status](https://travis-ci.org/guhilling/cdi-test.svg?branch=master)](https://travis-ci.org/guhilling/cdi-test)
========

junit-addon for easy and quick testing of cdi projects.

## Main features:

* Plain cdi test, no classpath magic.
* Custom scopes for testing.
* Now uses interceptors for on-the-fly switching between mockito-mocks and real implementations.
* Basic jdbc support.

## TODO

* add database cleanup support.
* add jpa support.
* allow switchable test implementations instead of mocks.