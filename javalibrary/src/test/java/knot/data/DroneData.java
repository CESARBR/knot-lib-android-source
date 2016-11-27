package knot.data;

import com.google.gson.annotations.SerializedName;

import br.org.cesar.knot.ThingData;

public class DroneData extends ThingData {

    @SerializedName("altitude")
    private long mAltitude;

    public long getAltitude() {
        return mAltitude;
    }

    public void setAltitude(long altitude) {
        mAltitude = altitude;
    }

    @Override
    public String toString() {
        return "DroneData{" +
                "mAltitude=" + mAltitude +
                "} " + super.toString();
    }
}
