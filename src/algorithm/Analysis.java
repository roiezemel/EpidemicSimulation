package algorithm;

public interface Analysis {

    void infection(Person infecting, Person infected);

    void dead(Person deadPerson);

    void meeting(Zone zone);

}
