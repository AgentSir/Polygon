import entites.Point;
import entites.PolygonLogic;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        LinkedList<Point> coordinatesOfPoints = new LinkedList<Point>();

        //Add to list points in the plane
        coordinatesOfPoints.add(new Point(1,1));
        coordinatesOfPoints.add(new Point(2,4));
        coordinatesOfPoints.add(new Point(4,1));
        coordinatesOfPoints.add(new Point(1,5));
        coordinatesOfPoints.add(new Point(2,2));
        coordinatesOfPoints.add(new Point(4,4));
        coordinatesOfPoints.add(new Point(1,3));

        PolygonLogic polygonLogic = new PolygonLogic(coordinatesOfPoints);

        polygonLogic.findLeftPoint(coordinatesOfPoints);
        polygonLogic.findRightPoint(coordinatesOfPoints);
        polygonLogic.splitPointsIntoSegments(polygonLogic.getLeftPoint(), polygonLogic.getRightPoint(), coordinatesOfPoints);
        polygonLogic.sortAboveSegmentByIncX();
        polygonLogic.sortBelowSegmentByDecX();

        List<Point> polygon = polygonLogic.getPolygon();

        // Print ordered list of coordinates of points on the place that form a polygon
        for(Point point: polygon) {
            System.out.println(point.toString());
        }
    }
}





