package io.choerodon.liquibase;

import io.choerodon.liquibase.LiquibaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Created by xausky on 4/6/17.
 */
@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@Import({LiquibaseConfig.class})
public class LiquibaseTools {


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LiquibaseTools.class);
        app.run(args);
    }
}
