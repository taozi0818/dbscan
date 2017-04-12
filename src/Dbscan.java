import java.io.PrintWriter;
import java.util.*;

public class Dbscan {

    public Dbscan() {

    }

    public Hashtable<Integer, Vector<Point>> run(double R, int Pts, Vector<Point> points) { // 主函数
        System.out.println("run has reached");
        Hashtable<String, Vector<Point>> coresAndNotCores = getCores(R, Pts, points);
        Vector<Point> cores = coresAndNotCores.get("core");
        Hashtable<Integer, Vector<Point>> clusterTable;
        clusterTable = extend(R, coresAndNotCores);
        String pointFileName = "//Users//tyt//Desktop//point.txt"; // 点分类文件输出路径
        System.out.println();

        try {
            PrintWriter pointFile = new PrintWriter(pointFileName);

            /*
            输出核心点,便于调试观察,后期可以删除
            */
            System.out.println("*******cores*******");
            pointFile.println("*******cores*******");
            for (Iterator iterator = cores.iterator(); iterator.hasNext(); ) {
                Point core = (Point) iterator.next();
                System.out.println(core.getId());
                pointFile.println("X:" + core.getX() + " Y:" + core.getY() + " id:" + core.getId());
            }
            System.out.println("*******cores*******");
            pointFile.println("*******cores*******");
            pointFile.println("-------------------------------------------");
            System.out.println();

            /*
            输出边界点,便于观察调试,后期可以删除
            */
            System.out.println("*******borders*******");
            pointFile.println("*******borders*******");
            for (Iterator iterator = coresAndNotCores.get("borders").iterator(); iterator.hasNext(); ) {
                Point border = (Point) iterator.next();
                System.out.println(border.getId());
                pointFile.println("X:" + border.getX() + " Y:" + border.getY() + " id:" + border.getId());
            }
            System.out.println("*******borders*******");
            pointFile.println("*******borders*******");
            pointFile.println("-------------------------------------------");
            System.out.println();

            /*
            输出噪声点,便于调试观察,后期可以删除
             */
            System.out.println("*******noises*******");
            pointFile.println("*******noises*******");
            for (Iterator iterator = coresAndNotCores.get("noise").iterator(); iterator.hasNext(); ) {
                Point noise = (Point) iterator.next();
                System.out.println(noise.getId());
                pointFile.println("X:" + noise.getX() + " Y:" + noise.getY() + " id:" + noise.getId());
            }
            System.out.println("*******noises*******");
            pointFile.println("*******noises*******");

            pointFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("run has ended");
        return clusterTable;
    }


    public Hashtable<String, Vector<Point>> getCores(double R, int minPts, Vector<Point> points) { // 核心点
        Hashtable<String, Vector<Point>> result = new Hashtable<>();
        Vector<Point> core = new Vector<>(); // 核心点集合
        Vector<Point> notCore = new Vector<>(); // 非核心点集合

        System.out.println("getCores has reached");

        for (Iterator iterator = points.iterator(); iterator.hasNext(); ) {
            Point point1 = (Point) iterator.next();
            int count = 0; // 输入半径内总点数

            for (Iterator iterator1 = points.iterator(); iterator1.hasNext(); ) {
                Point point2 = (Point) iterator1.next();

                if (getDistance(point1, point2) <= R) {
                    count++;
                }
            }

            // 若总点数大于等于用户输入最小点数,则该点为核心点
            if (count >= minPts) {
                core.add(point1);
                point1.setCore(true);
            } else {
                notCore.add(point1);
            }
        }

        result.put("core", core);
        result.put("notCore", notCore);

        System.out.println("getCores has ended");
        return result;
    }

    public Hashtable<Integer, Vector<Point>> extend(double R, Hashtable<String, Vector<Point>> coreAndNotCores) {
        Hashtable<Integer, Vector<Point>> cluster = new Hashtable<>();
        Vector<Point> cpCores = (Vector) coreAndNotCores.get("core").clone();
        Vector<Point> notCores = coreAndNotCores.get("notCore");
        Vector<Point> borders = new Vector<>();

        System.out.println("extend has reached");

        int id = 0;

        // 递归处理核心点集
        for (int i = 0; i < cpCores.size(); i++) { // 每层循环输出一个核心聚类
            Vector<Point> coreCluster = new Vector<>();
            Point startPoint = cpCores.get(i);

            coreCluster.add(startPoint);
            cpCores.remove(startPoint);

            for (int j = 0; j < coreCluster.size(); j++) {
                Point point1 = coreCluster.get(j);

                for (int k = 0; k < cpCores.size(); k++) {
                    Point point2 = cpCores.get(k);

                    if (getDistance(point1, point2) <= R && coreCluster.indexOf(point2) == -1) {
                        coreCluster.add(point2);
                        cpCores.remove(point2);
                        k--;
                    }
                }

                /*
               如果该点在非核心点集合中被加入到某一个聚类里面,说明该点为边界点
               如果此Vector中的点没有被加入到任何聚类中,则此点为噪声点
                */
                for (int l = 0; l < notCores.size(); l++) {
                    Point point3 = notCores.get(l);

                    if (getDistance(point1, point3) <= R && coreCluster.indexOf(point3) == -1) {
                        coreCluster.add(point3);
                        borders.add(point3);
                        notCores.remove(point3);
                        l--;
                    }
                }
            }

            cluster.put(id, coreCluster);
            i--;
            id++;
        }

        coreAndNotCores.put("borders", borders);
        coreAndNotCores.put("noise", notCores);
        System.out.println("extend has ended");
        return cluster;
    }

    public double getDistance(Point a, Point b) {
        double distance = Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
        return distance;
    }

}