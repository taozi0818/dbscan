import java.util.*;

public class Main {

    public static void main(String[] args) {

        Util u = new Util();
        Dbscan dbscan = new Dbscan();
        String fileName = "//Users//tyt//Desktop//data.txt";
        Double R = 2.50;
        int minPts = 5;

        try {
            Vector<Point> points = u.inputData(fileName);
            Hashtable<Integer, Vector<Point>> cluster = dbscan.run(R, minPts, points);

            for (Iterator iterator = cluster.keySet().iterator(); iterator.hasNext(); ) {
                System.out.println("-------");
                int key = (Integer) iterator.next();
                Vector<Point> test = cluster.get(key);
                for (Iterator iterator1 = test.iterator(); iterator1.hasNext();) {
                    Point point = (Point) iterator1.next();
                    System.out.println("X:" + point.getX() + " Y:" + point.getY() + " id:" + point.getId());
                }
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
