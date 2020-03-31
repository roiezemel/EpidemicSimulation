package algorithm;

import java.util.HashMap;

public class Person {

    private Zone1 zone1;
    private Zone2 zone2;
    private HashMap<String, Double> factors;

    /**
     * Initialize lonely person.
     * @param factors Table of factors.
     */
    public Person(HashMap<String, Double> factors) {
        this.factors = factors;
    }

    /**
     * Initialize person with factors and zones.
     * @param factors Table of factors.
     * @param zone1
     * @param zone2
     */
    public Person(HashMap<String, Double> factors, Zone1 zone1, Zone2 zone2) {
        this.factors = factors;
        this.zone1 = zone1;
        this.zone2 = zone2;
    }

    /**
     * @return zone1.
     */
    public Zone1 getZone1() {
        return zone1;
    }

    /**
     * @return zone2.
     */
    public Zone2 getZone2() {
        return zone2;
    }

    /**
     * @return table of factors.
     */
    public HashMap<String, Double> getFactors() {
        return factors;
    }

}
