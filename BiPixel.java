/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kosta
 */
public class BiPixel implements IFilter {

    @Override
    public Color toFilter(Point2D point, int highSize, int lowSize, BufferedImage originalImage, BufferedImage mipmap[], float distortion) {

        int botx = (int) Math.floor(point.getX());
        int boty = (int) Math.floor(point.getY());
        int topx = botx + 1;
        int topy = boty + 1;

        double x = point.getX();
        double y = point.getY();

        Color tt = new Color(originalImage.getRGB(topx, topy));
        Color tb = new Color(originalImage.getRGB(topx, boty));
        Color bt = new Color(originalImage.getRGB(botx, topy));
        Color bb = new Color(originalImage.getRGB(botx, boty));

        int red = (int) ((bb.getRed() * (topx - x) + tb.getRed() * (x - botx)) * (topy - y)
                + (bt.getRed() * (topx - x) + tt.getRed() * (x - botx)) * (y - boty));
        int green = (int) ((bb.getGreen() * (topx - x) + tb.getGreen() * (x - botx)) * (topy - y)
                + (bt.getGreen() * (topx - x) + tt.getGreen() * (x - botx)) * (y - boty));
        int blue = (int) ((bb.getBlue() * (topx - x) + tb.getBlue() * (x - botx)) * (topy - y)
                + (bt.getBlue() * (topx - x) + tt.getBlue() * (x - botx)) * (y - boty));

        return new Color(red, green, blue);
    }
}
