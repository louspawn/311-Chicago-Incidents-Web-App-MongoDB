package gr.di.uoa.m1542m1552.databasesystems.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import gr.di.uoa.m1542m1552.databasesystems.enumerations.TypeOfServiceRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResult {
    private String userId;
    private String typeOfServiceRequest;
    private Integer total;
    private Integer ward;
    private String date;
    private String fullName;
    private String address;
    private String telephoneNumber;
    private Integer upvotesCount;
    private Integer zipCode;
    private String requestId;
    private List<Integer> wards;
    private Long avgCompletionTime;
    private List<RequestType> requestTypes;
    
    /**
     * @return the ward
     */
    public Integer getWard() {
        return ward;
    }

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }
    
    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    /**
     * @return the telephoneNumber
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * @param telephoneNumber the telephoneNumber to set
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @param ward the ward to set
     */
    public void setWard(Integer ward) {
        this.ward = ward;
    }


    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the zipCode
     */
    public Integer getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return the wards
     */
    public List<Integer> getWards() {
        return wards;
    }

    /**
     * @param wards the wards to set
     */
    public void setWards(List<Integer> wards) {
        this.wards = wards;
    }

    /**
     * @return the avgCompletionTime
     */
    public Long getAvgCompletionTime() {
        return avgCompletionTime;
    }

    /**
     * @param avgCompletionTime the avgCompletionTime to set
     */
    public void setAvgCompletionTime(Long avgCompletionTime) {
        this.avgCompletionTime = avgCompletionTime;
    }

    /**
     * @return the requestTypes
     */
    public List<RequestType> getRequestTypes() {
        return requestTypes;
    }

    /**
     * @param requestTypes the requestTypes to set
     */
    public void setRequestTypes(List<RequestType> requestTypes) {
        this.requestTypes = requestTypes;
    }

}

class RequestType {

    private String type;
    private Integer total;

    /**
     * @return the type
     */
    public TypeOfServiceRequest getType() {
        return TypeOfServiceRequest.fromString(type);
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @param type the type to set
     */
    public void setType(TypeOfServiceRequest type) {
        this.type = type.getText();
    }
}
