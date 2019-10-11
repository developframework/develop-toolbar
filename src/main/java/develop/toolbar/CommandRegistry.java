package develop.toolbar;

import develop.toolbar.command.Command;
import develop.toolbar.command.HttpCommand;
import develop.toolbar.command.OpenCommand;
import develop.toolbar.command.SearchCommand;
import develop.toolbar.properties.CommandProperties;
import develop.toolbar.properties.ToolbarProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandRegistry {

    private List<Command> commands = new ArrayList<>();

    public CommandRegistry(ToolbarProperties toolbarProperties) {
        final CommandProperties commandProperties = toolbarProperties.getCommands();
        this.commands.add(new SearchCommand(commandProperties));
        this.commands.add(new HttpCommand(commandProperties));
        this.commands.add(new OpenCommand(commandProperties));
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
