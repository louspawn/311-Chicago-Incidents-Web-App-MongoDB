package gr.di.uoa.m1542m1552.databasesystems.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.mongodb.BasicDBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import gr.di.uoa.m1542m1552.databasesystems.domain.QueryResult;
import gr.di.uoa.m1542m1552.databasesystems.domain.Request;
import gr.di.uoa.m1542m1552.databasesystems.domain.User;
import gr.di.uoa.m1542m1552.databasesystems.enumerations.TypeOfServiceRequest;
import gr.di.uoa.m1542m1552.databasesystems.repository.RequestRepository;
import gr.di.uoa.m1542m1552.databasesystems.repository.UserRepository;

@Service
public class QueryService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    public List<QueryResult> query1(String startDate, String endDate) {

        ProjectionOperation projectDateStage = Aggregation.project("_id", "typeOfServiceRequest")
                .andExpression("creationDate").dateAsFormattedString("%Y-%m-%d").as("formatedDate");
        MatchOperation matchStage = Aggregation.match(new Criteria("formatedDate").gte(startDate).lte(endDate));
        GroupOperation groupByStageAndCount = group("typeOfServiceRequest").count().as("total");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("typeOfServiceRequest")
                .andInclude("total");
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "total"));

        Aggregation aggregation = newAggregation(projectDateStage, matchStage, groupByStageAndCount, projectStage,
                sortByStage);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class)
                .getMappedResults();

        return result;
    }

    // _id : {year: { $year: "$creationDate" }, month: { $month: "$creationDate" },
    // day: { $dayOfMonth: "$creationDate" }},
    // yearMonthDay: { $dateToString: { format: "%Y-%m-%d", date: "$date" } }
    // mongodb query
    // db.requests.aggregate([
    // { $match: {"typeOfServiceRequest": "Tree Trim", "policeDistrict": 8}},
    // { $group : {
    // _id : {yearMonthDay: { $dateToString: { format: "%Y-%m-%d", date:
    // "$creationDate" }}},
    // count: { $sum: 1 }
    // }
    // }
    // ])

    // Criteria criteria = Criteria.where("user._id").is(paramuserid).andOperator(
    // Criteria.where("datetime").gte(paramfirstdate),
    // Criteria.where("datetime").lt(paramseconddate)
    // );
    // AggregationOperation match = Aggregation.match(criteria);

    // List<AggregationOperation> aggregationoperations = new ArrayList
    // <AggregationOperation>();
    // aggregationoperations.add(match);
    // aggregationoperations.add(Aggregation.project("ip","datetime").andExpression("datetime").dateAsFormattedString("%d-%m-%Y").as("formateddate"));
    // aggregationoperations.add(Aggregation.group("ip","formateddate").count().as("TOT"));
    // AggregationResults<?> aggregationresults =
    // this.mngT.aggregate(Aggregation.newAggregation(aggregationoperations),
    // Login.class, Object.class);

    public List<QueryResult> query2(String typeOfServiceRequest, Date creationDate, Date completionDate) {
        MatchOperation filter = match(Criteria.where("creationDate").gte(creationDate).lte(completionDate)
                .and("typeOfServiceRequest").is(typeOfServiceRequest));

        // ProjectionOperation projectStage = Aggregation.project("creationDate",
        // "typeOfServiceRequest").andExpression("_id").as("typeOfServiceRequest").andInclude("total");
        ProjectionOperation p = Aggregation.project().andExpression("creationDate").dateAsFormattedString("%Y-%m-%d")
                .as("formatedDate");

        // GroupOperation groupByServiceRequest =
        // group("formatedDate").count().as("total");

        Aggregation aggregation = newAggregation(filter, p); // , groupByServiceRequest); //, sortByPopDesc);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class)
                .getMappedResults();

        return result;
    }

    public List<QueryResult> query3(String date) {

        ProjectionOperation projectDateStage = Aggregation.project("_id", "zipCode", "typeOfServiceRequest").andExpression("creationDate")
                .dateAsFormattedString("%Y-%m-%d").as("formatedDate");
        MatchOperation matchStage = Aggregation.match(new Criteria("formatedDate").is(date));
        GroupOperation groupByStageAndCount = group("zipCode", "typeOfServiceRequest").count().as("total");
        GroupOperation groupByStageAndPush = group("_id.zipCode").push(new BasicDBObject("type", "$_id.typeOfServiceRequest").append("total", "$total")).as("requestTypes");
        UnwindOperation typesUnwindOperation = Aggregation.unwind("requestTypes");
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "_id", "requestTypes.total"));
        GroupOperation groupByStageAndPushAgain = group("_id").push("requestTypes").as("requestTypes");
        ProjectionOperation projectStageAndSlice = Aggregation.project().and("_id").as("zipCode").and("requestTypes").slice(3).as("requestTypes");

        Aggregation aggregation = newAggregation(projectDateStage, matchStage, groupByStageAndCount, groupByStageAndPush, typesUnwindOperation, sortByStage, groupByStageAndPushAgain, projectStageAndSlice);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();
		
		return result;
    }

    public List<QueryResult> query5(String startDate, String endDate) {

        ProjectionOperation projectDateStage = Aggregation.project("_id", "typeOfServiceRequest", "creationDate", "completionDate").andExpression("creationDate").dateAsFormattedString("%Y-%m-%d").as("formatedDate");
        MatchOperation matchStage = Aggregation.match(new Criteria("formatedDate").gte(startDate).lte(endDate));
        ProjectionOperation projectAndSubtractStage = Aggregation.project("_id", "typeOfServiceRequest").and("completionDate").minus("creationDate").as("completionTime");
        GroupOperation groupByStageAndAvg = group("typeOfServiceRequest").avg("completionTime").as("avgCompletionTime");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("typeOfServiceRequest").andInclude("avgCompletionTime");

        Aggregation aggregation = newAggregation(projectDateStage, matchStage, projectAndSubtractStage, groupByStageAndAvg, projectStage);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();
		
		return result;
    }

    public List<Request> query7(String date) {

        ProjectionOperation projectDateStage = Aggregation.project("upvotes", "upvotesCount").andExpression("creationDate").dateAsFormattedString("%Y-%m-%d").as("formatedDate");
        MatchOperation matchStage = Aggregation.match(new Criteria("formatedDate").is(date));    
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "upvotesCount"));
        LimitOperation limitToFirstFifty = limit(50);

        Aggregation aggregation = newAggregation(projectDateStage, matchStage, sortByStage, limitToFirstFifty);

        List<Request> result = mongoTemplate.aggregate(aggregation, "requests", Request.class).getMappedResults();
		
		return result;
    }

    public List<QueryResult> query9() {

        UnwindOperation upvotesUnwindOperation = Aggregation.unwind("upvotes");
        GroupOperation groupByStage = group("_id").addToSet("upvotes.ward").as("wards");
        UnwindOperation wardsUnwindOperation = Aggregation.unwind("wards");
        GroupOperation groupByStageAndCount = group("_id").count().as("total");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("userId").andInclude("total");    
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "total"));
        LimitOperation limitToFirstFifty = limit(50);

        Aggregation aggregation = newAggregation(upvotesUnwindOperation, groupByStage, wardsUnwindOperation, groupByStageAndCount, projectStage, sortByStage, limitToFirstFifty);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "users", QueryResult.class).getMappedResults();
		
		return result;
    }

    public QueryResult query11(String fullName) {

        MatchOperation matchStage = Aggregation.match(new Criteria("fullName").is(fullName));
        UnwindOperation upvotesUnwindOperation = Aggregation.unwind("upvotes");
        GroupOperation groupByStage = group("_id").addToSet("upvotes.ward").as("wards");

        Aggregation aggregation = newAggregation(matchStage, upvotesUnwindOperation, groupByStage);

        QueryResult result = mongoTemplate.aggregate(aggregation, "users", QueryResult.class).getUniqueMappedResult();
		
		return result;
    }

}
