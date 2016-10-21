package sample.userservice.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;
import sample.userservice.api.User;

import javax.annotation.concurrent.Immutable;

/**
 * This interface defines all the events that the User entity supports.
 * <p>
 * By convention, the events should be inner classes of the interface, which
 * makes it simple to get a complete picture of what events an entity has.
 */
public interface UserEvent extends Jsonable {
    /**
     * An event that represents a new user added.
     */
    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class UserAdded implements UserEvent {
        public final User user;
        @JsonCreator
        public UserAdded(User u) { this.user = u; }
    }
}
