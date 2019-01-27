package gr.di.uoa.m1542m1552.databasesystems.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import gr.di.uoa.m1542m1552.databasesystems.enumerations.Status;
import gr.di.uoa.m1542m1552.databasesystems.enumerations.TypeOfServiceRequest;

@Document(collection = "requests")
@JsonInclude(JsonInclude.Include.NON_NULL)
@CompoundIndexes({
    @CompoundIndex(name = "creationDate_typeOfServiceRequest", def = "{'creationDate' : 1, 'typeOfServiceRequest': 1}"),
    @CompoundIndex(name = "typeOfServiceRequest_creationDate", def = "{'typeOfServiceRequest' : 1, 'creationDate': 1}"),
    @CompoundIndex(name = "creationDate_zipCode", def = "{'creationDate' : 1, 'zipCode': 1}"),
    @CompoundIndex(name = "creationDate_upvotesCount", def = "{'creationDate' : 1, 'upvotesCount': 1}")
})
public class Request {

    /* Common request attributes */

    @Id
    private String id;

    private String serviceRequestNumber;

    private Date creationDate;

    private String status;

    private Date completionDate;

    private String typeOfServiceRequest;

    private String streetAddress;

    private Integer ward;

    private Integer policeDistrict;

    private Integer communityArea;

    private Integer zipCode;

    private Double xCoordinate;

    private Double yCoordinate;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint geoLocation;

    private String location;

    /* SSA attribute */

    private Integer ssa;

    /* Actions attributes */

    private String currentActivity;

    private String mostRecentAction;

    /* Location description attribute */

    private String whereIsLocated;

    /* Abandoned Vehicles Request attributes */

    private String licensePlate;

    private String vehicleModel;

    private String vehicleColor;

    private String daysAbandoned;

    /* Garbage Carts Request attribute */

    private Integer blackCartsDelivered;

    /* Graffiti Removal Request attributes */

    private String typeOfSurface;

    /* Pot Holes Request attribute */

    private String numberOfPotholes;

    /* Rodent Baiting Request attributes */

    private String numberOfPremisesBaited;

    private String numberOfPremisesWithGarbage;

    private String numberOfPremisesWithRats;

    /* Sanitation Code Complaints Request attribute */

    private String natureOfCodeViolation;

    /* Upvotes attributes */

    private List<RequestUpvote> upvotes = new ArrayList<RequestUpvote>();

    private Integer upvotesCount = 0;

    /* Getters and setters */

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceRequestNumber() {
        return this.serviceRequestNumber;
    }

