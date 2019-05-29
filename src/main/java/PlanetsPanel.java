
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 *
 */
public class PlanetsPanel extends PlotHelper {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < latitudes.length; i++) {
            g2d.setColor(Color.RED);
            double centerX = longitudes[i] - 10D / 2;
            double centerY = latitudes[i] - 10D / 2;
            Ellipse2D.Double endCustomer = new Ellipse2D.Double(centerX, centerY, 10, 10);
            g2d.draw(endCustomer);
            g2d.fill(endCustomer);
            g2d.setColor(Color.BLACK);
        }
    }
}
