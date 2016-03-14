/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Kosta
 */
public class Points {

    private final ArrayList<Point> points = new ArrayList<>();
    private final int size = 6;

    public void add(Point point) {

        if (points.size() > 2) {
            points.removeAll(points);
        }
        points.add(point);
    }

    public int getPointsSize() {
        return points.size();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void Draw(Graphics graphics) {
        Color pointColor = new Color(0);

        for (int i = 0; i < points.size(); i++) {
            switch (i) {
                case 0:
                    pointColor = Color.RED;
                    break;
                case 1:
                    pointColor = Color.GREEN;
                    break;
                case 2:
                    pointColor = Color.BLUE;
                    break;
            }
            graphics.setColor(pointColor);
            graphics.fillRect(points.get(i).x - size / 2, points.get(i).y - size / 2, size, size);
        }
    }
}
