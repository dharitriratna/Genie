package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/23/2018.
 */

public class Cites {
    public destinationInput getDestinationInput() {
        return DestinationInput;
    }

    public void setDestinationInput(destinationInput destinationInput) {
        DestinationInput = destinationInput;
    }

    @SerializedName("DestinationInput")
    private destinationInput DestinationInput;
    public class destinationInput{
        public int getOriginId() {
            return OriginId;
        }

        public void setOriginId(int originId) {
            OriginId = originId;
        }

        @SerializedName("OriginId")
        private int OriginId;
    }
}
