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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import gr.di.uoa.m1542m1552.databasesystems.domain.QueryResult;
import gr.di.uoa.m1542m1552.databasesystems.domain.Request;
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

    public List<QueryResult> query2(String typeOfServiceRequest, Date creationDate, Date completionDate) {
        GroupOperation groupByServiceRequest = group("typeOfServiceRequest"); //.sum("pop").as("statePop");
        MatchOperation filterDates = match(new Criteria("creationDate").gte(creationDate).lte(completionDate));
        // SortOperation sortByPopDesc = sort(new Sort(Direction.DESC, "statePop"));
        
        Aggregation aggregation = newAggregation(groupByServiceRequest, filterDates); //, sortByPopDesc);

        // List<QueryResult> result = mongoTemplate.aggregate(aggregation, "zips", Request.class);
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
}
