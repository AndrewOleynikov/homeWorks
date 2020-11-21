package main;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String in = "./pictures/pic.jpg";
        String out = "./pictures/jpg.jpg";
       // BufferedImage pic = ImageIO.read(file);
       // BufferedImage result = new BufferedImage(pic.getWidth(), pic.getHeight(), pic.getType());

        //BlurFilter blur = new BlurFilter(pic, result,0, pic.getWidth(), 0, pic.getHeight());
        //blur.start();

        BlurThreads b = new BlurThreads(10, in, out);
        b.verticalMode();


    }
}
