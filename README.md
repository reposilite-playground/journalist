# Journalist [![CI](https://github.com/reposilite-playground/journalist/actions/workflows/maven.yml/badge.svg)](https://github.com/reposilite-playground/journalist/actions/workflows/maven.yml) [![codecov](https://codecov.io/gh/reposilite-playground/journalist/graph/badge.svg?token=bxo0JbVeDE)](https://codecov.io/gh/reposilite-playground/journalist)
Tiny logging wrapper dedicated for CLI oriented applications with non-static logger that require dynamic threshold/level changes,
programmable output formatting, custom levels, print stream redirecting and easily testable output.

Available implementations:
* [x] System logger
* [x] In memory logger
* [x] Cached loggers
* [x] [SL4J](https://github.com/qos-ch/slf4j) implementation
* [x] Aggregated logger

#### Artifact

```xml

<dependency>
    <groupId>com.reposilite</groupId>
    <!-- Default -->
    <artifactId>journalist</artifactId>
    <!-- For SL4J based implementations -->
    <artifactId>journalist-sl4j</artifactId>
    <version>1.0.12</version>
</dependency>
```
Repository:
```xml
<repository>
    <id>panda-repository</id>
    <url>https://maven.reposilite.com/releases</url>
</repository>
```

### Usage

#### Print stream redirecting

```java
PrintStream printStream = logger.toPrintStream();
throwable.printStackTrace(printStream); // pass logger as printstream
printStream.close();
```

#### Dynamic threshold change

```java
Logger logger = // logger with INFO level
logger.debug("message"); // filtered
logger.setThreshold(Channel.DEBUG);
logger.debug("message"); // displayed
```

#### Custom logging levels

```java
Channel bugs = new Channel("bugs", 100.0, ChannelIntention.NEGATIVE)
logger.log(bugs, "Should not happen");
```

#### Testable output

```java
InMemoryLogger logger = new InMemoryLogger();

/*
 * Some code/app/libs using logger
 */

assertTrue(inMemory.contains("Exception"))

// or using custom filters

assertTrue(inMemory.getMessages().stream() // Stream of Entry<Channel, String /* messsage */>
    .filter(entry -> Channel.ERROR.equals(entry.getKey()))
    .filter(entry -> entry.getValue().contains("Exception"))
    .findAny())
```

#### Aggregation

```java
Logger logger = new AggregatedLogger(
        customLogger,
        new SystemLogger(),
        new Slf4jLogger(LoggerFactory.getLogger("Default logger"), Channel.ALL)
);
```

### Used by
* [Reposilite](https://github.com/dzikoysk/reposilite/)
* [Panda](https://github.com/panda-lang/panda)
