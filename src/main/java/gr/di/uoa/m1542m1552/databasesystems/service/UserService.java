package gr.di.uoa.m1542m1552.databasesystems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.di.uoa.m1542m1552.databasesystems.domain.User;
import gr.di.uoa.m1542m1552.databasesystems.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }

    // Test findFirstByTestString(String testString) {
    //     return testRepository.findFirstByTestString(testString);
    // }

    // Test findByTestString(String testString) {
    //     return testRepository.findByTestString(testString);
    // }

    // Test findCustomByTestString(String testString) {
    //     return testRepository.findCustomByTestString(testString);
    // }

    // List<Test> findCustomByRegExTestString(String testString) {
    //     return testRepository.findCustomByRegExTestString(testString);
    // }
}
