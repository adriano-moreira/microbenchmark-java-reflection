package study.perf.reflec;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import study.perf.reflec.dto.Person;
import study.perf.reflec.myframework.MyValidator;
import study.perf.reflec.myframework.MyValidatorWithCache;
import study.perf.reflec.myframework.RequiredFieldException;

import java.util.Set;

import static java.util.concurrent.TimeUnit.*;

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


    @Benchmark
    public void hibernateValidator(ThreadState state, Blackhole blackhole) {
        Person pessoaDTO = state.getPersonValid();
        Validator validator = state.getJakartaValidator();

        Set<ConstraintViolation<Person>> violations = validator.validate(pessoaDTO);
        if (!violations.isEmpty()) {
            throw new RequiredFieldException(violations.iterator().next().getPropertyPath().toString());
        }

        blackhole.consume(violations);
        blackhole.consume(pessoaDTO);
    }

}
