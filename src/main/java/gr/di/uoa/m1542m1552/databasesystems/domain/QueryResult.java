package gr.di.uoa.m1542m1552.databasesystems.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import gr.di.uoa.m1542m1552.databasesystems.enumerations.TypeOfServiceRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResult {

    private String userId;
    private String typeOfServiceRequest;
    private Integer total;
    private String date;
    private Integer zipCode;
    private List<Integer> wards;
    private Long avgCompletionTime;
    private List<RequestType> requestTypes;

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
