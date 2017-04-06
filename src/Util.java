import java.io.*;
import java.util.*;

public class Util {

    public Util() {

    }

    public Vector<Point> inputData(String fileName) {

        Vector<Point> points = new Vector<Point>();

        try {

            File file = new File(fileName);
            Scanner input = new Scanner(file);
            int id = 1;

            while (input.hasNext()) {
                String point[] = input.nextLine().split(",");
                points.add(new Point(id, Double.parseDouble(point[0]), Double.parseDouble(point[1])));
                id++;
            }

            return points;
        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

        return points;

    }

//    public static Vector<Point> getNeighbours(Point p, Vector<Point> points) {
//
//        Vector<Point> result = new Vector<Point>();
//        Iterator<Point> list = points.iterator();
//
//        while (list.hasNext()) {
//            Point a = points.next();
//            if (getDistance(p, a) <= ) {
//
//            }
//        }
//
//        return result;
//    }


    public static double getDistance(Point a, Point b) {
        double distance = Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
        return distance;
    }
}
