To reproduce [issue 47430](https://github.com/quarkusio/quarkus/issues/47430), run 
`./gradlew :application-server:services:foo:test`

the service `foo` imports the dependency 'sdk' from `:shared:sdk`. 

`:shared` is an included build in `application-server`.

A duplicate of the SDK code is in the `application-server` project as well, and you can observe that the tests pass if toggling between the local and external copy of the dependency.

See line 14/15 of `application-server/services/foo/build.gradle.kts`

Also observe the KSerializer cast failure due to using different class loaders. Stacktrace from `./gradlew :application-server:services:foo:test`. The task `:application-server:services:foo:quarkusTest` shows the `NoClassDefFoundError` error. 