package br.org.cesar.knot.data;

import com.google.gson.annotations.SerializedName;

import br.org.cesar.knot.lib.model.AbstractThingData;

public class WaterFountainData extends AbstractThingData {

    @SerializedName("mid")
    private int mFountainId;

    @SerializedName("w")
    private Double mWeight;

    public Double getWeight() {
        return mWeight;
    }

    public void setWeight(Double weight) {
        mWeight = weight;
    }

    public int getFountainId() {
        return mFountainId;
    }

    public void setFountainId(int fountainId) {
        mFountainId = fountainId;
    }
}
