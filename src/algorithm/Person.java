package algorithm;

import java.util.HashMap;

public class Person {

    public static final int NUM_SLOTS = 96; // 1440 / 15. Number of 15-minute slots in one day (24 hours).
    private Person[] meetings;
    private Zone1 zone1;
    private Zone2 zone2;
    private Zone3 zone3;
    private Analysis analysis;
    private HashMap<String, Object> factors;
    private State state = State.HEALTHY;
    private int countDays;
    private int countTarget;
    private boolean dying = false;
    private int infections;

    /**
     * Initialize lonely person.
     * @param factors Table of factors.
     */
    public Person(HashMap<String, Object> factors) {
        this.factors = factors;
        this.meetings = new Person[NUM_SLOTS];
        this.countDays = 0;
        this.countTarget = 0;
        this.infections = 0;
    }

    /**
     * Initialize person with factors and zones.
     * @param factors Table of factors.
     * @param zone1
     * @param zone2
     */
    public Person(HashMap<String, Object> factors, Zone1 zone1, Zone2 zone2) {
        this.factors = factors;
        this.zone1 = zone1;
        this.zone2 = zone2;
        this.meetings = new Person[NUM_SLOTS];
        this.countDays = 0;
        this.countTarget = 0;
        this.infections = 0;
    }

    /**
     * Should be called at the end of each day.
     * This function decides if the person should move to another state.
     * It also increases the day count, which is why it should be called every day.
     * @return true if the person died during the process, false if not.
     */
    public boolean updateState() {
        // If finished predetermined period of time and a carrier:
        if (countDays == countTarget && state == State.CARRIER) {
            dying = Math.random() < (double) factors.get("severity"); // Decide if the person is destined to die
            state = State.SICK; // Move to next state
            countDays = 0;
            countTarget = (int) Util.poisson((double) factors.get(dying ? "dTimeAvg" : "iTimeAvg")); // Set target
        }

        // If finished predetermined period of time and sick:
        if (countDays == countTarget && state == State.SICK) {
            if (dying) {// If destined to die
                if (analysis != null)
                    analysis.dead(this);
                return true;
            }
            state = State.IMMUNE; // Move to next state
        }

        if (state == State.CARRIER || state == State.SICK) // Increase count only if person is not healthy nor immune
            countDays++; // Increase day count
        return false;
    }

    /**
     * Check if the person has been infected from another person at a given time slot.
     * @param timeSlot time slot
     * @return true if the person was infected, false if not.
     */
    public boolean checkInfection(int timeSlot) {
        if (state == State.HEALTHY && meetings[timeSlot] != null) {
            if (meetings[timeSlot].getState() == State.CARRIER)
                return checkInfection("infection C", meetings[timeSlot]);
            if (meetings[timeSlot].getState() == State.SICK)
                return checkInfection("infection S", meetings[timeSlot]);
        }
        return false;
    }

    /**
     * Check if the person has been infected from another person according to the specified factor key.
     * @param factorKey
     * @return true if the person was infected, false if not.
     */
    private boolean checkInfection(String factorKey, Person other) {
        if (Math.random() < (double) factors.get(factorKey)) {
            state = State.CARRIER;
            countDays = 0;
            countTarget = (int) Util.poisson((double) factors.get("cTimeAvg"));
            if (analysis != null)
                analysis.infection(other, this);
            return true;
        }
        return false;
    }

    /**
     * Try to set a meeting at a given time slot.
     * This function goes through all zones and tries to set a meeting with other people. There is also a chance
     * that no meeting will be set.
     * @param timeSlot index of the current time slot.
     * @return true if a meeting successfully set, false otherwise.
     */
    public boolean setMeeting(int timeSlot) {
        return meetings[timeSlot] == null && (tryMeeting(timeSlot, zone1)
                || tryMeeting(timeSlot, zone2) || tryMeeting(timeSlot, zone3));
    }

    /**
     * Tries to set a meeting with a person from a specific zone.
     * @param timeSlot index of the current time slot.
     * @param zone the zone from which the partner will be picked.
     * @return true if a meeting successfully set, false otherwise.
     */
    private boolean tryMeeting(int timeSlot, Zone zone) {
        if (Math.random() < (double) factors.get(zone.getChanceKey())) {
            // Pick random partner:
            Person partner = zone.pickPartner();
            if (partner == null) // If zone is empty
                return false;
            // If partner is not already scheduled:
            if (partner.getMeetings()[timeSlot] == null) {
                // Meeting length:
                int length =  (int) factors.get(zone.getTimeKey());
                // Make sure length doesn't go over the size of the meetings chart:
                if (timeSlot + length - 1 >= meetings.length)
                    length = meetings.length - timeSlot;
                // Set the meeting:
                for (int i = 0; i < length; i++) {
                    meetings[timeSlot + i] = partner;
                    partner.getMeetings()[timeSlot + i] = this;
                }
                analysis.meeting(zone);
                return true; // Meeting set successfully
            }
        }
        // Two possibilities for getting here: the random toss didn't pass the threshold,
        // or the partner has already been scheduled for this time slot.
        return false;
    }

    /**
     * Remove a person from all zones.
     * @param person
     */
    public void remove(Person person) {
        if (zone1.inZone(person))
            zone1.getGroup().remove(person);
        if (zone2.inZone(person))
            zone2.getGroup().remove(person);
    }

    /**
     * Clear all meetings.
     */
    public void clearMeetings() {
        meetings = new Person[NUM_SLOTS];
    }

    /**
     * Increase number of infections the person made.
     */
    public void incInfections() {
        infections++;
    }

    /**
     * Set the analysis instance.
     * @param analysis
     */
    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
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
     * @return zone3.
     */
    public Zone3 getZone3() {
        return zone3;
    }

    /**
     * @return table of factors.
     */
    public HashMap<String, Object> getFactors() {
        return factors;
    }

    /**
     * Get the meetings charts.
     * @return
     */
    public Person[] getMeetings() {
        return meetings;
    }

    /**
     * Get the state of the person.
     * @return
     */
    public State getState() {
        return state;
    }

    /**
     * @return number of infections that the person made.
     */
    public int getInfections() {
        return infections;
    }

    /**
     * @param zone1
     */
    public void setZone1(Zone1 zone1) {
        this.zone1 = zone1;
    }

    /**
     * @param zone2
     */
    public void setZone2(Zone2 zone2) {
        this.zone2 = zone2;
    }

    /**
     * @param zone3
     */
    public void setZone3(Zone3 zone3) {
        this.zone3 = zone3;
    }

    /**
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Decide if the person is destined to die.
     */
    public void setDying() {
        dying = Math.random() < (double) factors.get("severity");
    }

}
