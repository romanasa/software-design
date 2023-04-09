package report.query;

import common.query.QueryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class QueryDaoImpl implements QueryDao {
    private final Connection connection;
    private final Report report;

    public QueryDaoImpl(Connection connection) throws Exception {
        this.connection = connection;
        this.report = new Report();
        updateReport();
    }

    public String getReport(LocalDate from, LocalDate to) throws Exception {
        updateReport();
        return report.toString(from, to);
    }

    void updateReport() throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT type, user_id, event_time FROM visit_events WHERE event_time >= ?");
        preparedStatement.setTimestamp(1, new Timestamp(report.getLastTsReport()));
        ResultSet rs = preparedStatement.executeQuery();

        List<Record> records = getAllRecords(rs);

        SortedSet<Long> enters = new TreeSet<>();
        Map<Long, Long> id_to_ts = new HashMap<>();
        long last_ts;
        for (Record record : records) {
            Long userId = record.getUserId();
            Timestamp ts = record.getEventTime();

            if (record.getType().equals("enter")) {
                id_to_ts.put(userId, ts.getTime());
                enters.add(ts.getTime());
            } else {
                Long enter_ts = id_to_ts.getOrDefault(userId, null);
                if (enter_ts != null) {
                    LocalDateTime enter = new Timestamp(enter_ts).toLocalDateTime();
                    LocalDateTime exit = new Timestamp(ts.getTime()).toLocalDateTime();
                    if (enter.toLocalDate().equals(exit.toLocalDate())) {
                        report.addVisit(enter.toLocalDate(), enter.until(exit, ChronoUnit.MINUTES));
                    }
                    enters.remove(enter_ts);
                }
                id_to_ts.remove(userId);
            }
            last_ts = ts.getTime();
            if (!enters.isEmpty()) {
                last_ts = enters.first();
            } else {
                last_ts = last_ts + 1;
            }
            report.setLastTsReport(last_ts);
        }
    }


    public static List<Record> getAllRecords(ResultSet rs) throws Exception {
        List<Record> records = new ArrayList<>();
        while (rs.next()) {
            String type = rs.getString(1);
            Long userId = rs.getLong(2);
            Timestamp ts = rs.getTimestamp(3);

            records.add(new Record(userId, ts, type));
        }
        return records;
    }


    public static class Record {
        Long userId;
        Timestamp eventTime;
        String type;

        public Record(Long userId, Timestamp eventTime, String type) {
            this.userId = userId;
            this.eventTime = eventTime;
            this.type = type;
        }

        public Long getUserId() {
            return userId;
        }

        public Timestamp getEventTime() {
            return eventTime;
        }

        public String getType() {
            return type;
        }
    }
}
