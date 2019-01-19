package gr.di.uoa.m1542m1552.databasesystems.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import gr.di.uoa.m1542m1552.databasesystems.domain.Request;

import java.util.List;

public interface RequestRepository extends MongoRepository<Request, String> {

    // Request findFirstByTestString(String testString);

    // Request findByTestString(String testString);

    //Supports native JSON query string
    // @Query("{testString:'?0'}")
    // Request findCustomByTestString(String testString);

    // @Query("{testString: { $regex: ?0 } })")
    // Request<Request> findCustomByRegExTestString(String testString);

}
