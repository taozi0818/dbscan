import java.util.*;

public class Main {

    public static void main(String[] args) {

        Util u = new Util();
        String fileName = "//Users//tyt//Desktop//data.txt";

        try {

            ArrayList<Point> points = u.inputData(fileName);

            System.out.println(points.size());

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
