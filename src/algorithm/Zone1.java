package algorithm;

public class Zone1 extends Zone {

    /**
     * Create a zone1 for a given person.
     * @param person The main person of the group.
     * @param group The zone1 group, containing the main person.
     */
    public Zone1(Person person, Person[] group) {
        for (Person p : group) {
            if (p != person) {
                this.group.add(p);
            }
        }
        this.full = true;
    }
}
