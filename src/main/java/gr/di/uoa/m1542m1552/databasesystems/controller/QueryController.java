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

import gr.di.uoa.m1542m1552.databasesystems.domain.Request;
import gr.di.uoa.m1542m1552.databasesystems.service.QueryService;

@RestController
@RequestMapping("/query")
public class QueryController {
    @Autowired
    QueryService queryService;

	@GetMapping("/1/startDate={startDateStr}&endDate={endDateStr}")
	public List<Request> query1(@PathVariable String startDateStr, @PathVariable String endDateStr) {
        Date startDate, endDate;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
        } catch (ParseException e) {
            return null;
        }
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
        } catch (ParseException e) {
            return null;
        }

        return queryService.query1(startDate, endDate);
    }
}
