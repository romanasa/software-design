package report.query.queries;

import common.query.Query;
import common.query.QueryDao;
import report.query.QueryDaoImpl;

import java.time.LocalDate;

public class GetStatsQuery implements Query {
    LocalDate from;
    LocalDate to;

    public GetStatsQuery(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String process(QueryDao queryDao) throws Exception {
        return ((QueryDaoImpl) queryDao).getReport(from, to);
    }
}
