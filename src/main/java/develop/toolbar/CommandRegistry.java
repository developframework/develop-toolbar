package develop.toolbar;

import develop.toolbar.command.Command;
import develop.toolbar.command.RegisterCommand;
import develop.toolbar.properties.AliasProperties;
import develop.toolbar.utils.CollectionAdvice;
import develop.toolbar.utils.StringAdvice;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CommandRegistry implements ApplicationContextAware {

    private List<Command> commands = new ArrayList<>();

    private ToolbarPropertiesFactory toolbarPropertiesFactory;

    public boolean executeCommand(String commandStr) {
        int spaceIndex = commandStr.indexOf(" ");
        String method, content = null;
        if (spaceIndex > 0) {
            String[] parts = StringAdvice.cutOff(commandStr, spaceIndex);
            method = parts[0];
            content = parts[1].trim();
        } else {
            method = commandStr;
        }

        AliasProperties matchAlias = CollectionAdvice
                .getFirstMatch(toolbarPropertiesFactory.getToolbarProperties().getCommands().getAliases(), method, AliasProperties::getAlias)
                .orElse(null);
        if (matchAlias != null) {
            executeCommand(matchAlias.getContent() + (content == null ? "" : (" " + content)));
            return true;
        }
        Command command = CollectionAdvice
                .getFirstMatch(commands, method, Command::keyword)
                .orElse(null);
        if (command != null) {
            try {
                command.execute(content);
                return true;
            } catch (CommandParseFailedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(RegisterCommand.class);
        for (Object value : beansWithAnnotation.values()) {
            if (value instanceof Command)
                commands.add((Command) value);
        }
        toolbarPropertiesFactory = applicationContext.getBean(ToolbarPropertiesFactory.class);
    }
}
