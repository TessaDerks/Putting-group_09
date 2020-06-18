package sampl;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PerlinNoiseGen {

    private float perlin[][];
    private Image image;

    public PerlinNoiseGen(int width, int height)
    {
        perlin = new float[width][height];
    }

    public double smoothNoise(double x, double y, int width, int height) {
        // get fractional part of x and y
        double fractX = x - (int) x;
        double fractY = y - (int) y;

        // wrap around
        int x1 = (int) x + width % width;
        int y1 = (int) y + height % height;

        // neighbor values
        int x2 = (x1 + width - 1) % width;
        int y2 = (y1 + height - 1) % height;

        // smooth the noise with bilinear interpolation
        double value = 0.0;
        value += fractX * fractY * perlin[y1][x1];
        value += (1 - fractX) * fractY * perlin[y1][x2];
        value += fractX * (1 - fractY) * perlin[y2][x1];
        value += (1 - fractX) * (1 - fractY) * perlin[y2][x2];

        return value;
    }

    public double turb(double x, double y, double size, int width, int height) {
        double value = 0.0, initialSize = size;

        while (size >= 1) {
            value += smoothNoise(x / size, y / size, width, height) * size;
            size /= 2.0;
        }

        return (130.0 * value / initialSize);
    }

    public BufferedImage GeneratePerlin(int width, int height) throws IOException {
        Random generator = new Random();
        perlin = new float[width][height];
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < result.getHeight() - 1; y++) {
            for (int x = 0; x < result.getWidth() - 1; x++) {
                perlin[x][y] = generator.nextFloat();
            }
        }
        for (int y = 0; y < result.getHeight() - 1; y++) {
            for (int x = 0; x < result.getWidth() - 1; x++) {
                int cor = (int) turb(x, y, 1000, width, height);
                result.setRGB(x, y, new Color(cor, cor, cor).getRGB());
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int tone1 = new Color(result.getRGB(x, y)).getRed();
                int tone2 = x > 1 ? new Color(result.getRGB(x-1, y)).getRed() : tone1;
                int tone3 = y > 1 ? new Color(result.getRGB(x, y-1)).getRed() : tone1;
                int tone4 = x < width - 1 ? new Color(result.getRGB(x+1, y)).getRed() : tone1;
                int tone5 = y < height - 1 ? new Color(result.getRGB(x, y+1)).getRed() : tone1;
                int h = (tone1 + tone2 + tone3 + tone4 + tone5) / 5;
                result.setRGB(x, y, new Color(h,h,h).getRGB());
            }
        }

        ImageIO.write(result, "png", new File("res/perlinNoise.png"));
        return result;
    }

}