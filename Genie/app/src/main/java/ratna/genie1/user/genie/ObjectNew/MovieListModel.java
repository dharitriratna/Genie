package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/27/2018.
 */

public class MovieListModel {
    @SerializedName("Movie")
    private String Movie;
    @SerializedName("Status")
    private String Status;

    public String getMovie() {
        return Movie;
    }

    public void setMovie(String movie) {
        Movie = movie;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPosterURL() {
        return PosterURL;
    }

    public void setPosterURL(String posterURL) {
        PosterURL = posterURL;
    }

    @SerializedName("PosterURL")
    private String PosterURL;
}
