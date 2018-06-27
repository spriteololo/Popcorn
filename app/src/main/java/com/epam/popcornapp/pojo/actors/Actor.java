package com.epam.popcornapp.pojo.actors;

import com.epam.popcornapp.pojo.actors.credits.Credits;
import com.epam.popcornapp.pojo.actors.images.Images;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Actor extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;

    @SerializedName("adult")
    @Expose
    private boolean adult;

    @SerializedName("also_known_as")
    @Expose
    private RealmList<String> alsoKnownAs = null;

    @SerializedName("biography")
    @Expose
    private String biography;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("deathday")
    @Expose
    private String deathday;

    @SerializedName("gender")
    @Expose
    private int gender;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("combined_credits")
    @Expose
    private Credits credits;

    @SerializedName("place_of_birth")
    @Expose
    private String placeOfBirth;

    @SerializedName("popularity")
    @Expose
    private double popularity;

    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    @SerializedName("known_for")
    @Expose
    @Ignore
    private List<KnownFor> knownFor = null;

    private RealmList<KnownForLink> knownForLink = null;

    private Images images = null;

    public RealmList<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(final RealmList<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(final String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(final String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(final String deathday) {
        this.deathday = deathday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(final int gender) {
        this.gender = gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(final String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(final String imdbId) {
        this.imdbId = imdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(final String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(final double popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(final String profilePath) {
        this.profilePath = profilePath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(final boolean adult) {
        this.adult = adult;
    }

    public List<KnownFor> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(final List<KnownFor> knownFor) {
        this.knownFor = knownFor;
    }

    public RealmList<KnownForLink> getKnownForLink() {
        return knownForLink;
    }

    public void setKnownForLink(final RealmList<KnownForLink> knownForLink) {
        this.knownForLink = knownForLink;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(final Images images) {
        this.images = images;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(final Credits credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return ("id = " + getId() + "\n") +
                "name = " + getName() + "\n" +
                "biography = " + getBiography() + "\n" +
                "birthday = " + getBirthday() + "\n";
    }
}