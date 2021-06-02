package com.solar.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FetchAllSolarProductLists {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("fetchAllSolarProductLists")
    @Expose
    private List<FetchAllSolarProductList> fetchAllSolarProductLists = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public FetchAllSolarProductLists() {
    }

    /**
     *
     * @param error
     * @param fetchAllSolarProductLists
     */
    public FetchAllSolarProductLists(Boolean error, List<FetchAllSolarProductList> fetchAllSolarProductLists) {
        super();
        this.error = error;
        this.fetchAllSolarProductLists = fetchAllSolarProductLists;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<FetchAllSolarProductList> getFetchAllSolarProductLists() {
        return fetchAllSolarProductLists;
    }

    public void setFetchAllSolarProductLists(List<FetchAllSolarProductList> fetchAllSolarProductLists) {
        this.fetchAllSolarProductLists = fetchAllSolarProductLists;
    }

}