
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;

/**
 *
 */
public class PlanetsPanel extends PlotHelper {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        double minLat = latitudes[0];
        double minLon = longitudes[0];
        double maxLat = latitudes[0];
        double maxLon = longitudes[0];
        for (int i = 1; i < latitudes.length; i++) {
            double lat = latitudes[i];
            double lon = longitudes[i];
            if(lat  < minLat) {
                minLat = lat;
            }
            if(lat  > maxLat) {
                maxLat = lat;
            }
            if(lon  < minLon) {
                minLon = lon;
            }
            if(lon  > maxLon) {
                maxLon = lon;
            }
        }
        minLon -= 100;
        minLat -= 100;
        maxLon += 100;
        maxLat += 100;

        double rescaleX = (maxLon - minLon) / WIDTH;
        double rescaleY = (maxLat - minLat) / HEIGHT;

        for (int i = 0; i < latitudes.length; i++) {
            g2d.setColor(Color.RED);
            double centerX = (longitudes[i] - 10D / 2 - minLon) / rescaleX;
            double centerY = (latitudes[i] - 10D / 2 - minLat) / rescaleY ;
            Ellipse2D.Double endCustomer = new Ellipse2D.Double(centerX, centerY, 10, 10);
            g2d.draw(endCustomer);
            g2d.fill(endCustomer);
            g2d.setColor(Color.BLACK);
        }
    }
}
