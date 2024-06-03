package study.perf.reflec;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;
import study.perf.reflec.dto.Person;
import study.perf.reflec.myframework.MyValidator;
import study.perf.reflec.myframework.MyValidatorWithCache;
import study.perf.reflec.myframework.RequiredFieldException;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(NANOSECONDS)
public class Validation {

    @Benchmark
    public void myValidator(ThreadState state, Blackhole blackhole) {
        Person pessoaDTO = state.getPersonValid();

        MyValidator.validate(pessoaDTO);

        blackhole.consume(pessoaDTO);
    }

    @Benchmark
    public void myValidationWithCache(ThreadState state, Blackhole blackhole) {
        Person pessoaDTO = state.getPersonValid();

        MyValidatorWithCache.validate(pessoaDTO);

        blackhole.consume(pessoaDTO);
    }

    @Benchmark
    public void manualValidation(ThreadState state, Blackhole blackhole) {
        Person pessoaDTO = state.getPersonValid();

        if (pessoaDTO.getCod() == null) {
            throw new RequiredFieldException("cod");
        }
        if (pessoaDTO.getName() == null) {
            throw new RequiredFieldException("name");
        }
        if (pessoaDTO.getCreatedAt() == null) {
            throw new RequiredFieldException("createdAt");
        }

        blackhole.consume(pessoaDTO);
    }

}
