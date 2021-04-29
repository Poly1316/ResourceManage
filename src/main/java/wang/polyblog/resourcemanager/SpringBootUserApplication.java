package wang.polyblog.resourcemanager;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by User on 2020/7/6.
 */

@SpringBootApplication()
@MapperScan("wang.polyblog.resourcemanager.dao")
@EnableScheduling
public class SpringBootUserApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootUserApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootUserApplication.class,args);
    }
}
