package gr.di.uoa.m1542m1552.databasesystems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.di.uoa.m1542m1552.databasesystems.domain.Test;
import gr.di.uoa.m1542m1552.databasesystems.service.TestService;

@RestController
@RequestMapping("/tests")
public class TestController {
    @Autowired
    TestService testService;

	@PostMapping("/")
	public Test createTest(@RequestBody Test newTest) {
        return testService.createTest(newTest);
    }
    
	@GetMapping("/")
	public List<Test> findAllTests() {
        return testService.getTests();
	}
}
