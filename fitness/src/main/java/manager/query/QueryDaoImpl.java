package manager.query;

import common.query.QueryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QueryDaoImpl implements QueryDao {
    private final Connection connection;

    public QueryDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public String getSubscriptionInfo(Long uid) throws Exception {
        PreparedStatement st;
        ResultSet rs;

        st = connection.prepareStatement(
                "SELECT max(expiry_date) FROM membership_events WHERE user_id = ?");
        st.setLong(1, uid);
        rs = st.executeQuery();

        if (rs.next()) {
            return "Expiry date for uid " + uid + " is " + rs.getDate(1);
        }
        return "Error getting membership info";
    }
}
