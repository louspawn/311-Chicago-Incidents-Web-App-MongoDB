package gr.di.uoa.m1542m1552.databasesystems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.di.uoa.m1542m1552.databasesystems.domain.Test;
import gr.di.uoa.m1542m1552.databasesystems.repository.TestRepository;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    public List<Test> getTests(){
        return testRepository.findAll();
    }

    Test findFirstByTestString(String testString) {
        return testRepository.findFirstByTestString(testString);
    }

    Test findByTestString(String testString) {
        return testRepository.findByTestString(testString);
    }

    Test findCustomByTestString(String testString) {
        return testRepository.findCustomByTestString(testString);
    }

    List<Test> findCustomByRegExTestString(String testString) {
        return testRepository.findCustomByRegExTestString(testString);
    }
}
