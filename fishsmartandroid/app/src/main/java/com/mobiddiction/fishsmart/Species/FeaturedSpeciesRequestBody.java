package com.mobiddiction.fishsmart.Species;

public class FeaturedSpeciesRequestBody {

    public Boolean featured;
    public Integer type_id;

    public FeaturedSpeciesRequestBody(Boolean mfeatured, Integer mtype_id){
        this.featured = mfeatured;
        this.type_id = mtype_id;
    }
}
