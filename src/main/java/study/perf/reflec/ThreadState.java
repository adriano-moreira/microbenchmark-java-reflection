package study.perf.reflec;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import study.perf.reflec.dto.Person;

import java.util.Date;

@State(Scope.Thread)
public class ThreadState {

    final Person personValid;
    final Validator jakartaValidator;

    public ThreadState() {
        personValid = new Person();
        personValid.setCod(Long.MIN_VALUE);
        personValid.setName("XPO2");
        personValid.setEmail("xpo2@gmail.com");
        personValid.setCreatedAt(new Date());

        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        jakartaValidator = factory.usingContext()
                .getValidator();
        factory.close();
    }

    public Person getPersonValid() {
        return personValid;
    }

    public Validator getJakartaValidator() {
        return jakartaValidator;
    }
}
