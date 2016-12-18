package br.org.cesar.knot;

import com.google.gson.annotations.SerializedName;

public class ThingData {

    @SerializedName("timestamp")
    private String mCreatedAt;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    @Override
    public String toString() {
        return "ThingData{" +
                "mCreatedAt=" + mCreatedAt +
                '}';
    }
}
