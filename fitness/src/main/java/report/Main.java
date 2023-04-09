package report;

import com.github.vanbv.num.json.JsonParserDefault;
import common.http.ServerRunner;
import report.http.HttpMappingHandler;

public class Main {
    private static final int PORT = 3333;

    public static void main(String[] args) throws Exception {
        ServerRunner.runServer(PORT, new HttpMappingHandler(new JsonParserDefault()));
    }
}
