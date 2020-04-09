package algorithm;

@FunctionalInterface
public interface Count {

    /**
     * Apply some sort of count operation on a person.
     * @param person
     * @return
     */
    int count(Person person);

}
