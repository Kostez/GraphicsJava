/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Kosta
 */
public class Matrix {

    private final double[] matrix = new double[9];

    public Matrix(Points originalPoints, Points transformPoints) {

        ArrayList<Point> o = originalPoints.getPoints();
        ArrayList<Point> t = transformPoints.getPoints();

        float xx = (t.get(1).y - t.get(0).y) / (float) (t.get(1).x - t.get(0).x);
        float yy = (o.get(1).x - o.get(0).x) / (float) (t.get(1).x - t.get(0).x);
        int dx = t.get(2).x - t.get(0).x;
        float b = (o.get(2).x - o.get(0).x - yy * dx) / (t.get(2).y - t.get(0).y - xx * dx);
        float a = -xx * b + yy;
        float c = o.get(0).x - t.get(0).x * a - t.get(0).y * b;
        matrix[0] = a;
        matrix[1] = b;
        matrix[2] = c;

        xx = (t.get(1).y - t.get(0).y) / (float) (t.get(1).x - t.get(0).x);
        yy = (o.get(1).y - o.get(0).y) / (float) (t.get(1).x - t.get(0).x);
        dx = t.get(2).x - t.get(0).x;
        b = (o.get(2).y - o.get(0).y - yy * dx) / (t.get(2).y - t.get(0).y - xx * dx);
        a = -xx * b + yy;
        c = o.get(0).y - t.get(0).x * a - t.get(0).y * b;
        matrix[3] = a;
        matrix[4] = b;
        matrix[5] = c;

        matrix[6] = 0;
        matrix[7] = 0;
        matrix[8] = 1;
    }

    public double getElements(int j, int i) {
        return matrix[(j - 1) * 3 + i - 1];
    }
}
