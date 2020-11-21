package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BlurThreads {

    private int countOfThreads;
    private BufferedImage in;
    private BufferedImage out;
    private int width;
    private int height;
    private File file;
    private String inName;
    private String outName;

    public BlurThreads(int countOfThreads, String inName, String outName) throws IOException {
        this.inName = inName;
        this.outName = outName;
        readFile();
        this.countOfThreads = countOfThreads;
        this.width = in.getWidth();
        this.height = in.getHeight();
    }

    private void readFile() throws IOException {
        File file = new File(inName);
        in = ImageIO.read(file);
        out = new BufferedImage(in.getWidth(), in.getHeight(), in.getType());
    }

    public void writeFile() throws IOException {
        File file = new File(outName);
        ImageIO.write(out, "jpg", file);
    }

    public void horizontalMode() throws InterruptedException, IOException {
        int step = height / countOfThreads;
        ArrayList<BlurFilter> threads = new ArrayList<>();
        for (int i = 0; i < height; i += step) {
            int stepTo;
            if (i == countOfThreads - 1) {
                stepTo = height;
            } else {
                stepTo = i + step;
            }
            BlurFilter thread = new BlurFilter(in, out, 0, width, i, stepTo);
            threads.add(thread);
            thread.start();
        }
        for (BlurFilter thread : threads) {
            thread.join();
        }
        //writeFile();
        //File output = new File("./pictures/jpg.jpg");
        //ImageIO.write(out, "jpg", output);
    }

    public void verticalMode() throws InterruptedException, IOException {
        int step = width / countOfThreads;
        ArrayList<BlurFilter> threads = new ArrayList<>();
        for (int i = 0; i < width; i += step) {
            int stepTo;
            if (i == countOfThreads - 1) {
                stepTo = width;
            } else {
                stepTo = i + step;
            }
            BlurFilter thread = new BlurFilter(in, out, i, stepTo, 0, height);
            threads.add(thread);
            thread.start();
        }
        for (BlurFilter thread : threads) {
            thread.join();
        }
       // writeFile();
        //File output = new File("./pictures/jpg.jpg");
        //ImageIO.write(out, "jpg", output);
    }

}
