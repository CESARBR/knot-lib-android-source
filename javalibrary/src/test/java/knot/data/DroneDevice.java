package knot.data;

import com.google.gson.annotations.SerializedName;

import br.org.cesar.knot.ThingDevice;

public class DroneDevice extends ThingDevice<DroneData> {

    @SerializedName("color")
    private int mColor;

    public DroneDevice() {
    }

    public DroneDevice(String id, String token) {
        super(id, token);
    }

    public DroneDevice(String id, String token, int color) {
        super(id, token);
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public String toString() {
        return "DroneDevice{" +
                "mColor=" + mColor +
                "} " + super.toString();
    }
}
