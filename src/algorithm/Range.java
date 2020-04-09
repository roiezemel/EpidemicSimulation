package algorithm;

import java.util.Random;

public class Range {

    public int min;
    public int max;

    /**
     * Generate range.
     * @param min
     * @param max
     */
    public Range(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        this.min = min;
        this.max = max;
    }

    /**
     * Get a random integer in the range. min and max are inclusive.
     * @return
     */
    public int random() {
        return new Random().nextInt((max - min) + 1) + min;
    }

}
