package models.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.validation.Constraints;

public class Category {
    //Mongo DB identifiers
    private static final String M_CATEGORY = "category";

    @MongoObjectId
    private String _id;

    @Constraints.Required
    private String category;

    public Category(String id, String category){
        this._id = id;
        this.category = category.toLowerCase();
        prepareForStorage();
    }

    @JsonCreator
    public Category(@JsonProperty(M_CATEGORY) String category){
        this.category = category;
        prepareForStorage();
    }

    public String get_id(){
        return _id;
    }

    public void set_id(String _id){this._id = _id;}

    @JsonProperty(M_CATEGORY)
    public String getCategory(){
        String category = this.category;
        return category.substring(0,1).toUpperCase() + category.substring(1);
    }

    public void setCategory(String category){this.category = category.toLowerCase();}

    private void prepareForStorage() {
        this.category = category.toLowerCase();
    }
}
