package manager;

import com.github.vanbv.num.json.JsonParserDefault;
import common.http.ServerRunner;
import manager.http.HttpMappingHandler;

public class Main {
    private final static int PORT = 2222;

    public static void main(String[] args) throws Exception {
        ServerRunner.runServer(PORT, new HttpMappingHandler(new JsonParserDefault()));
    }
}
