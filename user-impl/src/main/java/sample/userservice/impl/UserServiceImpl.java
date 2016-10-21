package sample.userservice.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import sample.userservice.api.User;
import sample.userservice.api.UserService;

import javax.inject.Inject;

/**
 * Implementation of the UserService.
 */
public class UserServiceImpl implements UserService {

    private final PersistentEntityRegistry persistentEntityRegistry;

    @Inject
    public UserServiceImpl(PersistentEntityRegistry p) {
        this.persistentEntityRegistry = p;
        persistentEntityRegistry.register(UserEntity.class);
    }

    @Override
    public ServiceCall<NotUsed, User> findUser(String id) {
        return req -> {
            // Look up the user entity for the given ID.
            PersistentEntityRef<UserCommand> ref =  persistentEntityRegistry.refFor(UserEntity.class, id);
            // Ask the entity the FindUserCma command.
            return ref.ask(new UserCommand.FindUserCmd(id));
        };
    }

    @Override
    public ServiceCall<User, Done> addUser() {
        return req -> {
            PersistentEntityRef<UserCommand> ref =  persistentEntityRegistry.refFor(UserEntity.class, req.id);
            return ref.ask(new UserCommand.AddUserCmd(req));
        };
    }
}
