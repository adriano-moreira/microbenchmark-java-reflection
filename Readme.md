# microbenchmark-java-reflection

Java reflection benchmark with [OpenJdk JMH](https://github.com/openjdk/jmh)

...

Results
-------


How to Run
----------

### required:
- java 8 or above
- maven
- asdf-vm(optionally to run [./run-all.sh](./run-all.sh))


### Build benchmark
```shell
mvn clean package
```

### Run benchmark
```shell
java -jar target/benchmarks.jar
```