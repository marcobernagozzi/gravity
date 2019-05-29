
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ComputeGravitationalForce {
    public final static double G = 6.67408 * 10E-11;

    public static void main(String[] args) throws IOException {
        double mass = 1E10;
        Planet[] planets = new Planet[]{
            new Planet(300, 10, mass),
            new Planet(10, 500, mass),
            /*new Planet(10, distance, mass),*/
            new Planet(500, 700, mass),
        };

        int n = planets.length;
        PlotPlanets plotPlanets = new PlotPlanets(planets);
        double interval = 1; // in seconds
        double[][][] accelerations = new double[n][n][2];

        while(true) {
            ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    exec.submit(new ComputeAccelerations(i, j, planets, accelerations));
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
                exec.submit(new ComputePositions(planets, accelerations, i, interval));
            }
            exec.shutdown();
            while (!exec.isTerminated()) {
                try {
                    exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            plotPlanets.update(planets);
        }
    }

    private static class ComputePositions implements Runnable {
        Planet[] planets;
        double[][][] accelerations;
        int planetId;
        double t;

        public ComputePositions(Planet[] planets, double[][][] accelerations, int planetId, double t) {
            this.planets = planets;
            this.accelerations = accelerations;
            this.planetId = planetId;
            this.t = t;
        }

        @Override
        public void run() {
            double totalAccelerationX = 0;
            double totalAccelerationY = 0;
            for (int j = 0; j < planets.length; j++) {
                totalAccelerationX += accelerations[planetId][j][0];
                totalAccelerationY += accelerations[planetId][j][1];
            }
            Planet planet = planets[planetId];
            planet.x = planet.x + planet.speedX * t + 0.5 * totalAccelerationX * Math.pow(t, 2);
            planet.y = planet.y + planet.speedY * t + 0.5 * totalAccelerationY * Math.pow(t, 2);
            planet.speedX = planet.speedX + totalAccelerationX * t;
            planet.speedY = planet.speedY + totalAccelerationY * t;
        }
    }

    private static class ComputeAccelerations implements Runnable {
        int a;
        int b;
        Planet[] planets;
        double[][][] accelerations;

        public ComputeAccelerations(int a, int b, Planet[] planets, double[][][] accelerations) {
            this.a = a;
            this.b = b;
            this.planets = planets;
            this.accelerations = accelerations;
        }

        @Override
        public void run() {
            Planet planetA = planets[a];
            Planet planetB = planets[b];
            double deltaX = planetA.x - planetB.x;
            double deltaY = planetA.y - planetB.y;
            double distance = Math.sqrt(Math.pow(deltaY, 2) + Math.pow(deltaX, 2));
            double force = G * planetA.mass * planetB.mass / (Math.pow(distance, 2));

            double accelerationA = force / planetA.mass;
            double accelerationB = force / planetB.mass;
            if(deltaY != 0) {
                double accelerationAY = accelerationA * Math.sqrt(1 / (1 + Math.pow(deltaX / deltaY, 2)));
                double accelerationAX = accelerationAY * Math.abs(deltaX / deltaY);
                double accelerationBY = accelerationB * Math.sqrt(1 / (1 + Math.pow(deltaX / deltaY, 2)));
                double accelerationBX = accelerationBY * Math.abs(deltaX / deltaY);
                accelerations[a][b][0] = accelerationAX * (deltaX > 0 ? -1 : 1);
                accelerations[a][b][1] = accelerationAY * (deltaY > 0 ? -1 : 1);
                accelerations[b][a][0] = accelerationBX  * (deltaX < 0 ? -1 : 1);
                accelerations[b][a][1] = accelerationBY  * (deltaY < 0 ? -1 : 1);
            } else {
                accelerations[a][b][0] = accelerationA  * (deltaX > 0 ? -1 : 1);
                accelerations[a][b][1] = 0;
                accelerations[b][a][0] = accelerationB  * (deltaX < 0 ? -1 : 1);
                accelerations[b][a][1] = 0;
            }
        }
    }
}

