package manager.command.commands;

import common.command.Command;
import common.command.CommandDao;
import manager.command.CommandDaoImpl;

import java.sql.Date;

public class ExtendMembershipCommand implements Command {
    private final Long uid;
    private final Date expiryDate;

    public ExtendMembershipCommand(Long uid, Date expiryDate) {
        this.uid = uid;
        this.expiryDate = expiryDate;
    }

    @Override
    public String process(CommandDao commandDao) throws Exception {
        return ((CommandDaoImpl) commandDao).extendSubscription(uid, expiryDate);
    }
}
