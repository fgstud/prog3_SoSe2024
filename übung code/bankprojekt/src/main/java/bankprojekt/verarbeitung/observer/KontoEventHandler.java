package bankprojekt.verarbeitung.observer;

import java.util.HashMap;
import java.util.Map;

public class KontoEventHandler {
    Map<Long, KontoEvent> kontoEvents = new HashMap<Long, KontoEvent>();

    public void subscribe(long kontoNr, KontoEvent kontoEvent) {
        kontoEvents.put(kontoNr, kontoEvent);
    }

    public void unsubscribe(long kontoNr) {
        kontoEvents.remove(kontoNr);
    }

    public void update(long kontoNr, String event) {
        kontoEvents.get(kontoNr).update(event);
    }
}
