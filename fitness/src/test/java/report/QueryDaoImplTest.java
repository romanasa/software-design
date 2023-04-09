package report;

import com.mockrunner.mock.jdbc.MockResultSet;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import report.query.QueryDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

public class QueryDaoImplTest {

    @Test
    public void testGetSubscriptionInfo() throws Exception {
        Connection connection = PowerMockito.mock(Connection.class);

        PreparedStatement st = PowerMockito.mock(PreparedStatement.class);
        MockResultSet rs = new MockResultSet("rs");
        rs.addRow(new Object[]{"enter", 1L, Timestamp.valueOf("2023-03-28 10:10:00")});
        rs.addRow(new Object[]{"exit", 1L, Timestamp.valueOf("2023-03-28 12:10:00")});

        when(connection.prepareStatement(Mockito.startsWith("SELECT"))).thenReturn(st);

        when(st.executeQuery()).thenReturn(rs);

        QueryDaoImpl queryDao = new QueryDaoImpl(connection);
        String result = queryDao.getReport(LocalDate.parse("2023-03-28"), LocalDate.parse("2023-04-01"));

        assertEquals(result, """
                2023-03-28
                  number of visits: 1
                  average duration: 120
                """);
    }

    @Test
    public void testGetSubscriptionInfo_LastTsUpdated() throws Exception {
        Connection connection = PowerMockito.mock(Connection.class);

        PreparedStatement st = PowerMockito.mock(PreparedStatement.class);
        MockResultSet rs1 = new MockResultSet("rs1");
        rs1.addRow(new Object[]{"enter", 1L, Timestamp.valueOf("2023-03-28 10:10:00")});
        rs1.addRow(new Object[]{"exit", 1L, Timestamp.valueOf("2023-03-28 12:10:00")});
        rs1.addRow(new Object[]{"enter", 1L, Timestamp.valueOf("2023-03-28 11:10:00")});

        when(connection.prepareStatement(Mockito.startsWith("SELECT"))).thenReturn(st);

        MockResultSet rs2 = new MockResultSet("rs2");
        rs2.addRow(new Object[]{"enter", 1L, Timestamp.valueOf("2023-03-28 11:10:00")});
        rs2.addRow(new Object[]{"exit", 1L, Timestamp.valueOf("2023-03-28 15:10:00")});

        when(st.executeQuery()).thenAnswer(new Answer() {
            private int count = 0;

            public Object answer(InvocationOnMock invocation) {
                if (++count == 1) {
                    return rs1;
                }
                return rs2;
            }
        });

        QueryDaoImpl queryDao = new QueryDaoImpl(connection);
        String result = queryDao.getReport(LocalDate.parse("2023-03-28"), LocalDate.parse("2023-04-01"));

        assertEquals("""
                2023-03-28
                  number of visits: 2
                  average duration: 180
                """, result);
    }

    @Test
    public void testGetSubscriptionInfo_noReportDay() throws Exception {
        Connection connection = PowerMockito.mock(Connection.class);

        PreparedStatement st = PowerMockito.mock(PreparedStatement.class);
        MockResultSet rs = new MockResultSet("rs");
        rs.addRow(new Object[]{"enter", 1L, Timestamp.valueOf("2023-03-28 10:10:00")});
        rs.addRow(new Object[]{"exit", 1L, Timestamp.valueOf("2023-03-28 12:10:00")});
        rs.addRow(new Object[]{"enter", 1L, Timestamp.valueOf("2023-04-02 10:10:00")});
        rs.addRow(new Object[]{"exit", 1L, Timestamp.valueOf("2023-04-02 12:10:00")});

        when(connection.prepareStatement(Mockito.startsWith("SELECT"))).thenReturn(st);

        when(st.executeQuery()).thenReturn(rs);

        QueryDaoImpl queryDao = new QueryDaoImpl(connection);
        String result = queryDao.getReport(LocalDate.parse("2023-03-28"), LocalDate.parse("2023-04-02"));

        assertEquals(result, """
                2023-03-28
                  number of visits: 1
                  average duration: 120
                2023-04-02
                  number of visits: 1
                  average duration: 120
                """);
    }
}
