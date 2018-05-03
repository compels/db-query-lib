# db-query-lib

How do I install it using maven:

Just add to your pom the following repository

```xml
<repositories>
        ...
        <repository>
		<id>db-query-lib</id>
		<url>https://github.com/compels/db-query-lib/raw/master/target/mvn-repo</url>
	</repository>
        ...
</repositories>
```

And then add your dependency

```xml
<dependencies>
        ...
        <dependency>
		<groupId>db-query-lib</groupId>
		<artifactId>db-query-lib</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</dependency>
        ...
</dependencies>
```

And that's it!
