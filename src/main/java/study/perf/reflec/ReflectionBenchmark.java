package study.perf.reflec;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import study.perf.reflec.dto.Person;
import study.perf.reflec.myframework.ByReflectionValidator;
import study.perf.reflec.myframework.ByReflectionValidatorWithCache;
import study.perf.reflec.myframework.RequiredFieldException;

import java.util.Date;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

@Fork(value = 1, warmups = 2)
@Warmup(iterations = 1, time = 2)
@Measurement(iterations = 2, time = 2)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(NANOSECONDS)
public class ReflectionBenchmark {

    @Benchmark
    public void byReflectionValidation(Blackhole blackhole) {
        Person pessoaDTO = new Person();
        pessoaDTO.setCod(12345678L);
        pessoaDTO.setName("Joao");
        pessoaDTO.setEmail("joao@gmail.com");
        pessoaDTO.setCreatedAt(new Date());
        ByReflectionValidator.validate(pessoaDTO);
        blackhole.consume(pessoaDTO);
    }

    @Benchmark
    public void byReflectionWithCacheValidation(Blackhole blackhole) {
        Person pessoaDTO = new Person();
        pessoaDTO.setCod(12345678L);
        pessoaDTO.setName("Joao");
        pessoaDTO.setEmail("joao@gmail.com");
        pessoaDTO.setCreatedAt(new Date());
        ByReflectionValidatorWithCache.validate(pessoaDTO);
        blackhole.consume(pessoaDTO);
    }

    @Benchmark
    public void manualValidation(Blackhole blackhole) {
        Person pessoaDTO = new Person();
        pessoaDTO.setCod(12345678L);
        pessoaDTO.setName("Joao");
        pessoaDTO.setEmail("joao@gmail.com");
        pessoaDTO.setCreatedAt(new Date());

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
