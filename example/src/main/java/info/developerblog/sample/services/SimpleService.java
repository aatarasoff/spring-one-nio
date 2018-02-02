package info.developerblog.sample.services;

import org.springframework.stereotype.Component;

/**
 * @author alexander.tarasov
 */
@Component
public class SimpleService {
    public String getSimpleAnswer(String name) {
        return "I'm simple " + name;
    }
}