    public void setServiceRequestNumber(String serviceRequestNumber) {
        this.serviceRequestNumber = serviceRequestNumber;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
    public void setStatus(Status status) {
        this.status = status.getText();
    }

    public Date getCompletionDate() {
        return this.completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
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

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * @return the ward
     */
    public Integer getWard() {
        return ward;
    }

    /**
     * @param ward the ward to set
     */
    public void setWard(Integer ward) {
        this.ward = ward;
    }

    /**
     * @return the policeDistrict
     */
    public Integer getPoliceDistrict() {
        return policeDistrict;
    }

    /**
     * @param policeDistrict the policeDistrict to set
     */
    public void setPoliceDistrict(Integer policeDistrict) {
        this.policeDistrict = policeDistrict;
    }

    /**
     * @return the communityArea
     */
    public Integer getCommunityArea() {
        return communityArea;
    }

    /**
     * @param communityArea the communityArea to set
     */
    public void setCommunityArea(Integer communityArea) {
        this.communityArea = communityArea;
    }

    public Integer getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Double getXCoordinate() {
        return this.xCoordinate;
    }

    public void setXCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getYCoordinate() {
        return this.yCoordinate;
    }

    public void setYCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * @return the geoLocation
     */
    public GeoJsonPoint getGeoLocation() {
        return geoLocation;
    }

    /**
     * @param geoLocation the geoLocation to set
     */
    public void setGeoLocation(GeoJsonPoint geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the ssa
     */
    public Integer getSsa() {
        return ssa;
    }

    /**
     * @param ssa the ssa to set
     */
    public void setSsa(Integer ssa) {
        this.ssa = ssa;
    }

    /**
     * @return the currentActivity
     */
    public String getCurrentActivity() {
        return currentActivity;
    }

    /**
     * @param currentActivity the currentActivity to set
     */
    public void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
    }

    /**
     * @return the mostRecentAction
     */
    public String getMostRecentAction() {
        return mostRecentAction;
    }

    /**
     * @param mostRecentAction the mostRecentAction to set
     */
    public void setMostRecentAction(String mostRecentAction) {
        this.mostRecentAction = mostRecentAction;
    }

    /**
     * @return the whereIsLocated
     */
    public String getWhereIsLocated() {
        return whereIsLocated;
    }

    /**
     * @param whereIsLocated the whereIsLocated to set
     */
    public void setWhereIsLocated(String whereIsLocated) {
        this.whereIsLocated = whereIsLocated;
    }

    /**
     * @return the licensePlate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * @param licensePlate the licensePlate to set
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * @return the vehicleModel
     */
    public String getVehicleModel() {
        return vehicleModel;
    }

    /**
     * @param vehicleModel the vehicleModel to set
     */
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    /**
     * @return the vehicleColor
     */
    public String getVehicleColor() {
        return vehicleColor;
    }

    /**
     * @param vehicleColor the vehicleColor to set
     */
    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    /**
     * @return the daysAbandoned
     */
    public String getDaysAbandoned() {
        return daysAbandoned;
    }

    /**
     * @param daysAbandoned the daysAbandoned to set
     */
    public void setDaysAbandoned(String daysAbandoned) {
        this.daysAbandoned = daysAbandoned;
    }

    /**
     * @return the blackCartsDelivered
     */
    public Integer getBlackCartsDelivered() {
        return blackCartsDelivered;
    }

    /**
     * @param blackCartsDelivered the blackCartsDelivered to set
     */
    public void seBlackCartsDelivered(Integer blackCartsDelivered) {
        this.blackCartsDelivered = blackCartsDelivered;
    }

    /**
     * @return the typeOfSurface
     */
    public String getTypeOfSurface() {
        return typeOfSurface;
    }

    /**
     * @param typeOfSurface the typeOfSurface to set
     */
    public void setTypeOfSurface(String typeOfSurface) {
        this.typeOfSurface = typeOfSurface;
    }

    /**
     * @return the numberOfPotholes
     */
    public String getNumberOfPotholes() {
        return numberOfPotholes;
    }

    /**
     * @param numberOfPotholes the numberOfPotholes to set
     */
    public void setNumberOfPotholes(String numberOfPotholes) {
        this.numberOfPotholes = numberOfPotholes;
    }

    /**
     * @return the numberOfPremisesBaited
     */
    public String getNumberOfPremisesBaited() {
        return numberOfPremisesBaited;
    }

    /**
     * @param numberOfPremisesBaited the numberOfPremisesBaited to set
     */
    public void setNumberOfPremisesBaited(String numberOfPremisesBaited) {
        this.numberOfPremisesBaited = numberOfPremisesBaited;
    }

    /**
     * @return the numberOfPremisesWithGarbage
     */
    public String getNumberOfPremisesWithGarbage() {
        return numberOfPremisesWithGarbage;
    }

    /**
     * @param numberOfPremisesWithGarbage the numberOfPremisesWithGarbage to set
     */
    public void setNumberOfPremisesWithGarbage(String numberOfPremisesWithGarbage) {
        this.numberOfPremisesWithGarbage = numberOfPremisesWithGarbage;
    }

    /**
     * @return the numberOfPremisesWithRats
     */
    public String getNumberOfPremisesWithRats() {
        return numberOfPremisesWithRats;
    }

    /**
     * @param numberOfPremisesWithRats the numberOfPremisesWithRats to set
     */
    public void setNumberOfPremisesWithRats(String numberOfPremisesWithRats) {
        this.numberOfPremisesWithRats = numberOfPremisesWithRats;
    }

    /**
     * @return the natureOfCodeViolation
     */
    public String getNatureOfCodeViolation() {
        return natureOfCodeViolation;
    }

    /**
     * @param natureOfCodeViolation the natureOfCodeViolation to set
     */
    public void setNatureOfCodeViolation(String natureOfCodeViolation) {
        this.natureOfCodeViolation = natureOfCodeViolation;
    }

    /**
     * @return the upvotes
     */
    public List<RequestUpvote> getUpvotes() {
        return upvotes;
    }

    /**
     * @param upvotes the upvotes to set
     */
    public void setUpvotes(List<RequestUpvote> upvotes) {
        this.upvotes = upvotes;
    }

    /**
     * @return the upvotesCount
     */
    public Integer getUpvotesCount() {
        return upvotesCount;
    }

    /**
     * @param upvotesCount the upvotesCount to set
     */
    public void setUpvotesCount(Integer upvotesCount) {
        this.upvotesCount = upvotesCount;
    }

}
