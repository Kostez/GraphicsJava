/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kosta
 */
public class TriPixel implements IFilter {

    @Override
    public Color toFilter(Point2D point, int highSize, int lowSize, BufferedImage originalImage, BufferedImage mipmap[], float distortion) {
        Color c1 = new Color(mipmap[0].getRGB((int)Math.floor(point.getX()) / lowSize, (int) Math.floor(point.getY()) / lowSize));
        Color c2 = new Color(mipmap[1].getRGB((int)Math.floor(point.getX()) / highSize, (int) Math.floor(point.getY()) / highSize));

        byte red = (byte)((c1.getRed() * (highSize - distortion) + c2.getRed() * (distortion - lowSize)) / lowSize);
        byte green = (byte) ((c1.getGreen() * (highSize - distortion) + c2.getGreen() * (distortion - lowSize)) / lowSize);
        byte blue = (byte) ((c1.getBlue() * (highSize - distortion) + c2.getBlue() * (distortion - lowSize)) / lowSize);

        return new Color(red, green, blue);
    }
}
