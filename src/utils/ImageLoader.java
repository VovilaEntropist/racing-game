package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

	private static final int DEFAULT_WIDTH = 50;
	private static final int DEFAULT_HEIGHT = 50;

	private ImageLoader() {

	}

	public static BufferedImage load(File file) {
		BufferedImage image;

		if (file.exists()) {
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				return generateDefault(DEFAULT_WIDTH, DEFAULT_HEIGHT);
			}

			return image;
		}

		return generateDefault(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	private static BufferedImage generateDefault(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = (Graphics2D) image.getGraphics();
		bGr.drawRect(0, 0, width, height);
		bGr.dispose();
		return image;
	}

}
