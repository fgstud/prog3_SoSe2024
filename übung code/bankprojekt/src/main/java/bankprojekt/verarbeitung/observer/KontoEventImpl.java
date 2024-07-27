package bankprojekt.verarbeitung.observer;

public class KontoEventImpl implements KontoEvent{
    @Override
    public void update(String event) {
        System.out.println(event);
    }
}
