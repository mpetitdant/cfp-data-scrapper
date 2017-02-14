package fr.mixit;

import com.opencsv.CSVWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author mpetitdant
 *         Date: 04/02/17
 */
public class Main {

    public static final String HTTPS_API_CFP_IO_V0 = "https://api.cfp.io/v0";

    //public static final String ADMIN_DRAFTS = "/admin/drafts";
    public static final String ADMIN_SESSIONS = "/admin/sessions";

    public static final String API_TRACKS = "/tracks";
    public static final String API_RATES = "/rates";
    public static final String API_TRACKS_SPEAKERS = "/tracks/speakers";
    public static final String API_FORMATS = "/formats";
    public static final String API_ADMINS = "/admins";
    public static final String API_RATES_BY_ID = "/rates/proposals/826";
    public static final String SCRAPED_FOLDER = "./scraped/";
    private static final String SEP = "\t";


    private static ObjectMapper mapper = new ObjectMapper();

    private static String apiToken;

    public static void main(String[] args) throws IOException {
        apiToken = args[0];

        scrap("rates", API_RATES);

        Map<Integer, String> tracks = getTracks(scrap("tracks", API_TRACKS));
        Map<Integer, String> formats = getFormats(scrap("formats", API_FORMATS));
        //Map<Integer, Speaker> speakers = getSpeakers(scrap("speakers", API_TRACKS_SPEAKERS));

        Map<Integer, Session> sessions = getSessions(scrap("sessions", ADMIN_SESSIONS));

        for (Integer id : sessions.keySet()) {
            Session s = sessions.get(id);
            completeSession(s, scrap("session-" + id, ADMIN_SESSIONS + "/" + id), tracks, formats);
        }

        exportAsCSV("cfp", sessions, tracks, formats);
    }

    private static Map<Integer, Session> getSessions(String sessions) throws IOException {
        JsonNode rootNode = mapper.readValue(sessions, JsonNode.class);

        Map<Integer, Session> result = new HashMap<>();

        for (JsonNode elem : rootNode) {
            Session session = new Session();
            session.setMean(elem.get("mean").asText());

            String voters = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(elem.get("voteUsersEmail").getElements(), Spliterator.ORDERED),
                    false)
                    .map(JsonNode::getTextValue)
                    .collect(Collectors.joining(", "));
            session.setVoters(voters);

            result.put(elem.get("id").getIntValue(), session);
        }

        return result;
    }

    private static void completeSession(Session session, String sessionJson, Map<Integer, String> tracks, Map<Integer, String> formats) throws IOException {
        JsonNode rootNode = mapper.readValue(sessionJson, JsonNode.class);

        TextElem e = new TextElem(rootNode, formats, tracks);

        session.setName(e.get("name"));
        session.setLanguage(e.get("language"));
        session.setFormat(e.getFormat("format"));
        session.setTrack(e.getTrack("trackId"));
        session.setDescription(e.get("description"));
        session.setReferences(e.get("references"));
        session.setDifficulty(e.get("difficulty"));
        session.setSpeakerName(e.getSpeaker("firstname") + " " + e.getSpeaker("lastname"));
        session.setSpeakerEmail(e.getSpeaker("email"));
        session.setSpeakerLanguage(e.getSpeaker("language"));



        Iterator<JsonNode> cospeakers = rootNode.get("cospeakers").getElements();
        String cospeakerEmail = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(cospeakers, Spliterator.ORDERED),
                false)
                .map(cs -> cs.get("email").getTextValue())
                .collect(Collectors.joining(", "));

        session.setCospeakers(cospeakerEmail);
    }


    private static Map<Integer, String> getTracks(String src) throws IOException {

        JsonNode rootNode = mapper.readValue(src, JsonNode.class);

        Map<Integer, String> result = new HashMap<>();

        for (JsonNode track : rootNode) {
            result.put(track.get("id").getIntValue(), track.get("libelle").getTextValue());
        }

        return result;
    }

    private static Map<Integer, String> getFormats(String src) throws IOException {

        JsonNode rootNode = mapper.readValue(src, JsonNode.class);

        Map<Integer, String> result = new HashMap<>();

        for (JsonNode format : rootNode) {
            result.put(format.get("id").getIntValue(), format.get("name").getTextValue());
        }

        return result;
    }

    private static String scrap(String name, String url) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(HTTPS_API_CFP_IO_V0 + url);

        get.addHeader("Host", "api.cfp.io");
        get.addHeader("Accept", "application/json, text/plain, */*");
        get.addHeader("Cookie", apiToken);
        get.addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:51.0) Gecko/20100101 Firefox/51.0");
        get.addHeader("Accept-Language", "en-US,en;q=0.5");
        get.addHeader("Accept-Encoding", "gzip, deflate, br");
        get.addHeader("Referer", "https://mix-it.cfp.io/");
        get.addHeader("Origin", "https://mix-it.cfp.io");
        get.addHeader("Connection", "keep-alive");
        get.addHeader("Pragma", "no-cache");
        get.addHeader("Cache-Control", "no-cache");

        HttpResponse execute = client.execute(get);
        System.out.println(execute.getStatusLine());

        FileWriter fw = new FileWriter(new File("./scraped/" + name + ".json"));
        String resultStr = EntityUtils.toString(execute.getEntity());
        fw.append(resultStr);
        fw.close();

        return resultStr;
    }


    private static void exportAsCSV(String filename, Map<Integer, Session> sessions, Map<Integer, String> tracks, Map<Integer, String> formats) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(SCRAPED_FOLDER+filename + ".csv"), '\t');


        String[] entries = {"id", "name", "language", "format", "track", "description",
                "references", "difficulty", "speakerName", "speakerEmail",
                "speakerLanguage", "cospeakers", "mean", "voters"};
        writer.writeNext(entries);

        sessions.keySet().stream()
                .sorted(Comparator.naturalOrder())

                .forEach(key ->
                        {
                            Session s = sessions.get(key);
                            String[] nextLine =
                                    {key+"",
                                    s.getName(),
                                    s.getLanguage(),
                                    s.getFormat(),
                                    s.getTrack(),
                                    s.getDescription(),
                                    s.getReferences(),
                                    s.getDifficulty(),
                                    s.getSpeakerName(),
                                    s.getSpeakerEmail(),
                                    s.getSpeakerLanguage(),
                                    s.getCospeakers(),
                                    s.getMean(),
                                    s.getVoters()};
                            writer.writeNext(nextLine);
                        }

                );

        writer.close();

    }
}
