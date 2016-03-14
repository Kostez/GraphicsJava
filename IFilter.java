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
public interface IFilter {
    Color toFilter(Point2D point, int highSize, int lowSize, BufferedImage originalImage, BufferedImage mipmap[], float distortion);
}
