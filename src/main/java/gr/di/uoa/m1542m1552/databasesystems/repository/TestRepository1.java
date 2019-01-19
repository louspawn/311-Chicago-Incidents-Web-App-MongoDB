package gr.di.uoa.m1542m1552.databasesystems.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import gr.di.uoa.m1542m1552.databasesystems.domain.Test1;

import java.util.List;

public interface TestRepository1 extends MongoRepository<Test1, String> {

    Test1 findFirstByTestString(String testString);

    Test1 findByTestString(String testString);

    //Supports native JSON query string
    @Query("{testString:'?0'}")
    Test1 findCustomByTestString(String testString);

    @Query("{testString: { $regex: ?0 } })")
    List<Test1> findCustomByRegExTestString(String testString);

}
