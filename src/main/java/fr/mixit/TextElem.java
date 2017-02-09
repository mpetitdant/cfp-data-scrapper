package fr.mixit;

import org.codehaus.jackson.JsonNode;

import java.util.Map;

/**
 * @author mpetitdant
 *         Date: 08/02/17
 */
public class TextElem {
    private final JsonNode jsonElem;
    private final Map<Integer, String> formats;
    private final Map<Integer, String> tracks;

    public TextElem(JsonNode elem, Map<Integer, String> formats, Map<Integer, String> tracks) {
        this.jsonElem = elem;
        this.formats = formats;
        this.tracks = tracks;

    }

    public String get(String name) {
        return         jsonElem.get(name).getTextValue();
    }

    public String getFormat(String format) {
        int formatId = jsonElem .get(format).getIntValue();
        return formats.get(formatId);
    }

    public String getTrack(String track) {
        int trackId = jsonElem.get(track).getIntValue();
        return tracks.get(trackId);
    }

    public String getSpeaker(String elem) {
        return         jsonElem.get("speaker").get(elem).getTextValue();
    }
}
