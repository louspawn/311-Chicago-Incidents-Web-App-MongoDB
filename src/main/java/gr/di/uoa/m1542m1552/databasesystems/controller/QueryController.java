package gr.di.uoa.m1542m1552.databasesystems.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.di.uoa.m1542m1552.databasesystems.domain.QueryResult;
import gr.di.uoa.m1542m1552.databasesystems.domain.Request;
import gr.di.uoa.m1542m1552.databasesystems.domain.User;
import gr.di.uoa.m1542m1552.databasesystems.service.QueryService;

@RestController
@RequestMapping("/query")
public class QueryController {
    @Autowired
    QueryService queryService;

    @GetMapping("/1/startDate={startDateStr}&endDate={endDateStr}")
    public List<QueryResult> query1(@PathVariable String startDateStr, @PathVariable String endDateStr) {

        return queryService.query1(startDateStr, endDateStr);
    }

    @GetMapping("/2/typeOfServiceRequest={typeOfServiceRequestStr}&creationDate={creationDateStr}&sompletionDate={completionDateStr}")
    public List<QueryResult> query2(@PathVariable String typeOfServiceRequestStr, @PathVariable String creationDateStr,
            @PathVariable String completionDateStr) {
        Date creationDate, completionDate;

        try {
            creationDate = new SimpleDateFormat("yyyy-MM-dd").parse(creationDateStr);
        } catch (ParseException e) {
            return null;
        }
        try {
            completionDate = new SimpleDateFormat("yyyy-MM-dd").parse(completionDateStr);
        } catch (ParseException e) {
            return null;
        }

        return queryService.query2(typeOfServiceRequestStr, creationDate, completionDate);
    }

	@GetMapping("/3/date={dateStr}")
	public List<QueryResult> query3(@PathVariable String dateStr) {

        return queryService.query3(dateStr);
    }

	@GetMapping("/5/startDate={startDateStr}&endDate={endDateStr}")
	public List<QueryResult> query5(@PathVariable String startDateStr, @PathVariable String endDateStr) {

        return queryService.query5(startDateStr, endDateStr);
    }

	@GetMapping("/7/date={dateStr}")
	public List<Request> query7(@PathVariable String dateStr) {

        return queryService.query7(dateStr);
    }

	@GetMapping("/9/")
	public List<QueryResult> query9() {

        return queryService.query9();
    }

	@GetMapping("/11/fullName={fullName}")
	public QueryResult query11(@PathVariable String fullName) {

        return queryService.query11(fullName);
    }
}
