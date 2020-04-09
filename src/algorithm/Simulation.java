package algorithm;

public class Simulation implements Analysis{

    private Population[] populations;
    private int countInfections;
    private int countDead;
    private int zone1Meetings;
    private int zone2Meetings;
    private int zone3Meetings;

    /**
     * Initialize simulation with number of populations.
     * @param populations
     */
    public Simulation(Population... populations) {
        this.populations = populations;
        this.countInfections = 0;
        this.countDead = 0;
        this.zone1Meetings = 0;
        this.zone2Meetings = 0;
        this.zone3Meetings = 0;

        // Set analysis
        for (Population p : populations) {
            p.setAnalysis(this);
        }
    }

    /**
     * Schedule meetings for all populations.
     */
    public void schedule() {
        for (Population p : populations) {
            p.clearMeetings();
            p.scheduleMeetings();
        }
    }

    /**
     * Evaluate a single day for each population.
     */
    public void eval() {
        for (Population p : populations) {
            p.checkInfections();
            p.updateStates();
        }
    }

    /**
     * @param state state of the people to count.
     * @return number of people with the specified state.
     */
    public int count(State state) {
        int count = 0;
        for (Population p : populations) {
            for (int i = 0; i < p.size(); i++) {
                if (p.get(i).getState() == state)
                    count++;
            }
        }
        return count;
    }

    /**
     * Count some metric from all the people.
     * @param countFunction counting function.
     * @return result of the count.
     */
    public int count(Count countFunction) {
        int count = 0;
        for (Population p : populations)
            for (int i = 0; i < p.size(); i++)
                count += countFunction.count(p.get(i));
        return count;
    }

    /**
     * Calculate the average of some metric for all the people.
     * @param countFunction count function.
     * @return the result of the average.
     */
    public double avg(Count countFunction) {
        double sum = 0;
        for (Population p : populations)
            for (int i = 0; i < p.size(); i++)
                sum += countFunction.count(p.get(i));
        return sum / getNumPeople();
    }

    /**
     * @return total number of people in the population.
     */
    public int getNumPeople() {
        int sum = 0;
        for (Population p : populations)
            sum += p.size();
        return sum;
    }

    /**
     * @return number of people who got infected so far.
     */
    public int getCountInfections() {
        return countInfections;
    }

    /**
     * @return number people who died so far.
     */
    public int getCountDead() {
        return countDead;
    }

    /**
     * @return number of meetings with zone1
     */
    public int getZone1Meetings() {
        return zone1Meetings;
    }

    /**
     * @return number of meetings with zone2
     */
    public int getZone2Meetings() {
        return zone2Meetings;
    }

    /**
     * @return number of meetings with zone3
     */
    public int getZone3Meetings() {
        return zone3Meetings;
    }

    @Override
    public void infection(Person infecting, Person infected) {
        infecting.incInfections();
        countInfections++;
    }

    @Override
    public void dead(Person deadPerson) {
        countDead++;
    }

    @Override
    public void meeting(Zone zone) {
        if (zone instanceof Zone1)
            zone1Meetings++;

        else if (zone instanceof Zone2)
            zone2Meetings++;

        else if (zone instanceof Zone3)
            zone3Meetings++;
    }
}