package main.java;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlotHelper extends JPanel {
    static float[] latitudes;
    static float[] longitudes;

    public void drawPanels(JPanel panel) {
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 1800, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(Color.BLACK);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
