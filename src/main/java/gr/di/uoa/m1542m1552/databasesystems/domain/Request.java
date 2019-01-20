package gr.di.uoa.m1542m1552.databasesystems.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import gr.di.uoa.m1542m1552.databasesystems.enumerations.Status;
import gr.di.uoa.m1542m1552.databasesystems.enumerations.TypeOfServiceRequest;

@Document(collection = "request")
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
    private GeoJsonPoint geoLonLat;

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

    private Integer daysReported;

    /* Garbage Carts Request attribute */

    private Integer numberOfBlackCartsDelivered;

    /* Graffiti Removal Request attributes */

    private String typeOfSurfaceIsOn;

    /* Pot Holes Request attribute */

    private Integer numberOfPotHolesFilledOnBlock;

    /* Rodent Baiting Request attributes */

    private Integer numberOfPremisesBaited;

    private Integer numberOfPremisesWithGarbage;

    private Integer numberOfPremisesWithRats;

    /* Sanitation Code Complaints Request attribute */

    private String natureOfCodeViolation;

    /* Upvotes attributes */

    private List<RequestUpvote> upvotes;

    private Integer upvoteNum;

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
     * @return the daysReported
     */
    public Integer getDaysReported() {
        return daysReported;
    }

    /**
     * @param daysReported the daysReported to set
     */
    public void setDaysReported(Integer daysReported) {
        this.daysReported = daysReported;
    }

    /**
     * @return the numberOfBlackCartsDelivered
     */
    public Integer getNumberOfBlackCartsDelivered() {
        return numberOfBlackCartsDelivered;
    }

    /**
     * @param numberOfBlackCartsDelivered the numberOfBlackCartsDelivered to set
     */
    public void setNumberOfBlackCartsDelivered(Integer numberOfBlackCartsDelivered) {
        this.numberOfBlackCartsDelivered = numberOfBlackCartsDelivered;
    }

    /**
     * @return the typeOfSurfaceIsOn
     */
    public String getTypeOfSurfaceIsOn() {
        return typeOfSurfaceIsOn;
    }

    /**
     * @param typeOfSurfaceIsOn the typeOfSurfaceIsOn to set
     */
    public void setTypeOfSurfaceIsOn(String typeOfSurfaceIsOn) {
        this.typeOfSurfaceIsOn = typeOfSurfaceIsOn;
    }

    /**
     * @return the numberOfPotHolesFilledOnBlock
     */
    public Integer getNumberOfPotHolesFilledOnBlock() {
        return numberOfPotHolesFilledOnBlock;
    }

    /**
     * @param numberOfPotHolesFilledOnBlock the numberOfPotHolesFilledOnBlock to set
     */
    public void setNumberOfPotHolesFilledOnBlock(Integer numberOfPotHolesFilledOnBlock) {
        this.numberOfPotHolesFilledOnBlock = numberOfPotHolesFilledOnBlock;
    }

    /**
     * @return the numberOfPremisesBaited
     */
    public Integer getNumberOfPremisesBaited() {
        return numberOfPremisesBaited;
    }

    /**
     * @param numberOfPremisesBaited the numberOfPremisesBaited to set
     */
    public void setNumberOfPremisesBaited(Integer numberOfPremisesBaited) {
        this.numberOfPremisesBaited = numberOfPremisesBaited;
    }

    /**
     * @return the numberOfPremisesWithGarbage
     */
    public Integer getNumberOfPremisesWithGarbage() {
        return numberOfPremisesWithGarbage;
    }

    /**
     * @param numberOfPremisesWithGarbage the numberOfPremisesWithGarbage to set
     */
    public void setNumberOfPremisesWithGarbage(Integer numberOfPremisesWithGarbage) {
        this.numberOfPremisesWithGarbage = numberOfPremisesWithGarbage;
    }

    /**
     * @return the numberOfPremisesWithRats
     */
    public Integer getNumberOfPremisesWithRats() {
        return numberOfPremisesWithRats;
    }

    /**
     * @param numberOfPremisesWithRats the numberOfPremisesWithRats to set
     */
    public void setNumberOfPremisesWithRats(Integer numberOfPremisesWithRats) {
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
     * @return the upvoteNum
     */
    public Integer getUpvoteNum() {
        return upvoteNum;
    }

    /**
     * @param upvoteNum the upvoteNum to set
     */
    public void setUpvoteNum(Integer upvoteNum) {
        this.upvoteNum = upvoteNum;
    }

}
