package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.WhiteSpaceGame;
import org.json.*;

public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WhiteSpaceGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWhiteSpaceGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses WhiteSpaceGame from JSON object and returns it
    private WhiteSpaceGame parseWhiteSpaceGame(JSONObject jsonObject) {
        WhiteSpaceGame wsg = new WhiteSpaceGame();
        wsg.fillWithLoadedData(jsonObject);
        return wsg;
    }
}
