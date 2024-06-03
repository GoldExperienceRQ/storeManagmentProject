import org.junit.jupiter.api.Test;
import org.sockets.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GreetServerTest {
    @Test
    public void testCorrectGreeting() throws IOException {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }

    @Test
    public void testUncorrectGreeting() throws IOException {
        GreetClient client2 = new GreetClient();
        client2.startConnection("127.0.0.1", 6666);
        String response = client2.sendMessage("hello");
        assertEquals("unrecognised greeting", response);
    }
}
