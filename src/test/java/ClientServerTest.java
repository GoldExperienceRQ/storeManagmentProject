import org.example.*;

import org.example.sockets.*;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServerTest {
    @Test
    void testCorrectResponse() throws IOException {
        StoreClientTCP client1 = new StoreClientTCP();
        client1.startConnection("127.0.0.1", 6666);
        byte[] response = client1.sendMessage(new Message(1,1,"Hrechka"));
        Message m = Decryptor.decryptMessage(response);
        assertEquals("Response to SUBTRACT_PRODUCT Hrechka: ok", m.getText());
    }

}