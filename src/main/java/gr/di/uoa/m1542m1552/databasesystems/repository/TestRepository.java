package gr.di.uoa.m1542m1552.databasesystems.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import gr.di.uoa.m1542m1552.databasesystems.domain.Test;

import java.util.List;

public interface TestRepository extends MongoRepository<Test, String> {

    Test findFirstByTestString(String testString);

    Test findByTestStringAndTestBoolean(String testString, boolean testBoolean);

    //Supports native JSON query string
    @Query("{testString:'?0'}")
    Test findCustomByTestString(String testString);

    @Query("{testString: { $regex: ?0 } })")
    List<Test> findCustomByRegExTestString(String testString);

}
