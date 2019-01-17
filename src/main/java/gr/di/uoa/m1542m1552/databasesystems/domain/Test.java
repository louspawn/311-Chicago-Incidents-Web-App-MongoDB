package gr.di.uoa.m1542m1552.databasesystems.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "test")
public class Test {
    @Id
    private String id;

    @NotNull
    private String testString;

    @NotNull
    private boolean testBoolean;


    public Test() {}

    public Test(String testString, boolean testBoolean) {
        this.testString = testString;
        this.testBoolean = testBoolean;
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
     * @return the testBoolean
     */
    public boolean isTestBoolean() {
        return testBoolean;
    }

    /**
     * @param testBoolean the testBoolean to set
     */
    public void setTestBoolean(boolean testBoolean) {
        this.testBoolean = testBoolean;
    }

    
}
