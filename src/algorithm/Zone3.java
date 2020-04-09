package algorithm;

public class Zone3 extends Zone {

    Population population;

    /**
     * Initialize zone3.
     * @param population
     */
    public Zone3(Population population) {
        this.population = population;
    }

    @Override
    public Person pickPartner() {
        return population.pickRandomPerson();
    }

    @Override
    protected String getKey() {
        return "zone3";
    }

}
