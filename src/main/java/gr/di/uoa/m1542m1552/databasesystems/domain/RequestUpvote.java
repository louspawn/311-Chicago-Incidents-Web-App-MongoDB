package gr.di.uoa.m1542m1552.databasesystems.domain;

public class RequestUpvote {

    String userId;

    String telephoneNumber;

    public RequestUpvote(String userId, String telephoneNumber) {
        this.userId = userId;
        this.telephoneNumber = telephoneNumber;
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

}
