package visit;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import visit.command.CommandDaoImpl;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

public class CommandDaoImplTest {

    @Test
    public void testEnter_success() throws Exception {
        testEnter("2022-04-01", "exit", "Successful enter");
    }

    @Test
    public void testEnter_expired() throws Exception {
        testEnter("2022-03-28", "exit", "Membership 2 has expired");
    }

    @Test
    public void testEnter_alreadyEntered() throws Exception {
        testEnter("2022-04-02", "enter", "The user 2 has already entered");
    }

    void testEnter(String expiryDate, String lastType, String verdict) throws Exception {
        Connection connection = PowerMockito.mock(Connection.class);

        PreparedStatement stExpiryDate = PowerMockito.mock(PreparedStatement.class);
        ResultSet rsExpiryDate = PowerMockito.mock(ResultSet.class);

        // Expiry Date
        when(connection.prepareStatement(Mockito.startsWith("SELECT max(expiry_date)"))).thenReturn(stExpiryDate);
        when(stExpiryDate.executeQuery()).thenReturn(rsExpiryDate);
        when(rsExpiryDate.next()).thenReturn(true);
        when(rsExpiryDate.getDate(1)).thenReturn(Date.valueOf(expiryDate));

        PreparedStatement stHasEntered = PowerMockito.mock(PreparedStatement.class);
        ResultSet rsHasEntered = PowerMockito.mock(ResultSet.class);

        // Has Entered
        when(connection.prepareStatement(Mockito.startsWith("SELECT type"))).thenReturn(stHasEntered);
        when(stHasEntered.executeQuery()).thenReturn(rsHasEntered);
        when(rsHasEntered.next()).thenReturn(true);
        when(rsHasEntered.getString(1)).thenReturn(lastType);

        PreparedStatement st = PowerMockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.startsWith("INSERT"), Mockito.anyInt())).thenReturn(st);
        when(st.executeUpdate()).thenReturn(1);

        CommandDaoImpl commandDao = new CommandDaoImpl(connection);
        String result = commandDao.enter(2L, Timestamp.valueOf("2022-03-28 10:10:10.0").getTime());

        assertEquals(result, verdict);
    }

    @Test
    public void testExit_success() throws Exception {
        testExit("enter", "Successful exit");
    }

    @Test
    public void testExit_NotEntered() throws Exception {
        testExit("exit", "The user 2 has not entered");
    }

    void testExit(String lastType, String verdict) throws Exception {
        Connection connection = PowerMockito.mock(Connection.class);

        PreparedStatement stHasEntered = PowerMockito.mock(PreparedStatement.class);
        ResultSet rsHasEntered = PowerMockito.mock(ResultSet.class);

        // Has Entered
        when(connection.prepareStatement(Mockito.startsWith("SELECT type"))).thenReturn(stHasEntered);
        when(stHasEntered.executeQuery()).thenReturn(rsHasEntered);
        when(rsHasEntered.next()).thenReturn(true);
        when(rsHasEntered.getString(1)).thenReturn(lastType);

        PreparedStatement st = PowerMockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.startsWith("INSERT"), Mockito.anyInt())).thenReturn(st);
        when(st.executeUpdate()).thenReturn(1);

        CommandDaoImpl commandDao = new CommandDaoImpl(connection);
        String result = commandDao.exit(2L, Timestamp.valueOf("2022-03-28 10:10:10.0").getTime());

        assertEquals(result, verdict);
    }
}
