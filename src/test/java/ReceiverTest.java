import org.example.Receiver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiverTest {
    @Test
    public void messageNotNUll() {
        Receiver receiver = new Receiver();
        assertThrows(IllegalArgumentException.class, () -> receiver.receiveMessage(null));
    }
}
