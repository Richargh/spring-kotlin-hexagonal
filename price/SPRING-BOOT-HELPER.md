# Spring (Boot) Helper

A collection of somewhat useful Spring and Spring Boot content. Mostly because I have trouble remembering all the annotations and configurations that are possible and useful.

### Code Examples

* [Spring framework with functional routes and without bean annotations](https://github.com/poutsma/web-function-sample/blob/master/src/main/java/org/springframework/samples/web/reactive/function/Server.java)
* [Start a Spring Boot Application with Application Initializer](https://github.com/dsyer/spring-boot-allocations/blob/master/src/main/java/com/example/boot/BootApplication.java)
* [Kotlin ConfigurationProperties Example](https://github.com/snicoll-scratches/constructor-binding-showcase-kotlin)
* [Functional Spring without @EnableAutoConfiguration](https://github.com/dsyer/spring-boot-micro-apps/blob/master/src/main/java/com/example/func/FuncApplication.java)
* [Functional Micro Spring](https://github.com/dsyer/spring-boot-micro-apps/blob/master/src/main/java/com/example/micro/MicroApplication.java)
* [Spring Kotlin Functional](https://github.com/sdeleuze/spring-kotlin-functional)
* [Lots of Spring 5 examples](https://github.com/daggerok/spring-5-examples)

## Spring Beans

According to the [official](https://docs.spring.io/spring-framework/docs/4.1.5.RELEASE/spring-framework-reference/html/new-in-4.0.html) [docs](https://docs.spring.io/spring/docs/5.0.x/spring-framework-reference/languages.html#kotlin-bean-definition-dsl):
> The Spring Framework was first released in 2004; since then there have been significant major revisions: **Spring 2.0** provided XML namespaces and AspectJ support; **Spring 2.5** embraced annotation-driven configuration; **Spring 3.0** introduced a strong Java 5+ foundation across the framework codebase, and features such as the Java-based @Configuration model.
> ... 
> Beginning with **Spring Framework 4.0**, it is possible to define external bean configuration using a Groovy DSL. This is similar in concept to using XML bean definitions but allows for a more concise syntax. Using Groovy also allows you to easily embed bean definitions directly in your bootstrap code.
> **Spring Framework 5** introduces a new way to register beans in a functional way using lambdas as an alternative to XML or JavaConfig (@Configuration and @Bean). In a nutshell, it makes it possible to register beans with a lambda that acts as a FactoryBean. This mechanism is very efficient as it does not require any reflection or CGLIB proxies.

In code those three styles look like this:

### Xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <!-- this is just an excerpt -->
    <bean id="bookingService" class="x.y.DefaultBookingService">
        <property name="messenger" ref="messenger" />
    </bean>
</beans>
```

### Annotation

```java
@Configuration
 public class AppConfig {

     @Bean
     public MyBean myBean() {
         // instantiate, configure and return bean ...
     }
 }
```

**Bootstrap**
```java
AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 ctx.register(AppConfig.class);
 ctx.refresh();
 MyBean myBean = ctx.getBean(MyBean.class);
 // use myBean ...
```


### Functional

**Groovy**
```groovy
beans {
	service(SharedService) {
		message = "Hello World"
	}
}
```

**Java**
```java
GenericApplicationContext context = new GenericApplicationContext();
    context.registerBean(Foo.class);
    context.registerBean(Bar.class, () -> new Bar(context.getBean(Foo.class))
);
```

**Kotlin**
```kotlin
val context = GenericApplicationContext().apply {
    registerBean<Foo>()
    registerBean { Bar(it.getBean<Foo>()) }
}
```

More Kotlin examples can be found in the [spring framework reference](https://docs.spring.io/spring/docs/5.0.x/spring-framework-reference/languages.html#kotlin-bean-definition-dsl) and in the code examples from the beginning.


### My verdict

We can see a clear progression from xml to annotations to code dsl. We move the defintion of our injectable beans from a seperate language (xml, then annotations which have their own logic) to finally use the same syntax and semantics as our programming language. 

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

## Routes

Spring 5 from [September 2016](https://spring.io/blog/2016/09/22/new-in-spring-5-functional-web-framework) supports the definition of functional routes likes this:

```java
RouterFunction<?> route = route(GET("/person/{id}"),
  request -> {
    Mono<Person> person = Mono.justOrEmpty(request.pathVariable("id"))
      .map(Integer::valueOf)
      .then(repository::getPerson);
    return Response.ok().body(fromPublisher(person, Person.class));
  })
  .and(route(GET("/person"),
    request -> {
      Flux<Person> people = repository.allPeople();
      return Response.ok().body(fromPublisher(people, Person.class));
    }))
  .and(route(POST("/person"),
    request -> {
      Mono<Person> person = request.body(toMono(Person.class));
      return Response.ok().build(repository.savePerson(person));
    }));
```

**Start**
```java
HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
ReactorHttpHandlerAdapter adapter =
  new ReactorHttpHandlerAdapter(httpHandler);
HttpServer server = HttpServer.create("localhost", 8080);
server.startAndAwait(adapter);
```

**Beans**
```java
@Configuration
public class FunctionalRouter {

  @Bean
  public RouterFunction<ServerResponse> routes() {
    return RouterFunctions.route()
                          .POST("/fn", request -> ServerResponse.ok().body("Functional hello!"))
                          .GET("/**", request -> ServerResponse.ok().body("_self: " + request.path()))
                          .filter((request, next) -> {
                            var response = next.handle(request);
                            var headers = HttpHeaders.writableHttpHeaders(response.headers());
                            headers.add("X-FUNCTIONAL", "It's fucking awesome!");
                            return response;
                          })
                          .build();
  }
}
```

Or in [Kotlin](https://github.com/daggerok/spring-5-examples/blob/master/awesome-kotlin/src/main/kotlin/com/github/daggerok/AwesomeKotlinApplication.kt)

```kotlin
val v1: RouterFunctionDsl.() -> Unit = {
  GET("/hello") { helloApi }
  GET("/**", defaultFallback)
}

val v2: RouterFunctionDsl.() -> Unit = {
  GET("/2hello") { helloApi }
  GET("/2**", defaultFallback)
}

@Configuration
class RouterFunctionConfig {
  @Bean fun routes() = router {
    resources("/**", ClassPathResource("/static/"))

    contentType(TEXT_HTML)
    GET("/", spa)

    contentType(APPLICATION_JSON_UTF8)
    (accept(APPLICATION_JSON).and("/api")).nest {
      "/v1".nest(v1)
      "/v2".nest(v2)
    }
    GET("/**", defaultFallback)
  }
}
```

## Transactions

As written [here](https://github.com/daggerok/functional-spring-boot-transaction) you can use the Spring `TransactionTemplate` to execute commands in a transaction.

```java
// @Autowired private PlatformTransactionManager transactionManager;

TransactionTemplate template = new TransactionTemplate(transactionManager);
transactionTemplate.execute(status -> {
  Message message = Message.of(msg);
  em.persist(message);
  return message;
});
```

## Getting Rid of Autoconfiguration 

### Without Spring Fu

Interestingly the various [Spring Boot Starters (Source Code)](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project/spring-boot-starters) projects only include a single pom. The [web starter pom](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-starters/spring-boot-starter-web/pom.xml) for example has dependencies on things such as spring-boot-starter-tomcat, spring-webmvc, spring-web... 

So if you include `spring-boot-starter-web` in your project pom it only ensures that the right depenendencies are in your classpath. The magic happens elsewhere.

The [Spring Boot Project (Source Code)](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project) is one repository on github with multiple parts. The starters we have already talked about. The other intersting part is the autoconfigure folder. If we take a look at the [MongoAutoConfiguration](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/mongo/MongoAutoConfiguration.java) we can see that it is a `@Configuration` with various `Bean` definitions that are in turn guarded by various `@Conditionals`. 

We can theoretically replicate (I haven't tested it) the mongo configuration in functional bean style by instantiating the `@Configuration` ourselves, executing the public bean methods and returning their results from inside a bean definition:
 
 ```kotlin
 beans { 
    bean {
        MongoAutoConfiguration().mongo(
            ref<MongoProperties>(), 
            ref<ObjectProvider<MongoClientOptions>>(), 
            ref<Environment>()
        )
    } 
}
 ```
 
### With Spring Fu

That is similar to what the [Spring Fu](https://github.com/spring-projects/spring-fu) incubator does. It provides various Initializers like the [MongoDataInitializer](https://github.com/spring-projects/spring-fu/blob/master/autoconfigure-adapter/src/main/java/org/springframework/boot/autoconfigure/data/mongo/MongoDataInitializer.java). You can then reference these initializers when launching your app:

```kotlin
import org.springframework.boot.WebApplicationType
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.AbstractDsl


open class ReactiveMongoDsl(
    private val init: ReactiveMongoDsl.() -> Unit
) : AbstractDsl() {

	private val properties = MongoProperties()

	override fun initialize(context: GenericApplicationContext) {
		super.initialize(context)
		init()
		MongoDataInitializer(properties).initialize(context)
		MongoReactiveDataInitializer(properties).initialize(context)
		MongoReactiveInitializer(properties, embedded).initialize(context)
    }
}

fun ConfigurationDsl.reactiveMongodb(dsl: ReactiveMongoDsl.() -> Unit = {}) {
	ReactiveMongoDsl(dsl).initialize(context)
}


val dataConfig = configuration {
	beans {
		bean<UserRepository>()
	}
	listener<ApplicationReadyEvent> {
		ref<UserRepository>().init()
	}
	reactiveMongodb {
		embedded()
	}
}

val app = application(WebApplicationType.REACTIVE) {
	configurationProperties<SampleProperties>("sample")
	enable(dataConfig)
	enable(webConfig)
}

fun main() {
	app.run()
}
```

### Other

Another way is to figure out what bean defintions Spring needs at startup and register them by hand. This is similar to the first solution but we don't depend on the autoconfiguration classes. We can take inspiration from what they do though. The [micro spring example](https://github.com/dsyer/spring-boot-micro-apps/blob/master/src/main/java/com/example/micro/MicroApplication.java) from earlier includes this code:

```java
public class MicroApplication {

	public static void main(String[] args) throws Exception {
		long t0 = System.currentTimeMillis();
		GenericApplicationContext context = new MicroApplication().run();
		ApplicationBuilder.start(context, b -> {
			System.err.println(
					"Started HttpServer: " + (System.currentTimeMillis() - t0) + "ms");
		});
	}

	public GenericApplicationContext run() {
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBean(RouterFunction.class,
				() -> RouterFunctions
						.route(GET("/"),
								request -> ok().body(Mono.just("Hello"), String.class)));
		
		context.registerBean(DefaultErrorWebExceptionHandler.class,
				() -> errorHandler(context));
		context.registerBean("webHandler", HttpHandler.class, () -> httpHandler(context));
		context.refresh();
		return context;
    }
}
```