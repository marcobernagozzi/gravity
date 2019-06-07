
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 *
 */
public class ComputeGravitationalForce extends PlotHelper {
    public final static double G = 6.67408 * 10E-11;

    public static void main(String[] args) throws IOException {

        int n = planets.size();
        PlotPlanets plotPlanets = new PlotPlanets();
        double interval = 1; // in seconds
        double[][][] accelerations = new double[n][n][2];

        while (true) {
            ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    exec.submit(new ComputeAccelerations(i, j, accelerations));
                }
            }
            exec.shutdown();
            while (!exec.isTerminated()) {
                try {
                    exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (int i = 0; i < n; i++) {
                exec.submit(new ComputePositions(accelerations, i, interval));
            }
            exec.shutdown();
            while (!exec.isTerminated()) {
                try {
                    exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mergePlanets();
            plotPlanets.update();
        }
    }

    private static class ComputePositions implements Runnable {
        double[][][] accelerations;
        int planetId;
        double t;

        public ComputePositions(double[][][] accelerations, int planetId, double t) {
            this.accelerations = accelerations;
            this.planetId = planetId;
            this.t = t;
        }

        @Override
        public void run() {
            double totalAccelerationX = 0;
            double totalAccelerationY = 0;
            for (int j = 0; j < planets.size(); j++) {
                totalAccelerationX += accelerations[planetId][j][0];
                totalAccelerationY += accelerations[planetId][j][1];
            }
            Planet planet = planets.get(planetId);
            planet.x = planet.x + planet.speedX * t + 0.5 * totalAccelerationX * Math.pow(t, 2);
            planet.y = planet.y + planet.speedY * t + 0.5 * totalAccelerationY * Math.pow(t, 2);
            planet.speedX = planet.speedX + totalAccelerationX * t;
            planet.speedY = planet.speedY + totalAccelerationY * t;
        }
    }

    private static class ComputeAccelerations implements Runnable {
        int a;
        int b;
        double[][][] accelerations;

        public ComputeAccelerations(int a, int b, double[][][] accelerations) {
            this.a = a;
            this.b = b;
            this.accelerations = accelerations;
        }

        @Override
        public void run() {
            Planet planetA = planets.get(a);
            Planet planetB = planets.get(b);
            double deltaX = planetA.x - planetB.x;
            double deltaY = planetA.y - planetB.y;
            double lim = 5;
            if (deltaX > 0 && deltaX < lim) {
                deltaX = lim;
            }
            if (deltaY > 0 && deltaY < lim) {
                deltaY = lim;
            }
            if (deltaX < 0 && deltaX > -lim) {
                deltaX = -lim;
            }
            if (deltaY < 0 && deltaY > -lim) {
                deltaY = -lim;
            }
            double squaredDeltax = deltaX * deltaX;
            double squaredDeltay = deltaY * deltaY;
            double forceOverMassSquared = G / (squaredDeltay + squaredDeltax);
            double accelerationA = forceOverMassSquared * planetB.mass;
            double accelerationB = forceOverMassSquared * planetA.mass;
            if (deltaY != 0) {
                double var1 = Math.sqrt(1 / (1 + squaredDeltax / squaredDeltay));
                double var2 = Math.abs(deltaX / deltaY);
                accelerations[a][b][0] = accelerationA * var1 * var2 * (deltaX > 0 ? -1 : 1);
                accelerations[a][b][1] = accelerationA * var1 * (deltaY > 0 ? -1 : 1);
                accelerations[b][a][0] = accelerationB * var1 * var2 * (deltaX < 0 ? -1 : 1);
                accelerations[b][a][1] = accelerationB * var1 * (deltaY < 0 ? -1 : 1);
            } else {
                accelerations[a][b][0] = accelerationA * (deltaX > 0 ? -1 : 1);
                accelerations[a][b][1] = 0;
                accelerations[b][a][0] = accelerationB * (deltaX < 0 ? -1 : 1);
                accelerations[b][a][1] = 0;
            }
        }
    }

    private static void mergePlanets() {
        /*Map<String[], Double> planetsToDist = new LinkedHashMap<>();
        for(int i = 0; i < planets.size(); i++) {
            Planet planeti = planets.get(i);
            for (int j = i + 1; j < planets.size(); j++) {
                Planet planetj = planets.get(j);
                // squared distance
                double dist = Math.pow(planeti.x - planetj.x, 2) + Math.pow(planeti.y - planetj.y, 2);
                if(dist < 10) {
                    planetsToDist.put(new String[]{planeti.id,planetj.id}, dist);
                }
            }
        }
        Map<String[], Double> sorted = planetsToDist
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        Set<String> updated = new HashSet<>();*/
    }
}

