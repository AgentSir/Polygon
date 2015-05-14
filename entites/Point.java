package entites;

public class Point {

    public int x, y;

    public Point(){
    }

    public Point(int x, int y) {
        this.x=x;
        this.y=y;
    }

    // Переопределенный метод для изображения объекта entites.Point в виде строки

    // Override method toString for easy to out object as a string
    public String toString(){
        return "(" + x + ";" + y + ")";
    }
}