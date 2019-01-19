package gr.di.uoa.m1542m1552.databasesystems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.di.uoa.m1542m1552.databasesystems.domain.Request;
import gr.di.uoa.m1542m1552.databasesystems.repository.RequestRepository;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    public List<Request> getRequests(){
        return requestRepository.findAll();
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
