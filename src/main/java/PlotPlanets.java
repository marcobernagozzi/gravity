package main.java;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 */
public class PlotPlanets extends PlotHelper {
    private final PlanetsPanel planetsPanel = new PlanetsPanel();

    public PlotPlanets() {
        PlotHelper.latitudes = new float[planets.size()];
        PlotHelper.longitudes = new float[planets.size()];
        for(int i = 0; i < planets.size(); i++) {
            PlotHelper.latitudes[i] = (float) planets.get(i).y;
            PlotHelper.longitudes[i] = (float) planets.get(i).x;
        }
        drawPanels(planetsPanel);
    }

    public void update() {
        for(int i = 0; i < latitudes.length; i++) {
            PlotHelper.latitudes[i] = (float) planets.get(i).y;
            PlotHelper.longitudes[i] = (float) planets.get(i).x;
        }
        planetsPanel.repaint();
    }
}
