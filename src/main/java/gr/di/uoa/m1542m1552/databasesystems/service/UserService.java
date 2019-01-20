package gr.di.uoa.m1542m1552.databasesystems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.di.uoa.m1542m1552.databasesystems.domain.Request;
import gr.di.uoa.m1542m1552.databasesystems.domain.User;
import gr.di.uoa.m1542m1552.databasesystems.domain.UserUpvote;
import gr.di.uoa.m1542m1552.databasesystems.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(String userId) {
        return userRepository.findOne(userId);
    }

    public boolean hasUserUpvotedRequest(String userId, String requestId) {
        if(userRepository.getUserUpvotedRequest(userId, requestId) != null) {
            return true;
        }

        return false;
    }

    public User addUpvote(User user, Request request) {
        user.getUpvotes().add(new UserUpvote(request.getId(), request.getWard()));
        user.setUpvoteNum(user.getUpvoteNum() + 1);

        return userRepository.save(user);
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
