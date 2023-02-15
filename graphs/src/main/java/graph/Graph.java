package graph;

import drawing.DrawingApi;
import drawing.Point;
import drawing.PointsFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static drawing.DrawingUtils.MARGIN;
import static drawing.DrawingUtils.VERTEX_RADIUS;

public abstract class Graph {
    private final DrawingApi drawingApi;
    private final Map<Vertex, Point> drawnVertices = new HashMap<>();
    private final Set<Edge> drawnEdges = new HashSet<>();
    private PointsFactory pointsFactory;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public void drawGraph() {
        Point center = new Point(
                drawingApi.getDrawingAreaWidth() / 2.0,
                drawingApi.getDrawingAreaHeight() / 2.0
        );
        double radius = Math.min(
                drawingApi.getDrawingAreaWidth(),
                drawingApi.getDrawingAreaHeight()
        ) / 2.0 - MARGIN;

        pointsFactory = new PointsFactory(getGraphSize(), center, radius);

        doDrawGraph();
        drawingApi.visualize();
    }

    protected Point drawVertex(Vertex vertex) {
        if (drawnVertices.containsKey(vertex)) {
            return drawnVertices.get(vertex);
        }

        Point point = pointsFactory.nextPoint();
        drawingApi.drawCircle(point, VERTEX_RADIUS);
        drawnVertices.put(vertex, point);
        return point;
    }

    protected void drawEdge(Edge edge) {
        if (drawnEdges.contains(edge)) {
            return;
        }

        Point a = drawVertex(edge.getFrom());
        Point b = drawVertex(edge.getTo());
        drawingApi.drawLine(a, b);
        drawnEdges.add(edge);
    }

    public void readGraphFromConsole() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            readGraph(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void readGraphFromFile(String fileName) throws UncheckedIOException {
        Path file = Paths.get(fileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toFile().toPath())))) {
            readGraph(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected abstract void doDrawGraph();

    protected abstract void readGraph(BufferedReader reader);

    protected abstract int getGraphSize();
}
