package main.java;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 */
public class PlotPlanets extends PlotHelper {
    private final PlanetsPanel planetsPanel = new PlanetsPanel();

    public PlotPlanets(Planet[] planets) {
        PlotHelper.latitudes = new float[planets.length];
        PlotHelper.longitudes = new float[planets.length];
        for (int i = 0; i < planets.length; i++) {
            PlotHelper.latitudes[i] = (float) planets[i].y;
            PlotHelper.longitudes[i] = (float) planets[i].x;
        }
        drawPanels(planetsPanel);
    }

    public void update(Planet[] planets) {
        for (int i = 0; i < latitudes.length; i++) {
            PlotHelper.latitudes[i] = (float) planets[i].y;
            PlotHelper.longitudes[i] = (float) planets[i].x;
        }
        planetsPanel.repaint();
    }
}
