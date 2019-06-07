package main.java;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 */
public class PlotPlanets extends PlotHelper {
    private final PlanetsPanel planetsPanel = new PlanetsPanel();

<<<<<<< HEAD
    public PlotPlanets() {
        PlotHelper.latitudes = new float[planets.size()];
        PlotHelper.longitudes = new float[planets.size()];
        for(int i = 0; i < planets.size(); i++) {
            PlotHelper.latitudes[i] = (float) planets.get(i).y;
            PlotHelper.longitudes[i] = (float) planets.get(i).x;
=======
    public PlotPlanets(Planet[] planets) {
        PlotHelper.latitudes = new float[planets.length];
        PlotHelper.longitudes = new float[planets.length];
        for (int i = 0; i < planets.length; i++) {
            PlotHelper.latitudes[i] = (float) planets[i].y;
            PlotHelper.longitudes[i] = (float) planets[i].x;
>>>>>>> 1f5524eefb282b0bfbda643bd97612f7486e4d11
        }
        drawPanels(planetsPanel);
    }

<<<<<<< HEAD
    public void update() {
        for(int i = 0; i < latitudes.length; i++) {
            PlotHelper.latitudes[i] = (float) planets.get(i).y;
            PlotHelper.longitudes[i] = (float) planets.get(i).x;
=======
    public void update(Planet[] planets) {
        for (int i = 0; i < latitudes.length; i++) {
            PlotHelper.latitudes[i] = (float) planets[i].y;
            PlotHelper.longitudes[i] = (float) planets[i].x;
>>>>>>> 1f5524eefb282b0bfbda643bd97612f7486e4d11
        }
        planetsPanel.repaint();
    }
}
