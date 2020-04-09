package algorithm;

import java.util.ArrayList;
import java.util.Random;

public abstract class Zone {

    protected ArrayList<Person> group;
    private int capacity;

    /**
     * Initialize zone.
     */
    public Zone() {
        this.group = new ArrayList<>();
    }

    /**
     * Get the key name of the zone.
     * @return key name.
     */
    protected abstract String getKey();

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
        if (group.isEmpty())
            return null;
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
     * Get the key name of the time factor.
     * @return key + ' time'
     */
    public String getTimeKey() {
        return getKey() + " time";
    }

    /**
     * Get the key name of the chance factor.
     * @return key
     */
    public String getChanceKey() {
        return getKey();
    }

    /**
     * Checks if a person is already inside the zone.
     * @param person
     * @return true if the person is found, false otherwise.
     */
    public boolean inZone(Person person) {
        return group.contains(person);
    }

    /**
     * Get the capacity of the zone.
     * @return
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Set capacity of the zone.
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
