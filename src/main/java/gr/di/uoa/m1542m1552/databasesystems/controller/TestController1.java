package gr.di.uoa.m1542m1552.databasesystems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.di.uoa.m1542m1552.databasesystems.domain.Test1;
import gr.di.uoa.m1542m1552.databasesystems.service.TestService1;

@RestController
@RequestMapping("/tests1")
public class TestController1 {
    @Autowired
    TestService1 testService;

	@PostMapping("/")
	public Test1 createTest(@RequestBody Test1 newTest) {
        return testService.createTest(newTest);
    }
    
	@GetMapping("/")
	public List<Test1> findAllTests() {
        return testService.getTests();
	}
}
