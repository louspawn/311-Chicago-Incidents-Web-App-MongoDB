package gr.di.uoa.m1542m1552.databasesystems.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

    public Date getTimeOfDay(Date date, int hours, int minutes, int seconds, int milliseconds) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
}

    public List<QueryResult> query1(Date startDate, Date endDate) {

        MatchOperation matchStage = Aggregation.match(new Criteria("creationDate").gte(startDate).lte(endDate));
        GroupOperation groupByStageAndCount = group("typeOfServiceRequest").count().as("total");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("typeOfServiceRequest") .andInclude("total");
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "total"));

        Aggregation aggregation = newAggregation(matchStage, groupByStageAndCount, projectStage, sortByStage);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class) .getMappedResults();

        return result;
    }

    public List<QueryResult> query2(String typeOfServiceRequest, Date creationDate, Date completionDate) {
        MatchOperation filter = match(Criteria.where("creationDate").gte(creationDate).lte(completionDate)
                                      .and("typeOfServiceRequest").is(typeOfServiceRequest));

        ProjectionOperation projectStage = Aggregation.project().andExpression("creationDate").dateAsFormattedString("%Y-%m-%d").as("formattedDate");
        GroupOperation groupByDate = group("formattedDate").count().as("total");
        ProjectionOperation projectStage2 = Aggregation.project().andExpression("_id").as("date").andInclude("total");
        SortOperation sortByDate = sort(new Sort(Sort.Direction.DESC, "date"));
        
        Aggregation aggregation = newAggregation(filter, projectStage, groupByDate, projectStage2, sortByDate);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();

		return result;
    }

    // public List<QueryResult> query2(String typeOfServiceRequest, Date creationDate, Date completionDate) {
    //     MatchOperation filter = match(Criteria.where("creationDate").gte(creationDate).lte(completionDate)
    //                                           .and("typeOfServiceRequest").is(typeOfServiceRequest));

    //     ProjectionOperation p = Aggregation.project().andExpression("creationDate").dateAsFormattedString("%Y-%m-%d").as("formatedDate");

    //     Aggregation aggregation = newAggregation(filter, p); // , groupByServiceRequest); //, sortByPopDesc);

    //     List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();

    //     return result;
    // }

    public List<QueryResult> query3(Date date) {
        Date startOfDay = getTimeOfDay(date, 0, 0, 0, 0);
        Date endOfDay = getTimeOfDay(date, 23, 59, 59, 999);

        MatchOperation matchStage = Aggregation.match(new Criteria("creationDate").gte(startOfDay).lte(endOfDay));
        GroupOperation groupByStageAndCount = group("zipCode", "typeOfServiceRequest").count().as("total");
        GroupOperation groupByStageAndPush = group("_id.zipCode").push(new BasicDBObject("type", "$_id.typeOfServiceRequest")
                                             .append("total", "$total"))
                                             .as("requestTypes"); 
        UnwindOperation typesUnwindOperation = Aggregation.unwind("requestTypes");
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "_id", "requestTypes.total"));
        GroupOperation groupByStageAndPushAgain = group("_id").push("requestTypes").as("requestTypes");
        ProjectionOperation projectStageAndSlice = Aggregation.project().and("_id").as("zipCode").and("requestTypes")
                                                               .slice(3).as("requestTypes");

        Aggregation aggregation = newAggregation(matchStage, groupByStageAndCount,
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

    public List<QueryResult> query5(Date startDate, Date endDate) {

        MatchOperation matchStage = Aggregation.match(new Criteria("creationDate").gte(startDate).lte(endDate));
        ProjectionOperation projectAndSubtractStage = Aggregation.project("_id", "typeOfServiceRequest").and("completionDate")
                                                                 .minus("creationDate").as("completionTime");
        GroupOperation groupByStageAndAvg = group("typeOfServiceRequest").avg("completionTime").as("avgCompletionTime");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("typeOfServiceRequest").andInclude("avgCompletionTime");

        Aggregation aggregation = newAggregation(matchStage, projectAndSubtractStage, groupByStageAndAvg, projectStage);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();

        return result;
    }

    public List<QueryResult> query6(Date date, Double long1, Double lat1, Double long2, Double lat2) {
        Box box = new Box(new Point(long1, lat1), new Point(long2, lat2));

        Date startOfDay = getTimeOfDay(date, 0, 0, 0, 0);
        Date endOfDay = getTimeOfDay(date, 23, 59, 59, 999);

        MatchOperation filter = match(Criteria.where("creationDate").gte(startOfDay).lte(endOfDay).and("geoLocation").within(box));
        GroupOperation groupByServiceRequest = group("typeOfServiceRequest").count().as("total");
        ProjectionOperation projectStage2 = Aggregation.project().andExpression("_id").as("typeOfServiceRequest") .andInclude("total");
        SortOperation sortByDate = sort(new Sort(Sort.Direction.DESC, "total"));
        AggregationOperation limit = Aggregation.limit(1);

        Aggregation aggregation = newAggregation(filter, groupByServiceRequest, projectStage2, sortByDate, limit);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class) .getMappedResults();

        return result;
    }

    public List<QueryResult> query7(Date date) {

        Date startOfDay = getTimeOfDay(date, 0, 0, 0, 0);
        Date endOfDay = getTimeOfDay(date, 23, 59, 59, 999);

        MatchOperation matchStage = Aggregation.match(new Criteria("creationDate").gte(startOfDay).lte(endOfDay));
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "upvotesCount"));
        LimitOperation limitToFirstFifty = limit(50);
        ProjectionOperation projectStage = Aggregation.project("upvotesCount").and("_id").as("requestId");

        Aggregation aggregation = newAggregation(matchStage, sortByStage, limitToFirstFifty, projectStage);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();

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
