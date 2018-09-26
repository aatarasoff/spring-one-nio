package info.developerblog.sample.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author alexander.tarasov
 */
public class Payload implements Serializable {
    private String value;

    public String getValue() {
        return value;
    }

    public Payload withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payload)) return false;
        Payload payload = (Payload) o;
        return Objects.equals(value, payload.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
