package algorithm;

import java.util.ArrayList;
import java.util.Random;

public class Zone {

    protected ArrayList<Person> group;
    protected boolean full;

    /**
     * Initialize zone.
     */
    public Zone() {
        this.group = new ArrayList<>();
        this.full = false;
    }

    /**
     * Get zone's group.
     * @return
     */
    public ArrayList<Person> getGroup() {
        return group;
    }

    /**
     * Picks a random partner from the group.
     * @return Random person from group.
     */
    public Person pickPartner() {
        return group.get(new Random().nextInt(group.size()));
    }

    /**
     * Get the size of the group.
     * @return
     */
    public int getSize() {
        return group.size();
    }

    /**
     * Is the group full.
     * @return true if the group is full, false if not.
     */
    public boolean isFull() {
        return full;
    }

    /**
     * Checks if a person is already inside the zone.
     * @param person
     * @return true if the person is found, false otherwise.
     */
    public boolean inZone(Person person) {
        return group.contains(person);
    }

}
