package develop.toolbar;

import develop.toolbar.properties.ToolbarProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        context.start();
    }

    @Bean
    public ToolbarProperties toolbarProperties() {
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "toolbar.yml")) {
            return new Yaml().loadAs(fis, ToolbarProperties.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
