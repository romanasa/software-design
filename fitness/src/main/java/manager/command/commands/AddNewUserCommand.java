package manager.command.commands;

import common.command.Command;
import common.command.CommandDao;
import manager.command.CommandDaoImpl;

public class AddNewUserCommand implements Command {
    @Override
    public String process(CommandDao commandDao) throws Exception {
        return ((CommandDaoImpl) commandDao).addNewUser();
    }
}
