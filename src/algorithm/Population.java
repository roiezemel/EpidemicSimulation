package algorithm;

import java.util.*;

public class Population {

    private Person[] population;

    /**
     * Initializes a population with zone1 and zone2.
     * @param size
     */
    public Population(int size, int sickPeople) {
        population = new Person[size];
        int count = 0;

        // Random group size
        HashMap<String, Object> factors = Factors.getRandomFactorSet();
        int groupSize = ((Range)factors.get("zone1 amount")).random();
        Person[] group = new Person[groupSize];

        // Initialize population with zone1
        for (int i = 0; i < size; i++) {
            population[i] = new Person(factors);
            population[i].setZone3(new Zone3(this));
            group[count] = population[i];
            count++;
            if (count == groupSize) {
                for (int j = i - count + 1; j <= i; j++) {
                    population[j].setZone1(new Zone1(population[j], group));
                }
                count = 0;
                factors = Factors.getRandomFactorSet();
                groupSize = ((Range)factors.get("zone1 amount")).random();
                if (i + groupSize >= size)
                    groupSize = size - i - 1;
                group = new Person[groupSize];
            }
        }

        // Set zone2
        // Set capacities:
        for (int i = 0; i < population.length; i++) {
            population[i].setZone2(new Zone2(((Range)population[i].getFactors().get("zone2 amount")).random()));
        }

        // Set groups:
        Random rand = new Random();
        shuffle(rand);
        List<Person> unfilledPopulation = new LinkedList<>(Arrays.asList(population));
        while (!unfilledPopulation.isEmpty()){
            Person person = unfilledPopulation.get(0);
            Zone2.fillGroup(person, unfilledPopulation, rand);
        }

        // Set sick people
        for (int i = 0; i < sickPeople; i++) {
            Person randPerson = pickRandomPerson();
            randPerson.setState(State.SICK);
            randPerson.setDying();
        }
    }

    /**
     * Should be called at the end of each day. This function updates the states of the whole population,
     * according to their day count.
     */
    public void updateStates() {
        for (Person p : population) {
            if (p.updateState())
                remove(p);
        }
    }

    /**
     * Check if someone got infected and update their state.
     */
    public void checkInfections() {
        for (int i = 0; i < Person.NUM_SLOTS; i++)
            for (Person p : population)
                p.checkInfection(i);
    }

    /**
     * Set meetings for the whole population.
     */
    public void scheduleMeetings() {
        for (int i = 0; i < Person.NUM_SLOTS; i++) {
            for (Person p : population) {
                p.setMeeting(i);
            }
        }
    }

    /**
     * Clear all meetings.
     */
    public void clearMeetings() {
        for (Person p : population)
            p.clearMeetings();
    }

    /**
     * Remove a person from the population
     * @param person
     */
    public void remove(Person person) {
        // Remove from population
        Person[] newPopulation = new Person[population.length - 1];
        int add = 0;
        for (int i = 0; i < newPopulation.length; i++) {
            if (population[i + add] == person)
                add = 1;
            newPopulation[i] = population[i + add];
        }
        population = newPopulation;

        // Remove from zones
        for (Person p : population)
            p.remove(person);
    }

    /**
     * Set analysis instance for each person.
     * @param analysis
     */
    public void setAnalysis(Analysis analysis) {
        for (Person p : population)
            p.setAnalysis(analysis);
    }

    /**
     * Get a group of persons from the population starting with a given index.
     * @param index Index of the first person.
     * @param size Size of the group.
     * @return An array of persons.
     */
    public Person[] group(int index, int size) {
        Person[] group = new Person[size];
        for (int i = index; i < index + size; i++) {
            group[i - index] = population[i];
        }
        return group;
    }

    private void shuffle(Random rand) {
        for (int i = 0; i < population.length; i++) {
            int randIndex = rand.nextInt(population.length);
            Person temp = population[randIndex];
            population[randIndex] = population[i];
            population[i] = temp;
        }
    }

    /**
     * Get a person from the population at a given index
     * @param index
     * @return
     */
    public Person get(int index) {
        return population[index];
    }

    /**
     * @return size of the population.
     */
    public int size() {
        return population.length;
    }

    /**
     * Picks a random person from the population.
     * @return
     */
    public Person pickRandomPerson() {
        return population[new Random().nextInt(population.length)];
    }
}
