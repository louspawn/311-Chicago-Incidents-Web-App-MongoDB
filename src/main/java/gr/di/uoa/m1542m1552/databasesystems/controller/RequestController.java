package gr.di.uoa.m1542m1552.databasesystems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.di.uoa.m1542m1552.databasesystems.domain.Request;
import gr.di.uoa.m1542m1552.databasesystems.service.RequestService;

@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestService requestService;

	@PostMapping("/")
	public Request createRequest(@RequestBody Request newRequest) {
        return requestService.createRequest(newRequest);
    }
    
	@GetMapping("/")
	public List<Request> getRequests() {
        return requestService.getRequests();
	}
}
