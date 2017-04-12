import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        Util u = new Util();
        Dbscan dbscan = new Dbscan();
        String fileName = "//Users//tyt//Desktop//data.txt"; // 源数据文件路径
        String outFile = "//Users//tyt//Desktop//result.txt"; // 聚类输出文件路径
        Double R = 2.50;
        int minPts = 5;
        int i = 0;

        try {
            PrintWriter result = new PrintWriter(outFile);
            Vector<Point> points = u.inputData(fileName);
            Hashtable<Integer, Vector<Point>> cluster = dbscan.run(R, minPts, points);

            for (Iterator iterator = cluster.keySet().iterator(); iterator.hasNext(); ) {
                int key = (Integer) iterator.next();
                i++;
                System.out.println("-------");
                result.println("-----cluster " + i + "-----");
                Vector<Point> test = cluster.get(key);

                for (Iterator iterator1 = test.iterator(); iterator1.hasNext(); ) {
                    Point point = (Point) iterator1.next();
                    System.out.println("X:" + point.getX() + " Y:" + point.getY() + " id:" + point.getId());
                    result.println("X:" + point.getX() + " Y:" + point.getY());
                }
            }

            result.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        long endTime = System.currentTimeMillis();
        System.out.println("运行时间: " + (endTime - startTime) + "ms");
    }

}
