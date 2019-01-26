package gr.di.uoa.m1542m1552.databasesystems.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import gr.di.uoa.m1542m1552.databasesystems.enumerations.TypeOfServiceRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResult {

    private String typeOfServiceRequest;

    private Integer total;

    private Integer zipCode;

    private Long avgCompletionTime;

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

}
