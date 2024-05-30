import org.example.Sender;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

public class SenderTest {
    @Test
    public void testIfMessageIsNull() {
        byte[] message = null;
        assertThrows(IllegalArgumentException.class, () -> Sender.sendMessage(message, InetAddress.getLocalHost()));
    }
    @Test
    public void testIfAddressIsNull(){
        byte[] message = "Hello".getBytes();
        assertThrows(IllegalArgumentException.class, () -> Sender.sendMessage(message, null));
    }
}
