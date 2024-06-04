# microbenchmark-java-reflection

Java reflection benchmark with [OpenJdk JMH](https://github.com/openjdk/jmh)

How to Run
----------

### required:
- java 8 or above
- maven 3.x.x
- asdf-vm(optionally to run [./run-all.sh](./run-all.sh))


### Build benchmark
```shell
mvn clean package
```

### Run benchmark
```shell
java -jar target/benchmarks.jar
```


Results
-------
CPU AMD Ryzen 5 4600G @Ubuntu 22.04

Openjdk-21
```
Benchmark                         Mode  Cnt    Score   Error  Units
Validation.hibernateValidator     avgt   25  498.518 ±20.423  ns/op
Validation.myValidator            avgt   25  592.480 ± 4.291  ns/op
Validation.myValidationWithCache  avgt   25   34.221 ± 0.077  ns/op
Validation.manualValidation       avgt   25    1.227 ± 0.004  ns/op
```
