package utils;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImageRandom {

	private List<BufferedImage> images = new ArrayList<BufferedImage>();

	public ImageRandom() {

	}

	public boolean add(BufferedImage image) {
		return images.add(image);
	}

	public BufferedImage remove(int index) {
		return images.remove(index);
	}

	public BufferedImage getRandomImage() {
		Random random = new Random();

		int randomIndex = random.nextInt(images.size());

		return images.get(randomIndex);
	}
}
