package fr.mixit;

import java.util.Map;

/**
 * @author mpetitdant
 *         Date: 08/02/17
 */
public class Session {
    private String name;
    private String language;
    private String format;
    private String track;
    private String description;
    private String references;
    private Integer difficulty;
    private String speakerName;
    private String speakerEmail;
    private String speakerLanguage;
    private String cospeakers;
    private String mean;
    private Map<String, Integer> rates;
    private String comments;

    public Session(String name,
                   String language,
                   String format,
                   String track,
                   String description,
                   String references,
                   Integer difficulty,
                   String speakerName,
                   String speakerEmail,
                   String speakerLanguage,
                   String cospeakers,
                   String mean) {

        this.name = name;

        this.language = language;
        this.format = format;
        this.track = track;
        this.description = description;
        this.references = references;
        this.difficulty = difficulty;
        this.speakerName = speakerName;
        this.speakerEmail = speakerEmail;
        this.speakerLanguage = speakerLanguage;
        this.cospeakers = cospeakers;
        this.mean = mean;
    }

    public Session() {

    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerEmail() {
        return speakerEmail;
    }

    public void setSpeakerEmail(String speakerEmail) {
        this.speakerEmail = speakerEmail;
    }

    public String getSpeakerLanguage() {
        return speakerLanguage;
    }

    public void setSpeakerLanguage(String speakerLanguage) {
        this.speakerLanguage = speakerLanguage;
    }

    public String getCospeakers() {
        return cospeakers;
    }

    public void setCospeakers(String cospeakers) {
        this.cospeakers = cospeakers;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRates(Map<String, Integer> rates) {
        this.rates = rates;
    }

    public Map<String, Integer> getRates() {
        return rates;
    }

    public void setComments( String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }
}
