package fr.mixit;

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
    private String difficulty;
    private String speakerName;
    private String speakerEmail;
    private String speakerLanguage;
    private String cospeakers;
    private String mean;
    private String voters;

    public Session(String name,
                   String language,
                   String format,
                   String track,
                   String description,
                   String references,
                   String difficulty,
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

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
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

    public void setVoters(String voters) {
        this.voters = voters;
    }

    public String getVoters() {
        return voters;
    }
}
