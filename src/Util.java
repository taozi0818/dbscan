import java.io.*;
import java.util.*;

public class Util {

    public Util() {

    }

    public Vector<Point> inputData(String fileName) {

        Vector<Point> points = new Vector<>();

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
}
