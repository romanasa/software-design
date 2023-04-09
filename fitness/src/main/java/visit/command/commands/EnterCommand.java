package visit.command.commands;

import common.command.Command;
import common.command.CommandDao;
import visit.command.CommandDaoImpl;

public class EnterCommand implements Command {
    Long uid;
    Long timestamp;

    public EnterCommand(Long uid, Long timestamp) {
        this.uid = uid;
        this.timestamp = timestamp;
    }

    @Override
    public String process(CommandDao commandDao) throws Exception {
        return ((CommandDaoImpl) commandDao).enter(uid, timestamp);
    }
}
