package study.perf.reflec;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import study.perf.reflec.dto.Person;

import java.util.Date;

@State(Scope.Thread)
public class ThreadState {

    final Person personValid;

    public ThreadState() {
        personValid = new Person();
        personValid.setCod(Long.MIN_VALUE);
        personValid.setName("XPO2");
        personValid.setEmail("xpo2@gmail.com");
        personValid.setCreatedAt(new Date());
    }

    public Person getPersonValid() {
        return personValid;
    }

}
