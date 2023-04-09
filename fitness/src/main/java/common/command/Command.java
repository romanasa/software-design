package common.command;

public interface Command {
    String process(CommandDao commandDao) throws Exception;
}
