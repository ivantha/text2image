package com.ivantha;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Text2Image extends JPanel {

    public static final String TXT_PATH = "https://raw.githubusercontent.com/sinhala-ocr/tess-ta/master/samples/mawbima.txt";
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static void main(String[] args) {
        BufferedImage bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        // antialiasing
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        try (Scanner scanner = new Scanner(new URL(TXT_PATH).openStream())) {
            final String text = readText(scanner);

            // paint white background
            g2d.setPaint(Color.white);
            g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

            // set font
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("iskpota.ttf"));
//            g2d.setFont(new Font("Roboto", Font.PLAIN, 12));
            g2d.setFont(font.deriveFont(14f));
            g2d.setColor(Color.black);

            FontMetrics fontMetrics = g2d.getFontMetrics();
            int x = 20;
            int y = 20;
            g2d.drawString(text, x, y);
            g2d.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        File myNewTIFF_File = new File("ImageAsTIFF.tiff");
        try {
            ImageIO.write(bufferedImage, "TIFF", myNewTIFF_File);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String readText(Scanner scanner) {
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (!line.isEmpty()) {
                sb.append(line);
                sb.append(NEW_LINE);
            }
        }
        return sb.toString();
    }

}
