package gr.di.uoa.m1542m1552.databasesystems.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import gr.di.uoa.m1542m1552.databasesystems.domain.QueryResult;
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

    public Date getTimeOfDay(Date date, int hours, int minutes, int seconds, int milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        return calendar.getTime();
    }

    public List<QueryResult> query1(Date startDate, Date endDate) {

        MatchOperation matchStage = Aggregation.match(new Criteria("creationDate").gte(startDate).lte(endDate));
        GroupOperation groupByStageAndCount = group("typeOfServiceRequest").count().as("total");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("typeOfServiceRequest").andInclude("total");
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "total"));

        Aggregation aggregation = newAggregation(matchStage, groupByStageAndCount, projectStage, sortByStage);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();
		
		return result;
    }



            // _id : {year: { $year: "$creationDate" }, month: { $month: "$creationDate" }, day: { $dayOfMonth: "$creationDate" }},
            // yearMonthDay: { $dateToString: { format: "%Y-%m-%d", date: "$date" } }
    // mongodb query
    // db.requests.aggregate([
    //     { $match: {"typeOfServiceRequest": "Tree Trim", "policeDistrict": 8}},
    //     { $group : {
    //         _id : {yearMonthDay: { $dateToString: { format: "%Y-%m-%d", date: "$creationDate" }}},
    //         count: { $sum: 1 }
    //     }
    //     }
    // ])

    // Criteria criteria = Criteria.where("user._id").is(paramuserid).andOperator(
    //         Criteria.where("datetime").gte(paramfirstdate),
    //         Criteria.where("datetime").lt(paramseconddate)
    //                 );
    // AggregationOperation match = Aggregation.match(criteria);   

    // List<AggregationOperation> aggregationoperations = new ArrayList <AggregationOperation>();  
    // aggregationoperations.add(match);
    // aggregationoperations.add(Aggregation.project("ip","datetime").andExpression("datetime").dateAsFormattedString("%d-%m-%Y").as("formateddate"));
    // aggregationoperations.add(Aggregation.group("ip","formateddate").count().as("TOT"));
    // AggregationResults<?> aggregationresults = this.mngT.aggregate(Aggregation.newAggregation(aggregationoperations), Login.class, Object.class);

    
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

    public List<QueryResult> query3(Date date) {
        Date startOfDay = getTimeOfDay(date, 0, 0, 0, 0);
        Date endOfDay = getTimeOfDay(date, 23, 59, 59, 999);
        System.out.println(startOfDay.toString());
        System.out.println(endOfDay.toString());

        MatchOperation matchStage = Aggregation.match(new Criteria("creationDate").gte(startOfDay).lte(endOfDay));
        GroupOperation groupByStageAndCount = group("zipCode").count().as("total");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("zipCode").andInclude("total");
        SortOperation sortByStage = sort(new Sort(Sort.Direction.DESC, "total"));

        Aggregation aggregation = newAggregation(matchStage, groupByStageAndCount, projectStage, sortByStage);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();

        if (result.size() > 3) {
            result = result.size() > 0 ? result.subList(0, 3) : result;
        }
		
		return result;
    }

    public List<QueryResult> query4(String typeOfServiceRequest) {
        MatchOperation filter = match(Criteria.where("typeOfServiceRequest").is(typeOfServiceRequest).and("ward").gt(0));
        GroupOperation groupByWard = group("ward").count().as("total");
        ProjectionOperation projectStage = Aggregation.project().andExpression("_id").as("ward").andInclude("total");
        SortOperation sortByWard = sort(new Sort(Sort.Direction.ASC, "total"));
        AggregationOperation limit = Aggregation.limit(3);

        Aggregation aggregation = newAggregation(filter, groupByWard, projectStage, sortByWard, limit);

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();

		return result;
    }

    public List<QueryResult> query6(String date, Double long1, Double lat1, Double long2, Double lat2) {
        Box box = new Box(new Point(long1, lat1), new Point(long2, lat2));

        ProjectionOperation projectStage = Aggregation.project("typeOfServiceRequest", "geoLocation").andExpression("creationDate").dateAsFormattedString("%Y-%m-%d").as("formatedDate");
        MatchOperation filter = match(Criteria.where("formatedDate").is(date).and("geoLocation").within(box));
        GroupOperation groupByServiceRequest = group("typeOfServiceRequest").count().as("total");
        ProjectionOperation projectStage2 = Aggregation.project().andExpression("_id").as("typeOfServiceRequest").andInclude("total");
        SortOperation sortByDate = sort(new Sort(Sort.Direction.DESC, "total"));
        AggregationOperation limit = Aggregation.limit(1);
        
        Aggregation aggregation = newAggregation(projectStage, filter, groupByServiceRequest, projectStage2, sortByDate, limit);

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

    public List<QueryResult> query10() {
        MatchOperation filter = Aggregation.match(Criteria.where("upvotesCount").gt(1));
        UnwindOperation unwind = Aggregation.unwind("upvotes");
        GroupOperation groupByIdTel = Aggregation.group("_id", "upvotes.telephoneNumber").count().as("total");
        MatchOperation filter2 = Aggregation.match(Criteria.where("total").gte(2));
        GroupOperation groupById= Aggregation.group("_id._id");
        ProjectionOperation project= Aggregation.project().andExpression("_id").as("requestId");

        Aggregation aggregation = newAggregation(filter, unwind, groupByIdTel, filter2, groupById, project).
                                    withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build());

        List<QueryResult> result = mongoTemplate.aggregate(aggregation, "requests", QueryResult.class).getMappedResults();

		return result;
    }

}