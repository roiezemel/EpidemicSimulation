package algorithm;

import java.util.HashMap;
import java.util.Random;

public class Factors {

    @SuppressWarnings("unchecked")
    public static final HashMap<String, Object>[] FACTORS = map(new Object[][] {

       // Parameters       Children       Young          Old
       {"infection S",     1.005 ,        1.1   ,        1.5   },        // Chance to get infected by a sick person
       {"infection C",     1.001 ,        1.05  ,        1.005 },        // Change to get infected by a carrier
       {"severity",        0.0   ,        0.0   ,        0.0   },        // Chance to die
       {"zone1",           0.0   ,        0.0   ,        0.0   },        // Chance to meet someone from zone1
       {"zone2",           0.0   ,        0.0   ,        0.0   },        // Chance to meet someone from zone2
       {"zone3",           1.    ,        1.    ,        1.    },        // Chance to meet someone from zone3
       {"zone4",           0.    ,        0.    ,        0.    },        // Chance to meet someone from zone4
       {"cTimeAvg",        14.   ,        14.   ,        14.   },        // Average time to be carrier
       {"dTimeAvg",        21.   ,        21.   ,        7.    },        // Average time to be sick if destined to die
       {"iTimeAvg",        30.   ,        20.   ,        20.   },        // Average time to be sick if destined to heal
       {"zone1 time",      3     ,        3     ,        3     },        // Zone1 meeting time
       {"zone2 time",      2     ,        2     ,        2     },        // Zone2 meeting time
       {"zone3 time",      3     ,        3     ,        3     },        // Zone3 meeting time
       {"zone1 amount",    range(3 , 5 ), range(3 , 5 ), range(3 , 5 )}, // Amount of people in zone1
       {"zone2 amount",    range(4 , 20), range(4 , 20), range(4 , 20)}, // Amount of people in zone2

    });

    /**
     * Get a new Range object.
     * @param min Minimum value (inclusive).
     * @param max Maximum value (inclusive).
     * @return Range object.
     */
    private static Range range(int min, int max) {
        return new Range(min, max);
    }

    /**
     * Get a random set of factors.
     * @return HashMap with a set of factors.
     */
    public static HashMap<String, Object> getRandomFactorSet() {
        return FACTORS[new Random().nextInt(FACTORS.length)];
    }

    /**
     * Maps the factors inside a HashMap.
     * names and values should have the same length and same order.
     * @param names Names of the factors.
     * @param values Values of the factors.
     * @return HashMap which stores all factors with their names and values.
     */
    private static HashMap<Object, Object> map(Object[] names, Object[] values) {
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

    /**
     * Maps the factors inside a HashMap.
     * @param dictionary Should be evenly sized. Even indexes -> keys, odd indexes -> values.
     * @return HashMap with factors.
     */
    private static HashMap<Object, Object> map(Object[] dictionary) {
        if (dictionary.length % 2 != 0) {
            try {
                throw new Exception("dictionary should have even size!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < dictionary.length; i += 2) {
            map.put(dictionary[i], dictionary[i + 1]);
        }
        return map;
    }

    /**
     * Maps the factors inside a list (array) of HashMaps.
     * @param dictionary 2D array. First element in each row is the key, and the rest of them are the values for
     *                   different HashMaps.
     * @return Array of HashMaps containing the different factor sets.
     */
    @SuppressWarnings("unchecked")
    private static HashMap<String, Object>[] map(Object[][] dictionary) {
        HashMap<String, Object>[] maps = new HashMap[dictionary[0].length - 1];
        for (int i = 0; i < dictionary.length; i++) {
            for (int j = 1; j < dictionary[i].length; j++) {
                if (maps[j - 1] == null)
                    maps[j - 1] = new HashMap<>();
                maps[j - 1].put((String) dictionary[i][0], dictionary[i][j]);
            }
        }
        return maps;
    }

}
