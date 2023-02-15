import drawing.DrawingApi;
import drawing.DrawingAwt;
import drawing.DrawingJavaFx;
import graph.EdgesListGraph;
import graph.Graph;
import graph.MatrixGraph;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid arguments");
            System.out.println("Usage : javafx/awt matrix/edges <fileName>");
            return;
        }
        DrawingApi drawingApi = getDrawingApi(args[0]);
        Graph graph = getGraph(drawingApi, args[1]);
        graph.readGraphFromFile(args[2]);
        graph.drawGraph();
    }

    private static DrawingApi getDrawingApi(String apiType) {
        if ("awt".equals(apiType)) {
            return new DrawingAwt();
        }
        if ("javafx".equals(apiType)) {
            return new DrawingJavaFx();
        }
        throw new IllegalArgumentException("Unsupported drawing api type : " + apiType);
    }

    private static Graph getGraph(DrawingApi drawingApi, String graphType) {
        if ("matrix".equals(graphType)) {
            return new MatrixGraph(drawingApi);
        }
        if ("edges".equals(graphType)) {
            return new EdgesListGraph(drawingApi);
        }
        throw new IllegalArgumentException("Unsupported drawing graph type : " + graphType);
    }
}
