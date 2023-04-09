package search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StubSearchClient implements SearchClient {
    final String searchEngine;
    final String searchFolder = "src/main/resources/search/";


    public StubSearchClient(String searchEngine) {
        this.searchEngine = searchEngine;
    }

    @Override
    public String makeRequest(String request) {
        String result;
        try {
            Path path = Paths.get(searchFolder + searchEngine + ".json");
            result = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.err.println("Unable to make request. Search engine: " + searchEngine);
            throw new RuntimeException(e);
        }
        return result;
    }
}
