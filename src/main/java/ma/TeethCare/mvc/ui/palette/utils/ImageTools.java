package ma.TeethCare.mvc.ui.palette.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;

public class ImageTools {

    public static String[] getEnumNames(Class<? extends Enum<?>> enumClass) {
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants == null) {
            throw new IllegalArgumentException("La classe fournie n'est pas une énumération.");
        }

        String[] names = new String[enumConstants.length];
        for (int i = 0; i < enumConstants.length; i++) {
            names[i] = enumConstants[i].name();
        }

        return names;
    }

    public static ImageIcon loadIcon(String path, int w, int h) {
        var url = ImageTools.class.getResource(path);
        if (url == null)
            throw new IllegalArgumentException("Ressource introuvable: " + path);
        Image img = new ImageIcon(url).getImage()
                .getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static ImageIcon resizeImageIcon(ImageIcon originalIcon, int newWidth, int newHeight) {
        Image originalImage = originalIcon.getImage(); // Récupérer l'image de l'ImageIcon
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH); // Redimensionner
        return new ImageIcon(resizedImage); // Retourner une nouvelle ImageIcon
    }

    public static Image scaleImage(Image source, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();
        return img;
    }

    public static Image scaleImage(Image source, int size) {
        int width = source.getWidth(null);
        int height = source.getHeight(null);
        double f = 0;
        if (width < height) {//portrait
            f = (double) height / (double) width;
            width = (int) (size / f);
            height = size;
        } else {//paysage
            f = (double) width / (double) height;
            width = size;
            height = (int) (size / f);
        }
        return scaleImage(source, width, height);
    }

    public static Image scaleImage(final Image source, final double factor) {
        int width = (int) (source.getWidth(null) * factor);
        int height = (int) (source.getHeight(null) * factor);
        return scaleImage(source, width, height);
    }

    public static Color getDominantColor(ImageIcon icon) {
        Image img = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        int red = 0, green = 0, blue = 0, pixelCount = 0;

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                Color color = new Color(rgb);

                if (isSignificantColor(color)) {
                    red += color.getRed();
                    green += color.getGreen();
                    blue += color.getBlue();
                    pixelCount++;
                }
            }
        }

        if (pixelCount == 0) return Color.GRAY;

        red /= pixelCount;
        green /= pixelCount;
        blue /= pixelCount;

        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        hsb[2] = Math.min(1.0f, hsb[2] + 0.2f);
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    private static boolean isSignificantColor(Color color) {
        int brightness = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        return brightness > 30 && brightness < 130;
    }

    public static ImageIcon applyColorOverlay(ImageIcon icon, Color overlayColor, float alpha) {
        if (icon == null || icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) return icon;

        alpha = Math.max(0f, Math.min(1f, alpha));

        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        BufferedImage src = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = src.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(icon.getImage(), 0, 0, null);

        g.setComposite(AlphaComposite.SrcAtop.derive(alpha));
        g.setColor(overlayColor);
        g.fillRect(0, 0, w, h);

        g.dispose();
        return new ImageIcon(src);
    }
}
