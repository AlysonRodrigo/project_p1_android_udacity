package alysonrodrigo.com.br.filmepopular.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alyson on 24/03/17.
 */

public class Movie implements Serializable{

    private String title_movie;
    private String url_movie;
    private String synopsis_movie;
    private String user;
    private String release_date;

    public String getTitle_movie() {
        return title_movie;
    }

    public void setTitle_movie(String title_movie) {
        this.title_movie = title_movie;
    }

    public String getUrl_movie() {
        return url_movie;
    }

    public void setUrl_movie(String url_movie) {
        this.url_movie = url_movie;
    }

    public String getSynopsis_movie() {
        return synopsis_movie;
    }

    public void setSynopsis_movie(String synopsis_movie) {
        this.synopsis_movie = synopsis_movie;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


}
