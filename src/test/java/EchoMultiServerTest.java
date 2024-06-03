import org.junit.jupiter.api.Test;
import org.sockets.EchoMultiServer;
import org.sockets.GreetClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class EchoMultiServerTest {
    @Test
    void clientOneWorks() throws IOException {

        GreetClient client1 = new GreetClient();
        client1.startConnection("127.1.1.0", 6666);
        String m1 = client1.sendMessage("I am client one");
        String m2 = client1.sendMessage(".");

        assertEquals("I am client one", m1);
        assertEquals("tschuss", m2);
    }
    @Test
    void clientTwoWorks() throws IOException {

        GreetClient client2 = new GreetClient();
        client2.startConnection("127.1.1.0", 6666);
        String m1 = client2.sendMessage("I am client two");
        String m2 = client2.sendMessage(".");

        assertEquals("I am client two", m1);
        assertEquals("tschuss", m2);
    }
}
