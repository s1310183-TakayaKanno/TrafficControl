// 菅野貴也 
// ２本の道（線分）に対して交差地点を検出して出力する基本的なコード 

import java.util.Scanner;

public class Findcross {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt(); // Nは点の数（3 <= N <=4）
        int M = sc.nextInt(); // Mは線の数 (N=2)

        
        int[][] points = new int[N][2]; // 点の追加で[N]個分追加。[2]は[0]にx座標[1]にy座標
        for (int i = 0; i < N; i++) {   // 点を追加するためにN回までループ
            points[i][0] = sc.nextInt();// x座標の追加
            points[i][1] = sc.nextInt();// y座標の追加
        }         
        int[][] roads = new int[M][2]; //何個目に入力した点と”した点を線分とするか決める。
        for (int i = 0; i < M; i++) {  
            roads[i][0] = sc.nextInt() - 1; //どの点とどの点を結ぶかなので１×１の４回ループではなく２列２行のループにする
            roads[i][1] = sc.nextInt() - 1;
        } 
        
        double[] ans = findcross(
            points[roads[0][0]][0], points[roads[0][0]][1], //road[]で指定した?番目のx座標,y座標をpointsに代入して順番をただして引数としてFindcrossへ
            points[roads[0][1]][0], points[roads[0][1]][1],
            points[roads[1][0]][0], points[roads[1][0]][1],
            points[roads[1][1]][0], points[roads[1][1]][1]
        );
        
        if (ans == null) {
            System.out.println("NA");
        } else {
            if (ans[0] == (int)ans[0]) {
                System.out.print((int)ans[0]);
            } else {
                System.out.printf("%.5f", ans[0]);
            }
            System.out.print(" ");
            if (ans[1] == (int)ans[1]) {
                System.out.print((int)ans[1]);
            } else {
                System.out.printf("%.5f", ans[1]);
            }
            System.out.println();
        }
    }
    
    static double[] findcross(int x1, int y1, int x2, int y2,
                              int x3, int y3, int x4, int y4) {
        
        double a1 = y2 - y1;  //交点を求める連立方程式　1本目の線分のAx+By = C
        double b1 = x1 - x2;
        double c1 = a1 * x1 + b1 * y1;
        
        double a2 = y4 - y3;  //２本目
        double b2 = x3 - x4;
        double c2 = a2 * x3 + b2 * y3;
        
        double determinant = a1 * b2 - a2 * b1; //determinant = 行列式の斜め同士の積。平行かどうか
        
        if (determinant == 0) { //平行なら
            return null;
        } else {
            double x = (b2 * c1 - b1 * c2) / determinant; //交点のx座標
            double y = (a1 * c2 - a2 * c1) / determinant; //交点のy座標
            
            if (checkOnroad(x1, y1, x2, y2, x, y) && checkOnroad(x3, y3, x4, y4, x, y)) { //線分上に交点があるなら
                if (overlapEnd(x1, y1, x2, y2, x3, y3, x4, y4, x, y)) { //交点が線分の端点なら
                    return null;
                }
                return new double[]{x, y}; //交点返す
            } else {
                return null;//線分上に交点がないなら
            }
        }
    }
    
    static boolean checkOnroad(int x1, int y1, int x2, int y2, double x, double y) {
        
        boolean xRange = (x >= Math.min(x1, x2) && x <= Math.max(x1, x2));
        //交点のx座標が線分の２点の範囲に収まっているか
        boolean yRange = (y >= Math.min(y1, y2) && y <= Math.max(y1, y2));
        //交点のy座標が "
        return xRange && yRange;
    }//交点が線分の間に含まれているか //
    


    static boolean overlapEnd(int x1, int y1, int x2, int y2,
                             int x3, int y3, int x4, int y4,
                             double x, double y) {
        boolean onRoad1Endpoint = (almostEqual(x, x1) && almostEqual(y, y1)) ||
                               (almostEqual(x, x2) && almostEqual(y, y2));
                               //交点のx座標,y座標と線１の点２つのx,y(端点)と一致するか
        boolean onRoad2Endpoint = (almostEqual(x, x3) && almostEqual(y, y3)) ||
                               (almostEqual(x, x4) && almostEqual(y, y4));
        return onRoad1Endpoint && onRoad2Endpoint;
    }
    
    static boolean almostEqual(double a, double b) { //絶対差の誤差がないか
        return Math.abs(a - b) < 1e-8;
    }
} 
