package algorithm;

import java.io.FileNotFoundException;
import java.util.Formatter;

public class Util {

    /**
     * Poisson distribution.
     * @param avgTime Average time determined from real data.
     * @return chance to move from one state to another.
     */
    public static double poisson(double avgTime) {
        return -avgTime * Math.log(Math.random());
    }

    /**
     * Save two columns into a file.
     * @param path Path to the file. If the file already exist, it will overridden.
     * @param x X column.
     * @param y Y column.
     * @param xTitle X title.
     * @param yTitle Y title.
     */
    public static void save(String path, double[] x, double[] y, String xTitle, String yTitle) {
        if (x.length != y.length)
            try {
                throw new Exception("x and y should have the same size!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            Formatter f = new Formatter(path);
            f.format("%s,%s\n", xTitle, yTitle);
            for (int i = 0; i < x.length; i++)
                f.format("%s,%s\n", x[i], y[i]);
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
