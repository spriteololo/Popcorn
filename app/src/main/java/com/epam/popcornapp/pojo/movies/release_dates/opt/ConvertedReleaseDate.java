package com.epam.popcornapp.pojo.movies.release_dates.opt;

public class ConvertedReleaseDate {

    private String imagePath;
    private int type;
    private String date;
    private String constraint;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(final String imagePath) {
        this.imagePath = imagePath;
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(final String constraint) {
        this.constraint = constraint;
    }
}