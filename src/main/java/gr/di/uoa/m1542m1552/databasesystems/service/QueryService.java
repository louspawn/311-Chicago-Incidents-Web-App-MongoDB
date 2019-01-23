package gr.di.uoa.m1542m1552.databasesystems.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import gr.di.uoa.m1542m1552.databasesystems.domain.Request;
import gr.di.uoa.m1542m1552.databasesystems.repository.RequestRepository;
import gr.di.uoa.m1542m1552.databasesystems.repository.UserRepository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

@Service
public class QueryService {
    @Autowired
	MongoTemplate mongoTemplate;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Request> query1(Date startDate, Date endDate) {
        // Aggregation agg = newAggregation(
        // match(Criteria.where("_id").lt(10)),
        // group("hosting").count().as("total"),
        // project("total").and("hosting").previousOperation(),
        // sort(Sort.Direction.DESC, "total")
        // );

        Aggregation agg = newAggregation(
                match(Criteria.where("createdDate").gte(startDate).and("endDate").lte(endDate)),
                group("id").count().as("total"), project("typeOfServiceRequest").and("total").previousOperation(),
                sort(Sort.Direction.DESC, "total"));

        // Convert the aggregation result into a List
        AggregationResults<Request> groupResults = mongoTemplate.aggregate(agg, Request.class, Request.class);
		List<Request> result = groupResults.getMappedResults();
		
		return result;
    }
}
