package gr.di.uoa.m1542m1552.databasesystems.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gr.di.uoa.m1542m1552.databasesystems.domain.Request;
import gr.di.uoa.m1542m1552.databasesystems.domain.User;
import gr.di.uoa.m1542m1552.databasesystems.service.RequestService;
import gr.di.uoa.m1542m1552.databasesystems.service.UserService;

@RestController
@RequestMapping("/upvote")
public class UpvoteController {
    @Autowired
    RequestService requestService;
    @Autowired
    UserService userService;

	@PostMapping("/")
	public String createUpvote(@RequestBody Map<String, String> bodyMap) {
        String userId = bodyMap.get("userId");
        String requestId = bodyMap.get("requestId");

        Request request = requestService.getRequest(requestId);
        User user = userService.getUser(userId);

        if(user == null || request == null) {
            return "Bad request! Wrong userId or requestId";
        }

        if(userService.hasUserUpvotedRequest(userId, requestId)) {
            return "User already upvoted this request";
        }

        requestService.addUpvote(request, user);
        userService.addUpvote(user, request);

        return "OK";
    }
}
