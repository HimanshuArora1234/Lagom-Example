package sample.userservice.impl;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import sample.userservice.api.User;

import javax.annotation.concurrent.Immutable;

/**
 * This interface defines all the commands that the User entity supports.
 *
 * By convention, the commands should be inner classes of the interface, which
 * makes it simple to get a complete picture of what commands an entity
 * supports.
 */
public interface UserCommand extends Jsonable {

    /**
     * A command to add a new user.
     * <p>
     * It has a reply type of {@link akka.Done}, which is sent back to the caller
     * when all the events emitted by this command are successfully persisted.
     */
    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class AddUserCmd implements UserCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
        public final User user;
        @JsonCreator
        public AddUserCmd(User u) { this.user = u; }
    }

    /**
     * A command to find user of given id.
     * <p>
     * The reply type is User.
     */
    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class FindUserCmd implements UserCommand, PersistentEntity.ReplyType<User> {
        public final String id;
        @JsonCreator
        public FindUserCmd(String id) { this.id = id; }
    }
}
