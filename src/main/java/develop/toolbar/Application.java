package develop.toolbar;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        context.start();
    }

    @Bean
    public CommandRegistry commandRegistry() {
        return new CommandRegistry();
    }

    @Bean
    public SearchHistoryManager searchHistoryManager() {
        return new SearchHistoryManager();
    }
}
