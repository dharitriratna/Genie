package ratna.genie1.user.genie.Model;

/**
 * Created by RatnaDev008 on 11/2/2018.
 */

public class TrainSeatAvailableModel {
    String trainName;
    String destPlaceName;
    String arrivPlaceName;
    String destTime;

    public TrainSeatAvailableModel(){}
    public TrainSeatAvailableModel(String trainName, String destPlaceName, String arrivPlaceName, String destTime, String arriveTime) {
        this.trainName = trainName;
        this.destPlaceName = destPlaceName;
        this.arrivPlaceName = arrivPlaceName;
        this.destTime = destTime;
        this.arriveTime = arriveTime;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getDestPlaceName() {
        return destPlaceName;
    }

    public void setDestPlaceName(String destPlaceName) {
        this.destPlaceName = destPlaceName;
    }

    public String getArrivPlaceName() {
        return arrivPlaceName;
    }

    public void setArrivPlaceName(String arrivPlaceName) {
        this.arrivPlaceName = arrivPlaceName;
    }

    public String getDestTime() {
        return destTime;
    }

    public void setDestTime(String destTime) {
        this.destTime = destTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    String arriveTime;
}
