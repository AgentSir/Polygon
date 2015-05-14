package entites;

import java.util.*;

public class PolygonLogic {

    private  LinkedList<Point> inputPoints;

    private Point leftPoint;
    private Point rightPoint;

    private List<Point> aboveArea = new LinkedList<Point>();
    private List<Point> belowArea = new LinkedList<Point>();

    public PolygonLogic(LinkedList<Point> points) {
        inputPoints = points;
    }

    public Point getLeftPoint() {
        return leftPoint;
    }

    public Point getRightPoint() {
        return rightPoint;
    }

    public List<Point> getAboveArea() {
        return aboveArea;
    }

    public List<Point> getBelowArea() {
        return belowArea;
    }

    // Метод для поиска самой левой точки с найменьшими по значению координатами X и Y

    // Search method that defines point with the smallest X,Y coordinates
    public void findLeftPoint(LinkedList<Point> coordinatesOfPoints){
        leftPoint = coordinatesOfPoints.get(0);
        for(int i = 0; i < coordinatesOfPoints.size(); i++){
            if (leftPoint.x > coordinatesOfPoints.get(i).x && leftPoint.y == coordinatesOfPoints.get(i).y
                    || leftPoint.y  > coordinatesOfPoints.get(i).y && leftPoint.x == coordinatesOfPoints.get(i).x
                    || leftPoint.x > coordinatesOfPoints.get(i).x && leftPoint.y  > coordinatesOfPoints.get(i).y) {
                leftPoint = coordinatesOfPoints.get(i);
            }
        }
    }

    // Метод который определяет расположение точки С относительно отрезка АВ
    // Возвращает значение > 0 если точка С находится слева от отрезка АB
    // Возвращает значение = 0 если точка С находится на отрезке АВ
    // Возвращает значение < 0 если точка С находится справа от отрезка АВ

    // Method that look for location of point C relative to the AB line segment
    // Left, Right, Middle side of AB line segment, returns >0,0,<0
    public double checkPoint(Point a, Point b, Point c) {
        return  (c.x - a.x) * (b.y - a.y) - (c.y -a.y) * (b.x - a.x);
    }

    // Метод для поиска самой правой точки с найбольшими по значению координатами X и Y

    // Search method that defines point with the largest X,Y coordinates
    public void findRightPoint(LinkedList<Point> coordinatesOfPoints){
        rightPoint = coordinatesOfPoints.get(0);
        for(int i = 0; i < coordinatesOfPoints.size(); i++){
            if (rightPoint.x < coordinatesOfPoints.get(i).x && rightPoint.y == coordinatesOfPoints.get(i).y
                    || rightPoint.y  < coordinatesOfPoints.get(i).y && rightPoint.x == coordinatesOfPoints.get(i).x
                    || rightPoint.x < coordinatesOfPoints.get(i).x && rightPoint.y  < coordinatesOfPoints.get(i).y) {
                rightPoint = coordinatesOfPoints.get(i);
            }
        }
    }

    // Метод для реализации условного деления плоскости на 2 части отрезком AB,
    // в котором точка A имеет минимальное значением X и Y, а точка B имеет максимальное значение X и Y
    // Метод формирует 2 списка, в одном из которых будут храниться точки, которые распологаются над отрезком АВ,
    // в другом - которые распологаются под отрезком АВ.
    // Если попадется точка, которая будет находиться на отрезке АВ, метод добавит ее в список точек,
    // которые распологаются под отрезком АВ.

    // Method split plane into two parts.
    // Forms two lists with points.
    // If point located on line segment which divided plane then method add point into list with points located below a line segment
    public void splitPointsIntoSegments(Point leftPoint, Point rightPoint, LinkedList<Point> coordinatesOfPoints){
        for(int i =0; i < coordinatesOfPoints.size(); i++){
            if(checkPoint(leftPoint, rightPoint, coordinatesOfPoints.get(i)) > 0){
                belowArea.add(coordinatesOfPoints.get(i));
            }
            if(checkPoint(leftPoint, rightPoint, coordinatesOfPoints.get(i)) == 0 && (coordinatesOfPoints.get(i) != leftPoint && coordinatesOfPoints.get(i) != rightPoint)) {
                belowArea.add(coordinatesOfPoints.get(i));
            }
            if(checkPoint(leftPoint, rightPoint, coordinatesOfPoints.get(i)) < 0){
                aboveArea.add(coordinatesOfPoints.get(i));
            }
        }
    }

    // Сортировать список с точками, расположенными выше отрезка по увеличению X

    // Sort points in the area above a line segment by increase X
    public void sortAboveSegmentByIncX(){
        Collections.sort(aboveArea, new AboveComparatorByX());
        Collections.sort(aboveArea, new AboveComparatorByYForTheSameX());
    }

    // Сортировать список с точками, расположенными ниже отрезка по уменьшению X

    // Sort points in the area below a line segment by reduce X
    public void sortBelowSegmentByDecX(){
        Collections.sort(belowArea, new BelowComparatorByX());
        Collections.sort(belowArea, new BelowComparatorByYForTheSameX());
    }

    // Компоратор для реализации сортировки точек по значению X от меньшего к большему

    // Comparator for implementation sort points by X from smallest to largest.
    private class AboveComparatorByX implements Comparator<Point>{
        @Override
        public int compare(Point point, Point point2) {
            return point.x < point2.x ? -1 : point.x == point2.x ? 0 : 1;
        }
    }

    // Компаратор для реализации сортировки точек по координатам Y при одинаковых X от меньшего значения к большему.

    // Comparator for implementation sort points by Y from smallest to largest at the same values of X.
    private class AboveComparatorByYForTheSameX implements Comparator<Point>{
        @Override
        public int compare(Point point, Point point2) {
            return point.x == point2.x && point.y < point2.y ? -1 : 0;
        }
    }

    // Компаратор для реализации сортировки точек по координатам X от большего значения к меньшему.

    // Comparator for implementation sort points by X from largest to smallest.
    private class BelowComparatorByX implements Comparator<Point>{
        @Override
        public int compare(Point point, Point point2) {
            return point.x > point2.x ? -1 : point.x == point2.x ? 0 : 1;
        }
    }

    // Компаратор для реализации сортировки точек по координате Y при одинаковых X от большего значения к меньшему.

    // Comparator for implementation sort points by Y from largest to smallest at the same values of X.
    private class BelowComparatorByYForTheSameX implements Comparator<Point>{
        @Override
        public int compare(Point point, Point point2) {
            return point.x == point2.x && point.y > point2.y ? -1 : 0;
        }
    }


    // Метод для получения координат точек, последовательность которых образует полигон (ломаную линию без самопересечений)

    // Method return ordered list of points which forms a polygon.
    public LinkedList<Point> getPolygon(){
        LinkedList<Point> polygon = new LinkedList<Point>();
        polygon.add(leftPoint);
        polygon.addAll(getAboveArea());
        polygon.add(rightPoint);
        polygon.addAll(getBelowArea());
        polygon.add(leftPoint);
        return polygon;
    }
}
