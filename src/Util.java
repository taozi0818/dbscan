import java.io.*;
import java.util.*;

public class Util {

    public Util() {

    }

    public ArrayList<Point> inputData(String fileName) {

        ArrayList<Point> points = new ArrayList<Point>();

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

    public double getDistance(Point a, Point b) {
        double distance = Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
        return distance;
    }
}
