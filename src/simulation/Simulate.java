package simulation;

import algorithm.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Simulate {

    public static void main(String[] args) {

        Population p = new Population(100, 0);
        p.get(0).setState(State.SICK);
        Simulation simulation = new Simulation(p);

        int numDays = 1;
        for (int i = 0; i < numDays; i++) {
            simulation.schedule();
            simulation.eval();
        }

        for (int i = 0; i < p.size(); i++)
            System.out.println(p.get(i).getInfections());

        System.out.println(simulation.avg(Person::getInfections));
//        System.out.println(simulation.avg(Person::getInfections));
//        System.out.println(simulation.getCountInfections());
    }

//    double[] days = new double[numDays];
//    double[] healthy = new double[numDays];
//    double[] carriers = new double[numDays];
//    double[] sicks = new double[numDays];
//    double[] immune = new double[numDays];
//    double[] dead = new double[numDays];
//    double[] times = new double[numDays];
//    double[] dis = new double[numDays];
//
//    double max = 0;
//        for (int i = 0; i < numDays; i++) {
//        simulation.schedule();
//        simulation.eval();
//        days[i] = i;
//        healthy[i] = simulation.count(State.HEALTHY);
//        carriers[i] = simulation.count(State.CARRIER);
//        sicks[i] = simulation.count(State.SICK);
//        immune[i] = simulation.count(State.IMMUNE);
//        dead[i] = simulation.getCountDead();
//        int d = (int)(Util.poisson( 50.));
//        if (d < numDays) {
//            dis[d]++;
//            if (max < dis[d])
//                max = dis[d];
//        }
//    }
//
//        for (int i = 0; i < numDays; i++) {
//        times[i] = Math.exp(-i / 50.0) * max;
//    }

//        Util.save("dh.csv", days, healthy, "Days", "Healthy");
//        Util.save("dc.csv", days, carriers, "Days", "Carrier");
//        Util.save("ds.csv", days, sicks, "Days", "Sick");
//        Util.save("di.csv", days, immune, "Days", "Immune");
//        Util.save("dd.csv", days, dead, "Days", "Dead");
//        Util.save("data.csv", days, times, "Days", "Times");
//        Util.save("poisson.c

}
