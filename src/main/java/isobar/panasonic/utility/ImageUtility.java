package isobar.panasonic.utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by hieu on 11/1/2018.
 */

public class ImageUtility {
    private static final int SAISO_PIXEL = 10;
    private static final int SAISO_RGB = 50;

    private int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }

    public BufferedImage getDifferentImage(BufferedImage img1, BufferedImage img2) throws IOException {
        BufferedImage img3;
        int width = img1.getWidth();
        int height = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();
        Color myWhite = new Color(255, 255, 255);
        img3 = new BufferedImage(width, height, Image.SCALE_DEFAULT);

        if (Math.abs(width - width2) > SAISO_PIXEL || Math.abs(height - height2) > SAISO_PIXEL) {
            return img3;
        }

        long diff;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff = pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
                if (diff > SAISO_RGB) {
                    img3.setRGB(x, y, img2.getRGB(x, y));
                } else {
                    img3.setRGB(x, y, myWhite.getRGB());
                }
            }
        }

        return img3;
    }

    public double getDifferencePercent(BufferedImage img1, BufferedImage img2) throws IOException {
        int width = img1.getWidth();
        int height = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();

        if (Math.abs(width - width2) > SAISO_PIXEL || Math.abs(height - height2) > SAISO_PIXEL) {
            return 100;
        }

        long diff;
        long count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff = pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
                if (diff > SAISO_RGB)
                    count++;
            }
        }
        return (count * 100.0) / (width * height);
    }

    public BufferedImage resize(BufferedImage img_temp, int height, int width) {
        if (height > img_temp.getHeight())
            height = img_temp.getHeight();
        if (width > img_temp.getWidth())
            width = img_temp.getWidth();

        BufferedImage dimg = new BufferedImage(width, height, img_temp.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img_temp, 0, 0, width, height, 0, 0, img_temp.getWidth()
                , img_temp.getHeight(), null);

        g.dispose();
        return dimg;
    }
}
