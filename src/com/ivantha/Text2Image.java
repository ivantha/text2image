package com.ivantha;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Text2Image extends JPanel {

    public static final String IMG_PATH = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Thomas_Hicks_-_Leopold_Grozelier_-_Presidential_Candidate_Abraham_Lincoln_1860.jpg/456px-Thomas_Hicks_-_Leopold_Grozelier_-_Presidential_Candidate_Abraham_Lincoln_1860.jpg";
    public static final String TXT_PATH = "https://raw.githubusercontent.com/sinhala-ocr/tess-ta/master/samples/mawbima.txt";
    public static final String NEW_LINE = System.getProperty("line.separator");
    private BufferedImage backgroundImg = null;

    public Text2Image(BufferedImage img, String text) {
        backgroundImg = img;

        JTextArea textArea = new JTextArea(text);
        textArea.setOpaque(false);
        setLayout(new GridBagLayout());
        add(textArea);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet() || backgroundImg == null) {
            return super.getPreferredSize();
        }
        int w = backgroundImg.getWidth();
        int h = backgroundImg.getHeight();
        return new Dimension(w, h);
    }

    private static void createAndShowGui(BufferedImage img, String text) {
        Text2Image mainPanel = new Text2Image(img, text);

        JFrame frame = new JFrame("DrawOnImg");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            URL txtUrl = new URL(TXT_PATH);
            scanner = new Scanner(txtUrl.openStream());
            final String text = readText(scanner);

            URL imgUrl = new URL(IMG_PATH);
            final BufferedImage img = ImageIO.read(imgUrl);
            SwingUtilities.invokeLater(() -> createAndShowGui(img, text));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
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
