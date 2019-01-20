package gr.di.uoa.m1542m1552.databasesystems.domain;

public class UserUpvote {

    String requestId;

    Integer ward;

    public UserUpvote(String requestId, Integer ward) {
        this.requestId = requestId;
        this.ward = ward;
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


}
