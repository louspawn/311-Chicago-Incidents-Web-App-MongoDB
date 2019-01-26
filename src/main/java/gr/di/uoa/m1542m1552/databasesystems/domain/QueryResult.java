package gr.di.uoa.m1542m1552.databasesystems.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import gr.di.uoa.m1542m1552.databasesystems.enumerations.TypeOfServiceRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResult {

    private String typeOfServiceRequest;

    private Integer total;    

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

}
