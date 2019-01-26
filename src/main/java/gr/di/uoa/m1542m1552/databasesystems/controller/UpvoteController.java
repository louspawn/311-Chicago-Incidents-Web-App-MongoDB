package gr.di.uoa.m1542m1552.databasesystems.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

        if(userId == null || requestId == null) {
            return "Bad request! Null userId or requestId";
        }

        Optional<Request> request = requestService.getRequest(requestId);
        Optional<User> user = userService.getUser(userId);

        if(!user.isPresent() || !request.isPresent()) {
            return "Bad request! Wrong userId or requestId";
        }

        if(userService.hasUserUpvotedRequest(userId, requestId)) {
            return "User already upvoted this request";
        }

        requestService.addUpvote(request.get(), user.get());
        userService.addUpvote(user.get(), request.get());

        return "OK";
    }
}
