
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kosta
 */
public class Transformation {

    BufferedImage originalImage;
    BufferedImage transformedImage;
    Graphics originalGraphics;
    Graphics transformGraphics;
    IFilter filtering;
    BufferedImage mipmap[] = new BufferedImage[2];

    public Transformation(BufferedImage originalImage) {
        this.originalImage = originalImage;
        this.originalGraphics = originalImage.getGraphics();
        transformedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.transformGraphics = transformedImage.createGraphics();
    }

    private float distortion;

    public BufferedImage Transform(Matrix matrix) {
        //коэффициент изкажения
        distortion = getDistortion(matrix, transformedImage.getWidth(), transformedImage.getHeight());
        System.out.println(distortion);

        int lowLevel = 0, highSize = 0, lowSize = 0;

        if (distortion < 1) {
            filtering = new BiPixel();
        } else {
            filtering = new TriPixel();
            lowLevel = -1;
            //меньший уровень детализации (более глубокий)
            highSize = 2;
            while (distortion > highSize) {
                highSize = highSize * 2;
                lowLevel++;
            }
            //больший уровень
            lowSize = highSize / 2;
            getMipmap(lowSize);
        }
        Point2D point;
        Color color;

        for (int i = 0; i < transformedImage.getHeight(); i++) {
            for (int j = 0; j < transformedImage.getWidth(); j++) {
                point = new Point2D.Double(j, i);
                Point2D originalPoint = multMatrixOnPoint(matrix, point);

                if (originalPoint.getX() < 0 || Math.ceil(originalPoint.getX()) >= originalImage.getWidth() || originalPoint.getY() < 0 || Math.ceil(originalPoint.getY()) >= originalImage.getHeight()) {
                    color = Color.WHITE;
                } else {
                    color = filtering.toFilter(originalPoint, highSize, lowSize, originalImage, mipmap, distortion);
                }
                transformGraphics.setColor(color);
                transformGraphics.fillRect(j, i, 1, 1);
            }
        }
        return transformedImage;
    }

    private float getDistortion(Matrix matrix, int width, int height) {
        // искажение пространства по горизонтали
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(width, 0);

        a = multMatrixOnPoint(matrix, a);
        b = multMatrixOnPoint(matrix, b);

        float distortionH = pointsLength(a, b) / width;

        // искажение пространства по вертикали
        a = new Point2D.Double(0, 0);
        b = new Point2D.Double(0, height);

        a = multMatrixOnPoint(matrix, a);
        b = multMatrixOnPoint(matrix, b);

        float distortionV = pointsLength(a, b) / height;

        return Math.max(distortionH, distortionV);
    }

    public Point2D multMatrixOnPoint(Matrix matrix, Point2D point) {
        Point2D newPoint = new Point2D.Double(
                (float) matrix.getElements(1, 1) * point.getX() + (float) matrix.getElements(1, 2) * point.getY() + (float) matrix.getElements(1, 3),
                (float) matrix.getElements(2, 1) * point.getX() + (float) matrix.getElements(2, 2) * point.getY() + (float) matrix.getElements(2, 3));
        return newPoint;
    }

    public float pointsLength(Point2D p1, Point2D p2) {
        return (float) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public void getMipmap(int lowSize) {
        for (int lvl = 0; lvl < 2; lvl++) {
            int width = (int) Math.ceil(Double.valueOf(originalImage.getWidth()) / lowSize);
            int height = (int) Math.ceil(Double.valueOf(originalImage.getHeight()) / lowSize);

            mipmap[lvl] = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    // записываем усреднённое значение цвета для каждого пикселя нового уровня детализации
                    mipmap[lvl].setRGB(j, i, getColorFromImage(j * lowSize, i * lowSize, lowSize).getRGB());
                }
            }
            lowSize *= 2;
        }
    }

    public Color getColorFromImage(int x, int y, int k) {
        int red = 0, green = 0, blue = 0, alpha = 0;
        // пока в пределах пикселя и не выходим за рамки изображения
        for (int i = x; i < k + x && i < originalImage.getWidth(); i++) {
            for (int j = y; j < k + y && j < originalImage.getHeight(); j++) {
//                /*переделать эсли та фигня не работает на код с
//                *http://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image?newreg=ea08642c84344ade8ecf25c7b868edc5
//                 */
//
                Color c = new Color(originalImage.getRGB(x, y));

                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();


                /*
                red += originalImage.getColorModel().getRed(originalImage.getData());
                green += originalImage.getColorModel().getGreen(originalImage.getData());
                blue += originalImage.getColorModel().getBlue(originalImage.getData());
                 */
            }
        }
        return new Color(red / k / k, green / k / k, blue / k / k);
    }
}
