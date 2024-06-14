

import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.mockito.Mockito.*;

public class MySqlTest {

    @Mock
    Connection con;

    @Mock
    Statement stmt;

    @Mock
    ResultSet rs;

    @InjectMocks
    MySql mySql;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRead() throws Exception {
        Message message = new Message.Builder(1, 2, "Read")
                .product_id(6)
                .build();

        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getString(anyString())).thenReturn("test");

        mySql.read(message);

        verify(stmt, times(1)).executeQuery(anyString());
        verify(rs, times(1)).next();
        verify(rs, times(4)).getString(anyString());
    }
}