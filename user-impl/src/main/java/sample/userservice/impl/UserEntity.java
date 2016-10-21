package sample.userservice.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import sample.userservice.api.User;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This is an event sourced entity. It has a state, {@link UserState}, which
 * stores the user.
 * <p>
 * Event sourced entities are interacted with by sending them commands. This
 * entity supports two commands, a {@link sample.userservice.impl.UserCommand.AddUserCmd} command, which is
 * used to add a new user, and a {@link sample.userservice.impl.UserCommand.FindUserCmd} command, which finds user by id.
 * <p>
 * Commands get translated to events, and it's the events that get persisted by
 * the entity. Each event will have an event handler registered for it, and an
 * event handler simply applies an event to the current state. This will be done
 * when the event is first created, and it will also be done when the entity is
 * loaded from the database - each event will be replayed to recreate the state
 * of the entity.
 * <p>
 * This entity defines one event, the {@link sample.userservice.impl.UserEvent.UserAdded} event,
 * which is emitted when a {@link sample.userservice.impl.UserCommand.AddUserCmd} command is received.
 */
public class UserEntity extends PersistentEntity<UserCommand, UserEvent, UserState> {

    /**
     * An entity can define different behaviours for different states, but it will
     * always start with an initial behaviour. This entity only has one behaviour.
     */
    @Override
    public Behavior initialBehavior(Optional<UserState> snapshotState) {

        /*
         * Behaviour is defined using a behaviour builder. The behaviour builder
         * starts with a state, if this entity supports snapshotting (an
         * optimisation that allows the state itself to be persisted to combine many
         * events into one), then the passed in snapshotState may have a value that
         * can be used.
         *
         * Otherwise, the default state is to use the User("0", "No user", 0).
         */
        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(new UserState(new User("0", "No user", 0))));

        /*
         * Command handler for the AddUserCmd command.
         */
        b.setCommandHandler(UserCommand.AddUserCmd.class, (cmd, ctx) ->
            // In response to this command, we want to first persist it as a
            // UserAdded event
            ctx.thenPersist(new UserEvent.UserAdded(cmd.user), arg ->
                // Then once the event is successfully persisted, we respond with done.
                ctx.reply(Done.getInstance())
            )
        );
         /*
         * Command handler for the FindUserCmd command.
         */
        b.setReadOnlyCommandHandler(UserCommand.FindUserCmd.class, (cmd, ctx) ->
            // Get the user from the current state and reply back
            ctx.reply(state().user)
        );
        /*
         * Event handler for the UserAdded event.
         */
        b.setEventHandler(UserEvent.UserAdded.class, arg ->
            // We simply update the current state to use the user from
            // the event.
            new UserState(arg.user)
        );
        /*
         * We've defined all our behaviour, so build and return it.
         */
        return b.build();
    }
}