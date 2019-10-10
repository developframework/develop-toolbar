package develop.toolbar;

import develop.toolbar.command.Command;
import develop.toolbar.command.SearchCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {

    private List<Command> commands = new ArrayList<>();

    public CommandRegistry() {
        this.commands.add(new SearchCommand());
    }

    public boolean executeCommand(String commandStr) {
        Command matchCommand = null;
        for (Command command : commands) {
            if (commandStr.startsWith(command.keyword())) {
                matchCommand = command;
                break;
            }
        }
        if (matchCommand != null) {
            try {
                matchCommand.execute(commandStr.substring(commandStr.indexOf(" ")).trim());
                return true;
            } catch (CommandParseFailedException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
