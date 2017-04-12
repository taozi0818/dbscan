import java.util.*;

public class Dbscan {

    public Dbscan() {

    }

    public Hashtable<Integer, Vector<Point>> run(double R, int Pts, Vector<Point> points) { // 主函数
        System.out.println("run has reached");
//        Hashtable<String, Double> distanceList = distanceList(points);
        Hashtable<String, Double> distanceList = new Hashtable<>();
        Hashtable<String, Vector<Point>> coresAndNotCores = getCores(R, Pts, points, distanceList);
        Vector<Point> cores = coresAndNotCores.get("core");
        Hashtable<Integer, Vector<Point>> clusterTable;
        clusterTable = extend(R, coresAndNotCores, distanceList);


        /*
        输出距离表,便于观察,后期可以删除
         */
//        System.out.println("*******distanceList*******");
//        for (int i = 1; i <= points.size(); i++) {
//            for (int j = 1; j <= points.size(); j++) {
//                String key = i + "-" + j;
//                System.out.println(key + ": " + distanceList.get(key));
//            }
//        }
//        System.out.println("*******distanceList*******");
        System.out.println();
        /*
        输出核心点,便于调试观察,后期可以删除
         */
        System.out.println("*******cores*******");
        for (Iterator iterator = cores.iterator(); iterator.hasNext(); ) {
            Point core = (Point) iterator.next();
            System.out.println(core.getId());
        }
        System.out.println("*******cores*******");
        System.out.println();

        /*
        输出边界点,便于观察调试,后期可以删除
         */
        System.out.println("*******borders*******");
        for (Iterator iterator = coresAndNotCores.get("borders").iterator(); iterator.hasNext(); ) {
            Point core = (Point) iterator.next();
            System.out.println(core.getId());
        }
        System.out.println("*******borders*******");
        System.out.println();

        /*
        输出噪声点,便于调试观察,后期可以删除
         */
        System.out.println("*******noise*******");
        for (Iterator iterator = coresAndNotCores.get("noise").iterator(); iterator.hasNext(); ) {
            Point noise = (Point) iterator.next();
            System.out.println(noise.getId());
        }
        System.out.println("*******noise*******");
        System.out.println();

        System.out.println("run has ended");
        return clusterTable;
    }

    public Hashtable<String, Double> distanceList(Vector<Point> points) { // 点与点之间的距离
        Hashtable<String, Double> distanceList = new Hashtable<>();

        System.out.println("distanceList has reached");

        int i = 0;
        for (Iterator it1 = points.iterator(); it1.hasNext(); ) {
            Point point1 = (Point) it1.next();

            i++;
            System.out.println(i);
            for (Iterator it2 = points.iterator(); it2.hasNext(); ) {
                Point point2 = (Point) it2.next();
                distanceList.put(point1.getId() + "-" + point2.getId(), getDistance(point1, point2));
            }
        }

        System.out.println("distanceList has ended");

        return distanceList;
    }

    public Hashtable<String, Vector<Point>> getCores(double R, int minPts, Vector<Point> points, Hashtable<String, Double> distanceList) { // 核心点
        Hashtable<String, Vector<Point>> result = new Hashtable<>();
        Vector<Point> core = new Vector<>(); // 核心点集合
        Vector<Point> notCore = new Vector<>(); // 非核心点集合

        System.out.println("getCores has reached");

        for (Iterator iterator = points.iterator(); iterator.hasNext(); ) {
            Point point1 = (Point) iterator.next();
            int count = 0; // 输入半径内总点数

            for (Iterator iterator1 = points.iterator(); iterator1.hasNext(); ) {
                Point point2 = (Point) iterator1.next();
//                if (distanceList.get(point1.getId() + "-" + point2.getId()) <= R) {
//                    count++;
//                }

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

//    // 获取边界点集合
//    public Vector<Point> getBorders(double R, Vector<Point> core, Vector<Point> notCore) {
//        Vector<Point> border = new Vector<>();
//
//        for (Iterator iterator = notCore.iterator(); iterator.hasNext(); ) {
//            Point point1 = (Point) iterator.next();
//
//            for (Iterator iterator1 = core.iterator(); iterator1.hasNext(); ) {
//                Point point2 = (Point) iterator.next();
//                if (getDistance(point1, point2) <= R) {
//                    border.add(point1);
//                    break;
//                }
//            }
//        }
//
//        return border;
//    }

    public Hashtable<Integer, Vector<Point>> extend(double R, Hashtable<String, Vector<Point>> coreAndNotCores, Hashtable<String, Double> distanceList) {
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

//                    if (distanceList.get(point1.getId() + "-" + point2.getId()) <= R && coreCluster.indexOf(point2) == -1) {
//                        coreCluster.add(point2);
//                        cpCores.remove(point2);
//                        k--;
//                    }

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

//                    if (distanceList.get(point1.getId() + "-" + point3.getId()) <= R && coreCluster.indexOf(point3) == -1) {
//                        coreCluster.add(point3);
//                        borders.add(point3);
//                        notCores.remove(point3);
//                        l--;
//                    }

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