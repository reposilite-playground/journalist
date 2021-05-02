# Dynamic Logger [![CI](https://github.com/dzikoysk/dynamic-logger/actions/workflows/maven.yml/badge.svg)](https://github.com/dzikoysk/dynamic-logger/actions/workflows/maven.yml)
Tiny logging wrapper dedicated for CLI oriented applications with non-static logger that require dynamic threshold/level changes,
programmable output formatting, custom levels, print stream redirecting and easily testable output.

Available implementations:
* [x] System logger
* [x] In memory logger
* [x] [SL4J](https://github.com/qos-ch/slf4j) implementation
* [x] Aggregated logger

#### Artifact 
```xml
<dependency>
    <artifactId>dynamic-logger</artifactId>
    <groupId>net.dzikoysk</groupId>
    <version>1.0.0</version>
</dependency>
```
Repository:
```xml
<repository>
    <id>panda-repository</id>
    <url>https://repo.panda-lang.org/releases</url>
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
        logger,
        new SystemLogger(),
        new Slf4jLogger(LoggerFactory.getLogger("Default logger"), Channel.ALL)
);
```

### Used by
* [Panda](https://github.com/panda-lang/panda)
* [Reposilite](https://github.com/dzikoysk/reposilite/)
