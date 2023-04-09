package manager.query.queries;

import common.query.Query;
import common.query.QueryDao;
import manager.query.QueryDaoImpl;

public class GetMembershipInfoQuery implements Query {
    private final Long uid;

    public GetMembershipInfoQuery(Long uid) {
        this.uid = uid;
    }

    @Override
    public String process(QueryDao queryDao) throws Exception {
        return ((QueryDaoImpl) queryDao).getSubscriptionInfo(uid);
    }
}
