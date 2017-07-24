package in.clouthink.daas.sbb.shared.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.springframework.util.Assert;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageUtils {

	private enum Method {

		auto,

		jdk,

		graphicsMagick,

		imageMagick
	}

	private static ImageUtils.Method method = ImageUtils.Method.auto;

	private static String graphicsMagickPath;

	private static String imageMagickPath;

	private static final Color BACKGROUND_COLOR = Color.white;

	private static final int DEST_QUALITY = 88;

	static {
		if (SystemUtils.IS_OS_WINDOWS) {
			String pathVariable = System.getenv("Path");
			if (StringUtils.isNotEmpty(pathVariable)) {
				String[] paths = StringUtils.split(pathVariable, ";");
				if (graphicsMagickPath == null) {
					for (String path : paths) {
						File gmFile = new File(path.trim() + "/gm.exe");
						File gmdisplayFile = new File(path.trim() + "/gmdisplay.exe");
						if (gmFile.exists() && gmdisplayFile.exists()) {
							graphicsMagickPath = path.trim();
							break;
						}
					}
				}
				if (imageMagickPath == null) {
					for (String path : paths) {
						File convertFile = new File(path.trim() + "/convert.exe");
						File compositeFile = new File(path.trim() + "/composite.exe");
						if (convertFile.exists() && compositeFile.exists()) {
							imageMagickPath = path.trim();
							break;
						}
					}
				}
			}
		}

		if (ImageUtils.Method.auto.equals(method)) {
			try {
				IMOperation operation = new IMOperation();
				operation.version();
				IdentifyCmd identifyCmd = new IdentifyCmd(true);
				if (graphicsMagickPath != null) {
					identifyCmd.setSearchPath(graphicsMagickPath);
				}
				identifyCmd.run(operation);
				method = ImageUtils.Method.graphicsMagick;
			}
			catch (Throwable e1) {
				try {
					IMOperation operation = new IMOperation();
					operation.version();
					IdentifyCmd identifyCmd = new IdentifyCmd(false);
					identifyCmd.run(operation);
					if (imageMagickPath != null) {
						identifyCmd.setSearchPath(imageMagickPath);
					}
					method = ImageUtils.Method.imageMagick;
				}
				catch (Throwable e2) {
					method = ImageUtils.Method.jdk;
				}
			}
		}
	}

	private ImageUtils() {
	}

	public static boolean isSupported(String fileSuffix) {
		return (fileSuffix.equalsIgnoreCase("jpg") ||
				fileSuffix.equalsIgnoreCase("jpeg") ||
				fileSuffix.equalsIgnoreCase("bmp") ||
				fileSuffix.equalsIgnoreCase("gif") ||
				fileSuffix.equalsIgnoreCase("png"));
	}

	public static void zoom(File srcFile, File destFile, int destWidth, int destHeight) {
		Assert.notNull(srcFile);
		Assert.state(srcFile.exists());
		Assert.state(srcFile.isFile());
		Assert.notNull(destFile);
		Assert.state(destWidth > 0);
		Assert.state(destHeight > 0);

		if (ImageUtils.Method.jdk.equals(method)) {
			Graphics2D graphics2D = null;
			ImageOutputStream imageOutputStream = null;
			ImageWriter imageWriter = null;
			try {
				BufferedImage srcBufferedImage = ImageIO.read(srcFile);
				int srcWidth = srcBufferedImage.getWidth();
				int srcHeight = srcBufferedImage.getHeight();
				int width = destWidth;
				int height = destHeight;
				if (srcHeight >= srcWidth) {
					width = (int) Math.round(((destHeight * 1.0 / srcHeight) * srcWidth));
				}
				else {
					height = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
				}

				BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				graphics2D = destBufferedImage.createGraphics();
				graphics2D.setBackground(BACKGROUND_COLOR);
				graphics2D.clearRect(0, 0, destWidth, destHeight);
				graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
									 (destWidth / 2) - (width / 2),
									 (destHeight / 2) - (height / 2),
									 null);

				imageOutputStream = ImageIO.createImageOutputStream(destFile);
				imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
									 .next();
				imageWriter.setOutput(imageOutputStream);

				ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
				imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
				imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
				imageOutputStream.flush();
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			finally {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
				if (imageWriter != null) {
					imageWriter.dispose();
				}
				try {
					if (imageOutputStream != null) {
						imageOutputStream.close();
					}
				}
				catch (IOException e) {
				}
			}
		}
		else {
			IMOperation operation = new IMOperation();
			operation.thumbnail(destWidth, destHeight);
			operation.gravity("center");
			operation.background(toHexEncoding(BACKGROUND_COLOR));
			operation.extent(destWidth, destHeight);
			operation.quality((double) DEST_QUALITY);
			try {
				operation.addImage(srcFile.getCanonicalPath());
				operation.addImage(destFile.getCanonicalPath());
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			if (ImageUtils.Method.graphicsMagick.equals(method)) {
				ConvertCmd convertCmd = new ConvertCmd(true);
				if (graphicsMagickPath != null) {
					convertCmd.setSearchPath(graphicsMagickPath);
				}
				try {
					convertCmd.run(operation);
				}
				catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				catch (InterruptedException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				catch (IM4JavaException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			else {
				ConvertCmd convertCmd = new ConvertCmd(false);
				if (imageMagickPath != null) {
					convertCmd.setSearchPath(imageMagickPath);
				}
				try {
					convertCmd.run(operation);
				}
				catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				catch (InterruptedException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				catch (IM4JavaException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	public static void zoomByWidth(File srcFile, File destFile, int destWidth) {
		Assert.notNull(srcFile);
		Assert.state(srcFile.exists());
		Assert.state(srcFile.isFile());
		Assert.notNull(destFile);
		Assert.state(destWidth > 0);

		if (ImageUtils.Method.jdk.equals(method)) {
			Graphics2D graphics2D = null;
			ImageOutputStream imageOutputStream = null;
			ImageWriter imageWriter = null;
			try {
				BufferedImage srcBufferedImage = ImageIO.read(srcFile);
				int srcWidth = srcBufferedImage.getWidth();
				int srcHeight = srcBufferedImage.getHeight();
				int width = srcWidth;
				int height = srcHeight;

				if (srcWidth > destWidth) {
					width = destWidth;
					height = (int) Math.round((destWidth * 1.0 / srcWidth) * srcHeight);
				}

				BufferedImage destBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				graphics2D = destBufferedImage.createGraphics();
				graphics2D.setBackground(BACKGROUND_COLOR);
				graphics2D.clearRect(0, 0, width, height);
				graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

				imageOutputStream = ImageIO.createImageOutputStream(destFile);
				imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
									 .next();
				imageWriter.setOutput(imageOutputStream);
				ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
				try {
					imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
					imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
				}
				catch (Throwable e) {
					//ignore it
				}
				imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
				imageOutputStream.flush();
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			finally {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
				if (imageWriter != null) {
					imageWriter.dispose();
				}
				try {
					if (imageOutputStream != null) {
						imageOutputStream.close();
					}
				}
				catch (IOException e) {
				}
			}
		}
		else {
			// TODO
		}
	}

	/**
	 * @param srcFile
	 * @param destFile
	 * @param destWidth 如果目标宽度超过原始图片的宽高（以最大为准）,则直接返回图片,否则进行压缩,或者填充.
	 * @param filling   true,如果实际图片小余目标值,则进行填充,否则,直接返回该图片
	 */
	public static void zoomBySquare(File srcFile, File destFile, int destWidth, boolean filling) {
		Assert.notNull(srcFile);
		Assert.state(srcFile.exists());
		Assert.state(srcFile.isFile());
		Assert.notNull(destFile);
		Assert.state(destWidth > 0);

		if (ImageUtils.Method.jdk.equals(method)) {
			Graphics2D graphics2D = null;
			ImageOutputStream imageOutputStream = null;
			ImageWriter imageWriter = null;
			try {
				BufferedImage srcBufferedImage = ImageIO.read(srcFile);
				int srcWidth = srcBufferedImage.getWidth();
				int srcHeight = srcBufferedImage.getHeight();

				int width = srcWidth;
				int height = srcHeight;
				int x = 0;
				int y = 0;

				//if the original image is square, just zoom it as required
				if (srcWidth == srcHeight) {
					if (srcWidth >= destWidth) {
						width = destWidth;
						height = destWidth;
					}
					else if (srcWidth < destWidth) {
						width = srcWidth;
						height = srcWidth;
					}

					BufferedImage destBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					graphics2D = destBufferedImage.createGraphics();
					graphics2D.setBackground(BACKGROUND_COLOR);
					graphics2D.clearRect(0, 0, width, height);
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
										 x,
										 y,
										 null);

					imageOutputStream = ImageIO.createImageOutputStream(destFile);
					imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
										 .next();
					imageWriter.setOutput(imageOutputStream);
					ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
					try {
						imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
						imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
					}
					catch (Throwable e) {
						//ignore it
					}
					imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
					imageOutputStream.flush();
				}
				else {
					if (srcWidth < destWidth && srcHeight < destWidth) {
						if (filling) {
							width = Math.max(srcWidth, srcHeight);
							height = Math.max(srcWidth, srcHeight);
							if (srcWidth < srcHeight) {
								x = Math.abs(srcHeight - srcWidth) / 2;
							}
							else {
								y = Math.abs(srcWidth - srcHeight) / 2;
							}
						}
						else {
							width = srcWidth;
							height = srcHeight;
						}

						BufferedImage destBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
						graphics2D = destBufferedImage.createGraphics();
						graphics2D.setBackground(BACKGROUND_COLOR);
						graphics2D.clearRect(0, 0, width, height);

						graphics2D.drawImage(srcBufferedImage, x, y, null);

						imageOutputStream = ImageIO.createImageOutputStream(destFile);
						imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
											 .next();
						imageWriter.setOutput(imageOutputStream);
						ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
						try {
							imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
							imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
						}
						catch (Throwable e) {
							//ignore it
						}
						imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
						imageOutputStream.flush();

					}
					else if (srcWidth > destWidth && srcHeight > destWidth) {
						int cropX = 0;
						int cropY = 0;
						int cropWidth = 0;
						int cropHeight = 0;
						if (srcWidth > srcHeight) {
							//宽度大于高度,以高度为准进行crop
							width = destWidth;
							height = destWidth;
							x = 0;
							y = 0;

							cropWidth = cropHeight = srcHeight;
							cropX = Math.abs(srcWidth - srcHeight) / 2;
						}
						else {
							//宽度小于高度,以款度为准进行crop
							width = destWidth;
							height = destWidth;
							x = 0;
							y = 0;

							cropWidth = cropHeight = srcWidth;
							cropY = Math.abs(srcWidth - srcHeight) / 2;
						}

						BufferedImage cropBufferedImage = srcBufferedImage.getSubimage(cropX,
																					   cropY,
																					   cropWidth,
																					   cropHeight);

						BufferedImage destBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
						graphics2D = destBufferedImage.createGraphics();
						graphics2D.setBackground(BACKGROUND_COLOR);
						graphics2D.clearRect(0, 0, width, height);

						graphics2D.drawImage(cropBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
											 x,
											 y,
											 null);

						imageOutputStream = ImageIO.createImageOutputStream(destFile);
						imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
											 .next();
						imageWriter.setOutput(imageOutputStream);
						ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
						try {
							imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
							imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
						}
						catch (Throwable e) {
							//ignore it
						}
						imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
						imageOutputStream.flush();

					}
					else if (srcWidth > destWidth) {
						int cropX = Math.abs(srcWidth - destWidth) / 2;
						int cropY = 0;
						int cropWidth = destWidth;
						int cropHeight = srcHeight;

						if (filling) {
							width = destWidth;
							height = destWidth;
							x = 0;
							y = (destWidth - srcHeight) / 2;
						}
						else {
							width = destWidth;
							height = srcHeight;
						}

						BufferedImage cropBufferedImage = srcBufferedImage.getSubimage(cropX,
																					   cropY,
																					   cropWidth,
																					   cropHeight);

						BufferedImage destBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
						graphics2D = destBufferedImage.createGraphics();
						graphics2D.setBackground(BACKGROUND_COLOR);
						graphics2D.clearRect(0, 0, width, height);
						graphics2D.drawImage(cropBufferedImage, x, y, null);

						imageOutputStream = ImageIO.createImageOutputStream(destFile);
						imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
											 .next();
						imageWriter.setOutput(imageOutputStream);
						ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
						try {
							imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
							imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
						}
						catch (Throwable e) {
							//ignore it
						}
						imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
						imageOutputStream.flush();
					}
					else if (srcHeight > destWidth) {
						int cropX = 0;
						int cropY = Math.abs(srcHeight - destWidth) / 2;
						int cropWidth = srcWidth;
						int cropHeight = destWidth;

						if (filling) {
							width = destWidth;
							height = destWidth;
							x = (Math.abs(destWidth - srcWidth) / 2);
							y = 0;
						}
						else {
							width = destWidth;
							height = srcHeight;
						}

						BufferedImage cropBufferedImage = srcBufferedImage.getSubimage(cropX,
																					   cropY,
																					   cropWidth,
																					   cropHeight);

						BufferedImage destBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
						graphics2D = destBufferedImage.createGraphics();
						graphics2D.setBackground(BACKGROUND_COLOR);
						graphics2D.clearRect(0, 0, width, height);

						graphics2D.drawImage(cropBufferedImage, x, y, null);

						imageOutputStream = ImageIO.createImageOutputStream(destFile);
						imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
											 .next();
						imageWriter.setOutput(imageOutputStream);
						ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
						try {
							imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
							imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
						}
						catch (Throwable e) {
							//ignore it
						}
						imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
						imageOutputStream.flush();
					}
				}
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			finally {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
				if (imageWriter != null) {
					imageWriter.dispose();
				}
				try {
					if (imageOutputStream != null) {
						imageOutputStream.close();
					}
				}
				catch (IOException e) {
				}
			}
		}
		else {

		}
	}

	private static String toHexEncoding(Color color) {
		Assert.notNull(color);

		String r, g, b;
		StringBuilder result = new StringBuilder();
		r = Integer.toHexString(color.getRed());
		g = Integer.toHexString(color.getGreen());
		b = Integer.toHexString(color.getBlue());
		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;
		result.append("#").append(r).append(g).append(b);
		return result.toString();
	}

}