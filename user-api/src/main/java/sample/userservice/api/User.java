package sample.userservice.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;

import javax.annotation.concurrent.*;

/**
 * User domain or model.
 */
@Immutable
@JsonDeserialize
public final class User {

    public final String id;
    public final String name;
    public final Integer age;

    @JsonCreator
    public User(String id, String name, Integer age) {
        this.id = Preconditions.checkNotNull(id, "Id can't be null");
        this.name = Preconditions.checkNotNull(name, "Name can't be null");
        this.age = Preconditions.checkNotNull(age, "Age can't be null");
    }

}
