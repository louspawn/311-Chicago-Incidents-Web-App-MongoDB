package gr.di.uoa.m1542m1552.databasesystems.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import gr.di.uoa.m1542m1552.databasesystems.domain.User;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{_id: '?0', upvotes.requestId: '?1'}")
    public User getUserUpvotedRequest(String userId, String requestId);

    // User findFirstByTestString(String testString);

    // User findByTestString(String testString);

    //Supports native JSON query string
    // @Query("{testString:'?0'}")
    // User findCustomByTestString(String testString);

    // @Query("{testString: { $regex: ?0 } })")
    // User<User> findCustomByRegExTestString(String testString);

}
