package com.example.ilham.smartmuseum.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MuseumItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("muesum_name")
    @Expose
    private String muesumName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMuesumName() {
        return muesumName;
    }

    public void setMuesumName(String muesumName) {
        this.muesumName = muesumName;
    }

}
