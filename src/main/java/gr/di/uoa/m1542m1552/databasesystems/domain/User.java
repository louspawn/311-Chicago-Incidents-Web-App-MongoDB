package gr.di.uoa.m1542m1552.databasesystems.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String fullName;

    private String telephoneNumber;

    private String address;

    /* Upvotes attributes */

    private List<UserUpvote> upvotes = new ArrayList<UserUpvote>();

    @Indexed
    private Integer upvotesCount = 0;

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
     * @return the upvotes
     */
    public List<UserUpvote> getUpvotes() {
        return upvotes;
    }

    /**
     * @param upvotes the upvotes to set
     */
    public void setUpvotes(List<UserUpvote> upvotes) {
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
