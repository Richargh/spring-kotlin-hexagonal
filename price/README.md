# Spring Boot Hybrid Bean Defintions

An example for a Spring Boot Application that uses annotations and functional beans but no component scanning.

## Motivation

Functional bean defintions are very readable, provide a [significant performance boost](https://spring.io/blog/2018/12/12/how-fast-is-spring), and don't rely on a programming language outside of our actual programming language (kotlin/java). That last criteria is very important to me. I don't want to programm with annotations that have their own logic and hidden hierarchies, I want to program with kotlin/java and use the syntax I am familiar with. So I am very happy that Spring supports functional bean definitions.

The trouble is, most Spring applications use annotations and can't be migrated in one go to functional definitions. It turns out that it's possible to slowly migrate from one approach to another. This project is a small example that shows how to write a hybrid bean configs using both styles. I finally understood how to write a hybrid app thanks to this [blog post, that explains how to slowly migrate](https://blog.frankel.ch/spring-boot-migrating-functional/).

Also it's an example project for me to play around with Spring (Boot) features, but never mind that. Most of those remarks are hidden away in the [Spring Boot Helper file](./SPRING-BOOT-HELPER.md). You can check it out if you wonder why I implemented things a certain way.

Another important note: there are probably cleaner approaches to the one I picked. Having `WebApplication` implement `ApplicationContextInitializer<GenericApplicationContext>` is another approach and can be seen [here](https://github.com/dsyer/spring-boot-auto-reflect). 

And another important note: this example use `@EnableAutoConfiguration` because an Application without it is [rather large](https://github.com/dsyer/spring-boot-micro-apps/blob/master/src/main/java/com/example/func/FuncApplication.java). I hope to migrate the example once [Spring Fu](https://github.com/spring-projects/spring-fu) becomes mature.