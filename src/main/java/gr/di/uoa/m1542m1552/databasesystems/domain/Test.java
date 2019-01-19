package gr.di.uoa.m1542m1552.databasesystems.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import gr.di.uoa.m1542m1552.databasesystems.enumerations.Status;
import gr.di.uoa.m1542m1552.databasesystems.enumerations.TypeOfServiceRequest;

@Document(collection = "test")
public class Test {
    @Id
    private String id;

    private String testString;

    private Date date;

    private String typeOfServiceRequest;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint geoLonLat;

    private String status;

    public Test() {}

    public Test(String testString, boolean testBoolean) {
        this.testString = testString;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the testString
     */
    public String getTestString() {
        return testString;
    }

    /**
     * @param testString the testString to set
     */
    public void setTestString(String testString) {
        this.testString = testString;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the typeOfServiceRequest
     */
    public TypeOfServiceRequest getTypeOfServiceRequest() {
        return TypeOfServiceRequest.fromString(typeOfServiceRequest);
    }

    /**
     * @param typeOfServiceRequest the typeOfServiceRequest to set
     */
    public void setTypeOfServiceRequest(TypeOfServiceRequest typeOfServiceRequest) {
        this.typeOfServiceRequest = typeOfServiceRequest.getText();
    }

    /**
     * @return the geoLonLat
     */
    public GeoJsonPoint getGeoLonLat() {
        return geoLonLat;
    }

    /**
     * @param geoLonLat the geoLonLat to set
     */
    public void setGeoLonLat(GeoJsonPoint geoLonLat) {
        this.geoLonLat = geoLonLat;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return Status.fromString(status);
    }

    /**
     * @param status the status to set
     */
    public void getStatus(Status status) {
        this.status = status.getText();
    }

}
