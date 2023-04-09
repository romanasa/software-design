package report.query;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Report {
    private final Map<LocalDate, DayReport> days;
    long lastTsReport;

    public Report() {
        days = new HashMap<>();
        lastTsReport = 0;
    }

    public long getLastTsReport() {
        return lastTsReport;
    }

    public void setLastTsReport(long lastTsReport) {
        this.lastTsReport = lastTsReport;
    }

    public void addVisit(LocalDate localDate, long minutes) {
        days.putIfAbsent(localDate, new DayReport());

        DayReport dayReport = days.get(localDate);
        Long times = dayReport.getTimes();
        dayReport.setAverageDuration((times * dayReport.getAverageDuration() + minutes) / (times + 1));
        dayReport.setTimes(times + 1);
    }

    public String toString(LocalDate from, LocalDate to) {
        LocalDate date = from;
        LocalDate until = to.plusDays(1);
        StringBuilder sb = new StringBuilder();

        while (!date.equals(until)) {
            DayReport dayReport = days.getOrDefault(date, null);
            if (dayReport != null) {
                sb.append(date);
                sb.append("\n");

                sb.append("  number of visits: ");
                sb.append(dayReport.getTimes());
                sb.append("\n");

                sb.append("  average duration: ");
                sb.append(dayReport.getAverageDuration());
                sb.append("\n");
            }
            date = date.plusDays(1);
        }
        return sb.toString();
    }



    public static class DayReport {
        Long times = 0L;
        Long averageDuration = 0L;

        public Long getTimes() {
            return times;
        }

        public void setTimes(Long times) {
            this.times = times;
        }

        public Long getAverageDuration() {
            return averageDuration;
        }

        public void setAverageDuration(Long averageDuration) {
            this.averageDuration = averageDuration;
        }
    }
}
