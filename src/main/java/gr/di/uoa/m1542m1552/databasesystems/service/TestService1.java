package gr.di.uoa.m1542m1552.databasesystems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.di.uoa.m1542m1552.databasesystems.domain.Test1;
import gr.di.uoa.m1542m1552.databasesystems.repository.TestRepository1;

@Service
public class TestService1 {
    @Autowired
    private TestRepository1 testRepository;

    public Test1 createTest(Test1 test) {
        return testRepository.save(test);
    }

    public List<Test1> getTests(){
        return testRepository.findAll();
    }

    Test1 findFirstByTestString(String testString) {
        return testRepository.findFirstByTestString(testString);
    }

    Test1 findByTestStringAndTestBoolean(String testString, boolean testBoolean) {
        return testRepository.findByTestStringAndTestBoolean(testString, testBoolean);
    }

    Test1 findCustomByTestString(String testString) {
        return testRepository.findCustomByTestString(testString);
    }

    List<Test1> findCustomByRegExTestString(String testString) {
        return testRepository.findCustomByRegExTestString(testString);
    }
}
