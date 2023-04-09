package visit;

import com.github.vanbv.num.json.JsonParserDefault;
import common.http.ServerRunner;
import manager.http.HttpMappingHandler;

public class Main {
    private static final int PORT = 1111;

    public static void main(String[] args) throws Exception {
        ServerRunner.runServer(PORT, new HttpMappingHandler(new JsonParserDefault()));
    }
}
