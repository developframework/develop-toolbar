package develop.toolbar;

import develop.toolbar.command.Command;
import develop.toolbar.command.SearchCommand;
import develop.toolkit.base.struct.TwoValues;
import develop.toolkit.base.utils.CollectionAdvice;
import develop.toolkit.base.utils.StringAdvice;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {

    private List<Command> commands = new ArrayList<>();

    public CommandRegistry() {
        this.commands.add(new SearchCommand());
    }

    public void executeCommand(String commandStr) {
        CollectionAdvice
                .getFirstTrue(commands, c -> commandStr.startsWith(c.keyword()))
                .ifPresent(c -> {
                    TwoValues<String, String> twoValues = StringAdvice.cutOff(commandStr, commandStr.indexOf(" "));
                    try {
                        c.execute(twoValues.getSecondValue().trim());
                    } catch (CommandParseFailedException e) {
                        e.printStackTrace();
                    }
                });
    }
}
