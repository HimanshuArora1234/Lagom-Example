package sample.userservice.api;


import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

/**
 * The user service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the UserService.
 */
public interface UserService extends Service{

    /**
     * Example: curl http://localhost:9000/api/user/1
     */
    ServiceCall<NotUsed, User> findUser(String id);

    /**
     * Example: curl -H "Content-Type: application/json" -X POST -d '{"id"
     * :"1","name":"xyz","age":20}' http://localhost:9000/api/user
     */
    ServiceCall<User, Done> addUser();

    /**
     * Routing description.
     */
    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("userservice").withCalls(
                pathCall("/api/user/:id",  this::findUser),
                pathCall("/api/user", this::addUser)
        ).withAutoAcl(true);
        // @formatter:on
    }
}
