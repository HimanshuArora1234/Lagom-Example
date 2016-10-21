package sample.userservice.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.CompressedJsonable;
import sample.userservice.api.User;

import javax.annotation.concurrent.Immutable;


/**
 * The state for the {@link UserEntity} entity.
 */
@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public final class UserState implements CompressedJsonable {
    public final User user;

    @JsonCreator
    public UserState(User u) {
        this.user = Preconditions.checkNotNull(u);
    }
}
