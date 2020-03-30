package algorithm;

public class Zone2 extends Zone {

    public static final int MEETING_TIME = 2;

    /**
     * Finds random partners for zone2.
     * Each partner that is chosen is also updated with the main person as one of it's partners at zone2.
     * @param person The main person.
     * @param population The population.
     * @param index The index of person in the population. Important to reduce runtime.
     * @param size The size of the group.
     */
    public Zone2(Person person, Population population, int index, int size) {
        for (int i = 0; i < size; i++) {

            // Pick random partner who hasn't been filled yet
            Person partner = population.pickRandomPerson(index);
            while (partner == person || partner.getZone2().full)
                partner = population.pickRandomPerson(index);

            // Add partner to group, and update partner
            this.group.add(partner);
            partner.getZone2().group.add(person);
        }
        this.full = true;
    }

}
