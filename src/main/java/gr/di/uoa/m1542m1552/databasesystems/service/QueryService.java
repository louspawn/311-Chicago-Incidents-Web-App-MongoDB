package gr.di.uoa.m1542m1552.databasesystems.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
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

@Service
public class QueryService {
    @Autowired
    MongoTemplate mongoTemplate;

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

    public List<QueryResult> query2(String typeOfServiceRequest, Date creationDate, Date completionDate) {
        MatchOperation filter = match(Criteria.where("creationDate").gte(creationDate).lte(completionDate)
                                              .and("typeOfServiceRequest").is(typeOfServiceRequest));

        ProjectionOperation p = Aggregation.project().andExpression("creationDate").dateAsFormattedString("%Y-%m-%d").as("formatedDate");

        Aggregation aggregation = newAggregation(filter, p); // , groupByServiceRequest); //, sortByPopDesc);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();

        return result;
    }

    public List<QueryResult> query3(String date) {
        ProjectionOperation projectDateStage = Aggregation.project("_id", "zipCode", "typeOfServiceRequest")
                                                          .andExpression("creationDate")
                                                          .dateAsFormattedString("%Y-%m-%d").as("formatedDate");
        MatchOperation matchStage = Aggregation.match(new Criteria("formatedDate").is(date));
        GroupOperation groupByStageAndCount = group("zipCode", "typeOfServiceRequest").count().as("total");
        GroupOperation groupByStageAndPush = group("_id.zipCode").push(new BasicDBObject("type", "$_id.typeOfServiceRequest")
                                             .append("total", "$total"))
                                             .as("requestTypes"); 
        UnwindOperation typesUnwindOperation = Aggregation.unwind("requestTypes");
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "_id", "requestTypes.total"));
        GroupOperation groupByStageAndPushAgain = group("_id").push("requestTypes").as("requestTypes");
        ProjectionOperation projectStageAndSlice = Aggregation.project().and("_id").as("zipCode").and("requestTypes")
                                                               .slice(3).as("requestTypes");

        Aggregation aggregation = newAggregation(projectDateStage, matchStage, groupByStageAndCount,
                                                 groupByStageAndPush, typesUnwindOperation, sortByStage, 
                                                 groupByStageAndPushAgain, projectStageAndSlice);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class) .getMappedResults();

        return result;
    }

    public List<QueryResult> query4(String typeOfServiceRequest) {
        MatchOperation filter = match( Criteria.where("typeOfServiceRequest").is(typeOfServiceRequest).and("ward").gt(0));
        GroupOperation groupByWard = group("ward").count().as("total");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("ward").andInclude("total");
        SortOperation sortByWard = sort(new Sort(Sort.Direction.ASC, "total"));
        AggregationOperation limit = Aggregation.limit(3);

        Aggregation aggregation = newAggregation(filter, groupByWard, projectStage, sortByWard, limit);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class) .getMappedResults();

        return result;
    }

    public List<QueryResult> query5(String startDate, String endDate) {
        ProjectionOperation projectDateStage = Aggregation.project("_id", "typeOfServiceRequest", "creationDate", "completionDate")
                                                           .andExpression("creationDate").dateAsFormattedString("%Y-%m-%d").as("formatedDate");
        MatchOperation matchStage = Aggregation.match(new Criteria("formatedDate").gte(startDate).lte(endDate));
        ProjectionOperation projectAndSubtractStage = Aggregation.project("_id", "typeOfServiceRequest").and("completionDate")
                                                                 .minus("creationDate").as("completionTime");
        GroupOperation groupByStageAndAvg = group("typeOfServiceRequest").avg("completionTime").as("avgCompletionTime");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("typeOfServiceRequest").andInclude("avgCompletionTime");

        Aggregation aggregation = newAggregation(projectDateStage, matchStage, projectAndSubtractStage, groupByStageAndAvg, projectStage);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();

        return result;
    }

    public List<QueryResult> query6(String date, Double long1, Double lat1, Double long2, Double lat2) {
        Box box = new Box(new Point(long1, lat1), new Point(long2, lat2));

        ProjectionOperation projectStage = Aggregation.project("typeOfServiceRequest", "geoLocation")
                                                      .andExpression("creationDate").dateAsFormattedString("%Y-%m-%d").as("formatedDate");
        MatchOperation filter = match(Criteria.where("formatedDate").is(date).and("geoLocation").within(box));
        GroupOperation groupByServiceRequest = group("typeOfServiceRequest").count().as("total");
        ProjectionOperation projectStage2 = Aggregation.project().andExpression("_id").as("typeOfServiceRequest") .andInclude("total");
        SortOperation sortByDate = sort(new Sort(Sort.Direction.DESC, "total"));
        AggregationOperation limit = Aggregation.limit(1);

        Aggregation aggregation = newAggregation(projectStage, filter, groupByServiceRequest, projectStage2, sortByDate, limit);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class) .getMappedResults();

        return result;
    }

    public List<Request> query7(String date) {
        ProjectionOperation projectDateStage = Aggregation.project("upvotes", "upvotesCount").andExpression("creationDate")
                                                          .dateAsFormattedString("%Y-%m-%d").as("formatedDate");
        MatchOperation matchStage = Aggregation.match(new Criteria("formatedDate").is(date));
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "upvotesCount"));
        LimitOperation limitToFirstFifty = limit(50);

        Aggregation aggregation = newAggregation(projectDateStage, matchStage, sortByStage, limitToFirstFifty);

        List<Request> result = mongoTemplate.aggregate(aggregation, "requests", Request.class).getMappedResults();

        return result;
    }

    public List<QueryResult> query8() {
        SortOperation sortByUpvotes = sort(new Sort(Sort.Direction.DESC, "upvotesCount"));
        ProjectionOperation projectStage = Aggregation.project("fullName", "address", "telephoneNumber", "upvotesCount");
        AggregationOperation limit = Aggregation.limit(50);

        Aggregation aggregation = newAggregation(sortByUpvotes, limit, projectStage);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "users", QueryResult.class).getMappedResults();

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

        Aggregation aggregation = newAggregation(upvotesUnwindOperation, groupByStage, wardsUnwindOperation,
                                                 groupByStageAndCount, projectStage, sortByStage, limitToFirstFifty);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "users", QueryResult.class).getMappedResults();

        return result;
    }

    public List<QueryResult> query10() {
        MatchOperation filter = Aggregation.match(Criteria.where("upvotesCount").gt(1));
        UnwindOperation unwind = Aggregation.unwind("upvotes");
        GroupOperation groupByIdTel = Aggregation.group("_id", "upvotes.telephoneNumber").count().as("total");
        MatchOperation filter2 = Aggregation.match(Criteria.where("total").gte(2));
        GroupOperation groupById = Aggregation.group("_id._id");
        ProjectionOperation project = Aggregation.project().andExpression("_id").as("requestId");

        Aggregation aggregation = newAggregation(filter, unwind, groupByIdTel, filter2, groupById, project)
                                                 .withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build());

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class) .getMappedResults();

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
