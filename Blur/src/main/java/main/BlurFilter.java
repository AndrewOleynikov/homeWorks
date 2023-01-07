package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BlurFilter extends Thread {

    private BufferedImage in;
    private BufferedImage out;
    private int width;
    private int height;
    int startWidth;
    int endWidth;
    int startHeight;
    int endHeight;

    public BlurFilter(BufferedImage in, BufferedImage out, int startWidth, int endWidth, int startHeight, int endHeight) {
        this.in = in;
        this.out = out;
        this.width = in.getWidth();
        this.height = in.getHeight();
        this.startWidth = startWidth;
        this.endWidth = endWidth;
        this.startHeight = startHeight;
        this.endHeight = endHeight;
    }

    private boolean inRange(int x, int y) {
        return ((x >= 0) && (x < width) && (y >= 0) && (y < height));
    }

    private Color bluredColor(int x, int y) {
        int number = 0;
        int blue = 0;
        int red = 0;
        int green = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (inRange(i, j)) {
                    number++;
                    Color color = new Color(in.getRGB(i, j));
                    blue += color.getBlue();
                    red += color.getRed();
                    green += color.getGreen();
                }
            }
        }
        red /= number;
        blue /= number;
        green /= number;
        return new Color(red, green, blue);
    }
    @Override
    public void start() {
        for (int x = startWidth; x < endWidth; x++) {
            for (int y = startHeight; y < endHeight; y++) {
                out.setRGB(x, y, bluredColor(x, y).getRGB());
            }
        }
    }
}
