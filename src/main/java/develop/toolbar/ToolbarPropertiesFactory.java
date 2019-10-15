package develop.toolbar;

import develop.toolbar.properties.ToolbarProperties;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class ToolbarPropertiesFactory {

    @Getter
    private ToolbarProperties toolbarProperties;

    public ToolbarPropertiesFactory() {
        load();
    }

    public void load() {
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "toolbar.yml")) {
            toolbarProperties = new Yaml().loadAs(fis, ToolbarProperties.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
