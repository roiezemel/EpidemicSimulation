package algorithm;

import java.util.List;
import java.util.Random;

public class Zone2 extends Zone {

    /**
     * Initialize zone2.
     * @param capacity The desired size of the zone.
     */
    public Zone2(int capacity) {
        setCapacity(capacity);
    }

    /**
     * Finds random partners for zone2.
     * Each partner that is chosen is also updated with the main person as one of it's partners at zone2.
     * @param person The main person.
     * @param unfilledPopulation The part of the population that hasn't been filled yet. All instances in this list should already have zone2 initialized.
     * @param rand Random object. This is only important to reduce runtime.
     */
    public static void fillGroup(Person person, List<Person> unfilledPopulation, Random rand) {
        // Pick random partners?
        boolean random = true;

        int size = person.getZone2().getCapacity() - person.getZone2().getSize();

        // If no available people, decrease capacity
        if (size >= unfilledPopulation.size()) {
            size = unfilledPopulation.size() - 1; // 2
            random = false;
        }

        for (int i = 0; i < size; i++) { // 0, 1

            Person partner;

            if (random) {
                // Find random partner
                partner = unfilledPopulation.get(rand.nextInt(unfilledPopulation.size()));
                while (partner == person || person.getZone2().inZone(partner))
                    partner = unfilledPopulation.get(rand.nextInt(unfilledPopulation.size()));
            }
            else {
                // Add all remaining partners, except for person
                if (unfilledPopulation.get(i) == person)
                    i++;
                partner = unfilledPopulation.get(i);
            }

            // Add partner to group, update partner's group
            person.getZone2().getGroup().add(partner);
            partner.getZone2().getGroup().add(person);

            // Remove partner if filled
            if (partner.getZone2().getSize() == partner.getZone2().getCapacity()) {
                unfilledPopulation.remove(partner);
                if (!random) {
                    size--;
                    i--;
                }
            }

        }
        unfilledPopulation.remove(person);
    }

    @Override
    protected String getKey() {
        return "zone2";
    }

}
