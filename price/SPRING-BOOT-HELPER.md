# Spring (Boot) Helper

A collection of somewhat useful Spring and Spring Boot content. Mostly because I have trouble remembering all the annotations and configurations that are possible and useful.

### Code Examples

* [Spring framework with functional routes and without bean annotations](https://github.com/poutsma/web-function-sample/blob/master/src/main/java/org/springframework/samples/web/reactive/function/Server.java)
* [Start a Spring Boot Application with Application Initializer](https://github.com/dsyer/spring-boot-allocations/blob/master/src/main/java/com/example/boot/BootApplication.java)
* [Kotlin ConfigurationProperties Example](https://github.com/snicoll-scratches/constructor-binding-showcase-kotlin)
* [Functional Spring without @EnableAutoConfiguration](https://github.com/dsyer/spring-boot-micro-apps/blob/master/src/main/java/com/example/func/FuncApplication.java)
* [Functional Micro Spring](https://github.com/dsyer/spring-boot-micro-apps/blob/master/src/main/java/com/example/micro/MicroApplication.java)
* [Spring Kotlin Functional](https://github.com/sdeleuze/spring-kotlin-functional)

## Properties

### Explicit properties

See [Spring Property Docs](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-application-property-files)

`$ java -jar myproject.jar --spring.config.name=myproject`
`$ java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties`

### Property Order

Spring Boot has an [order](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config) in which it reads configuration to allow sensible overrides.

The important ones for us are:

2. `@TestPropertySource` annotations on your tests.
4. Command line arguments.
9. Java System properties (System.getProperties()).
12. Profile-specific application properties **outside** of your packaged jar (`application-{profile}.properties` and YAML variants).
13. Profile-specific application properties packaged **inside** your jar (`application-{profile}.properties` and YAML variants).
14. Application properties **outside** of your packaged jar (`application.properties` and YAML variants).
15. Application properties packaged **inside** your jar (`application.properties` and YAML variants).
16. `@PropertySource` annotations on your `@Configuration` classes.

### Common Application Properties

Spring Boot has a long list of [common application [properties]](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#common-application-properties) like:

```properties
spring.profiles.active= 
```

### Type-Safe Properties

See [Spring Typesafe Properties Docs](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties)

```java
@ConfigurationProperties("acme")
public class AcmeProperties {
	private boolean enabled;
	
	public static class Security {
    		private String username;
    }
}
```

or 

```kotlin
@ConfigurationProperties("acme")
class AcmeProperties(val enabled: Boolean = false)
```

which maps to the following properties:
```properties
acme.enabled=true
acme.security.username=foo
```

and needs to be enabled:

```kotlin
@SpringBootApplication
@EnableConfigurationProperties(AcmeProperties::class)
class BlogApplication {
  // ...
}
```

and is a type-safe alternative to the `@Value` annotation which we can add above fields.

## Web Server

[Disable Web Server](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-disable-web-server) with `spring.main.web-application-type=none`

To switch off the HTTP endpoints completely but still create a WebApplicationContext, use server.port=-1. (Doing so is sometimes useful for testing.)


## Testing

Important [Spring Testing Annotations](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#integration-testing-annotations-spring) like `@ContextConfiguration` can be found here.

### Running Context Tests

```kotlin
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RunningContextTests(@Autowired val restTemplate: TestRestTemplate) {

}
```

`@Autowired` is required for tests but not for production code. `@SpringBootTest` looks for a `@SpringBootApplication` in the production code. If we don't have this annotation we need to tell the test where to look.

### How much to test

Spring Boot Test provides a bunch of ways to test an application. Some of them run the context, resulting in our application listening on a port, some of them only initialize the context. 

* If we set our web environment the `@SpringBootTest(webEnvironment = ...)` will start our container. Then we can test our application using `TestRestTemplate`. For reactive WebFlux applications Spring now provides the `WebTestClient` instead.
* If we don't set the web environment the `@SpringBootTest()` won't start the container but will create the application context. In our tests we can inject the applicationContext and resolve all beans that we need. If we want to test our controllers without a running container we can inject `MockMvc` into our tests and pretend we are doing a web request.
* If we don'' require the spring infrastructure and we only want to test our domain logic we can write a regular JUnit test.


## Create Application Context manually

```kotlin
val context = GenericApplicationContext()
beans.invoke(context)
context.refresh()
assertNotNull(context.getBean<Foo>())
```