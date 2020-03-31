package algorithm;

import java.util.HashMap;
import java.util.Random;

public class Factors {

    @SuppressWarnings("unchecked")
    public static final HashMap<String, Double>[] FACTORS = new HashMap[]{

            // 0 - 18
            map(new String[] {"infection", "severity", "zone1", "zone2", "zone3", "zone4"},
                    new Double[] {0.1,        0.3,       0.8,    0.4,      0.2,    0.01}),

            // 20 - 60
            map(new String[] {"infection", "severity", "zone1", "zone2", "zone3", "zone4"},
                    new Double[] {0.3,        0.5,       0.8,     0.5,     0.6,     0.2}),

            // 60 +
            map(new String[] {"infection", "severity", "zone1", "zone2", "zone3", "zone4"},
                    new Double[] {0.8,        0.7,       0.8,    0.01,     0.01,    0.01})

    };

    public static final HashMap<Object, Object> MEETING_TIMES = map(
            new String[] {"zone1", "zone2", "zone3"},
            new Double[] {   3d,     2d,      1d}
    );

    public static final HashMap<Object, Object> PEOPLE_FOR_ZONE = map(
            new String[] {   "zone1",          "zone2"},
            new Range[] {new Range(3, 5), new Range(4, 8)}
    );

    /**
     * Get a random set of factors.
     * @return HashMap with a set of factors.
     */
    public static HashMap<String, Double> getRandomFactorSet() {
        return FACTORS[new Random().nextInt(FACTORS.length)];
    }

    /**
     * Maps the factors inside a HashMap.
     * names and values should have the same length and same order.
     * @param names Names of the factors.
     * @param values Values of the factors.
     * @return HashMap which stores all factors with their names and values.
     */
    public static HashMap<Object, Object> map(Object[] names, Object[] values) {
        if (names.length != values.length)
            try {
                throw new Exception("names and values should have the same length!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], values[i]);
        }
        return map;
    }

}
