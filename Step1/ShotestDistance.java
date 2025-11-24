import java.util.*;

public class ShortestDistance {
    static final double EPS = 1e-8;
    
    static class Point {
        double x, y;
        Point(double x, double y) { 
            this.x = x; 
            this.y = y; 
        }
        double dist(Point other) {
            return Math.hypot(this.x - other.x, this.y - other.y);
        }
    }
    
    static class Edge {
        int to;
        double cost;
        Edge(int to, double cost) {
            this.to = to;
            this.cost = cost;
        }
    }
    
    // Dijkstra法で最短経路を求める
    static double dijkstra(List<Edge>[] graph, int start, int end, int n) {
        double[] dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0;
        
        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(a[1], b[1]));
        pq.offer(new double[]{start, 0});
        
        while (!pq.isEmpty()) {
            double[] cur = pq.poll();
            int v = (int)cur[0];
            double d = cur[1];
            
            if (d > dist[v]) continue;
            
            for (Edge e : graph[v]) {
                if (dist[v] + e.cost < dist[e.to]) {
                    dist[e.to] = dist[v] + e.cost;
                    pq.offer(new double[]{e.to, dist[e.to]});
                }
            }
        }
        
        return dist[end];
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 8つの点を入力
        Point[] points = new Point[8];
        System.out.println("8つの点の座標を入力してください:");
        for (int i = 0; i < 8; i++) {
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            points[i] = new Point(x, y);
        }
        
        // グラフを構築（1-2, 3-4, 5-6, 7-8を接続）
        List<Edge>[] graph = new ArrayList[8];
        for (int i = 0; i < 8; i++) {
            graph[i] = new ArrayList<>();
        }
        
        // 線分を追加（0-1, 2-3, 4-5, 6-7）
        for (int i = 0; i < 8; i += 2) {
            double d = points[i].dist(points[i + 1]);
            graph[i].add(new Edge(i + 1, d));
            graph[i + 1].add(new Edge(i, d));
        }
        
        // 最短経路
        System.out.println("始点と終点を入力してください (0-7):");
        int start = sc.nextInt();
        int end = sc.nextInt();
        
        double distance = dijkstra(graph, start, end, 8);
        
        if (distance == Double.POSITIVE_INFINITY) {
            System.out.println("到達不可能");
        } else {
            System.out.printf("最短距離: %.6f\n", distance);
        }
    }
}
