package com.java.cows.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//Overrides collection name by 'cows'
@Document(collection = "cows")
public class Cow {
    @Id
    private String id;

    private String name;
    private String gender;
    private String description;
    private String image;
    private Boolean published;

    public Cow(){
    }

    public Cow(String name, String gender, String description, String image, Boolean published) {
        this.name = name;
        this.gender = gender;
        this.description = description;
        this.image = image;
        this.published = published;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public Boolean isPublished() {
        return published;
    }

    public void setPublished(Boolean isPublished) {
        this.published = isPublished;
    }

    @Override
    public String toString(){
        return "Cow [id=" + id + ", name=" + name + ", gender=" + gender + ", desc=" + description + ", image=" + image + ", published=" + published + "]";
    }


}
