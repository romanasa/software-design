package manager.command;

import common.command.CommandDao;

import java.sql.*;

public class CommandDaoImpl implements CommandDao {
    private final Connection connection;

    public CommandDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public String addNewUser() throws Exception {
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO users DEFAULT VALUES",
                        Statement.RETURN_GENERATED_KEYS);

        int affectedRows = preparedStatement.executeUpdate();

        long uid = -1;
        if (affectedRows > 0) {
            // get user id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                uid = rs.getLong(1);
            }
            if (uid != -1) {
                return "New uid is " + uid;
            }
        }

        // rollback the transaction if the insert failed
        connection.rollback();
        return "Error while inserting new user";
    }


    public String extendSubscription(Long uid, Date expiryDate) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO membership_events(user_id, expiry_date) VALUES(?, ?) ");
        preparedStatement.setLong(1, uid);
        preparedStatement.setDate(2, expiryDate);

        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows > 0) {
            return "Subscription was successfully extended";
        }
        // rollback the transaction if the insert failed
        connection.rollback();
        return "Error while extending subscription";
    }
}
