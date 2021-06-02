package com.solar.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FetchAllSolarProductList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("Discountprices")
    @Expose
    private String discountprices;
    @SerializedName("Simage")
    @Expose
    private String simage;
    @SerializedName("OrigonalPrice")
    @Expose
    private String origonalPrice;
    @SerializedName("discountNotes")
    @Expose
    private String discountNotes;
    @SerializedName("discountAvaliabel")
    @Expose
    private String discountAvaliabel;

    /**
     * No args constructor for use in serialization
     *
     */
    public FetchAllSolarProductList() {
    }

    /**
     *
     * @param duration
     * @param discountprices
     * @param discountNotes
     * @param discountAvaliabel
     * @param description
     * @param origonalPrice
     * @param id
     * @param title
     * @param simage
     */
    public FetchAllSolarProductList(String id, String title, String description, String duration, String discountprices, String simage, String origonalPrice, String discountNotes, String discountAvaliabel) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.discountprices = discountprices;
        this.simage = simage;
        this.origonalPrice = origonalPrice;
        this.discountNotes = discountNotes;
        this.discountAvaliabel = discountAvaliabel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDiscountprices() {
        return discountprices;
    }

    public void setDiscountprices(String discountprices) {
        this.discountprices = discountprices;
    }

    public String getSimage() {
        return simage;
    }

    public void setSimage(String simage) {
        this.simage = simage;
    }

    public String getOrigonalPrice() {
        return origonalPrice;
    }

    public void setOrigonalPrice(String origonalPrice) {
        this.origonalPrice = origonalPrice;
    }

    public String getDiscountNotes() {
        return discountNotes;
    }

    public void setDiscountNotes(String discountNotes) {
        this.discountNotes = discountNotes;
    }

    public String getDiscountAvaliabel() {
        return discountAvaliabel;
    }

    public void setDiscountAvaliabel(String discountAvaliabel) {
        this.discountAvaliabel = discountAvaliabel;
    }

}