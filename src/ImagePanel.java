import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	private int width;
	private int height;
	
	BufferedImage image = null;
	
	public ImagePanel(String fn){
		image = readImageFile(this, fn);
		width = image.getWidth();
		height = image.getHeight();
	}
	
	public static BufferedImage readImageFile(Object requestor, String fileName){
		BufferedImage image = null;
		try {
			InputStream input = requestor.getClass().getResourceAsStream(fileName);
			image = ImageIO.read(input);
		} catch (IOException e){
			String message = "The image file " + fileName + " could not be opened.";
			JOptionPane.showMessageDialog(null, message);
		}
		return image;
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null);
		//g.drawString("I love Home Depot", 100, 100);
	}
	
	public Dimension getPreferredSize() {
		Dimension size = new Dimension(width, height);
		return size;
	}
	
	public void convertToGrayscle() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				//get value for 1 pixel
				int p = image.getRGB(x, y);
				
				int a = (p>>24) & 0xff;
				int r = (p>>16) & 0xff;
				int g = (p>>8) & 0xff;
				int b = (p>>0) & 0xff;
				
				//calculate average
				int avg = (r + g + b)/3;
				
				//reset our pixel
				p = (a<<24) | (avg<<16) | (avg<<8) | (avg<<0);
				image.setRGB(x, y, p);
			}
		}
		
		
	}
	
	public void convertToSepia() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				//get value for 1 pixel
				int p = image.getRGB(x, y);
				
				int a = (p>>24) & 0xff;
				int r = (p>>16) & 0xff;
				int g = (p>>8) & 0xff;
				int b = (p>>0) & 0xff;
				
				int newR = (int)(0.393 * r + 0.769 * g + 0.189 * b);
				int newG = (int)(0.349 * r + 0.686 * g + 0.168 * b);
				int newB = (int)(0.272 * r + 0.534 * g + 0.131 * b);
				
				if (newR > 255) {
					newR = 255;
				}
				if (newG > 255) {
					newG = 255;
				}
				if (newB > 255) {
					newB = 255;
				}
				
				//reset our pixel
				p = (a<<24) | (newR<<16) | (newG<<8) | (newB<<0);
				image.setRGB(x, y, p);
			}
		}
	}

}
