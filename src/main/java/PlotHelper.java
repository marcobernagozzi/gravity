import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlotHelper extends JPanel {
    static float[] latitudes;
    static float[] longitudes;
    static List<Planet> planets = Arrays.asList(
            new Planet(300, 10, 1.5E10, "1"),
            new Planet(10, 500, 1.2E10, "2"),
            new Planet(100, 300, 1.3E10, "3"),
            new Planet(10, 300, 1.1E10, "4"),
            new Planet(500, 700, 1.8E10, "5")
    );
    final static int WIDTH = 1800;
    final static int HEIGHT = 1000;

    public void drawPanels(JPanel panel) {
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(Color.BLACK);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
