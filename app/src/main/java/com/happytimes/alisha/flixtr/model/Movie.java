package com.happytimes.alisha.flixtr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alishaalam on 7/19/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "page",
        "results"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class Movie {


    @JsonProperty("page")
    private long page;
    @JsonProperty("results")
    private List<Result> results = new ArrayList<Result>();

    public Movie() {
    }

    public Movie(long page, List<Result> results) {
        this.page = page;
        this.results = results;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
