package boot;



import boot.configprops.DBProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**Класс представляет собой java-конфигурацию Spring Context а также точку входа
 * в Spring Boot приложение.
 @author Артемьев Р.А.
 @version 15.09.2019 */
@SpringBootApplication
@EnableTransactionManagement//Включаем поддержку транзакций
public class App implements WebMvcConfigurer
{
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }

    /*Без реализации этого метода не подключаются JS-скрипты и CSS-файлы
    в подпапках папки static*/
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/static/**").
                addResourceLocations("classpath:/static/");

    }

    @Bean("dbProperties")
    @Profile("default")
    @ConfigurationProperties(prefix="db")
    public DBProperties getDBProperties()
    {
        return new DBProperties();
    }

    /**Метод возвращает бин-источник данных для готового приложения*/
    @Bean("dataSource")
    @Profile("default")
    public DriverManagerDataSource getDriverManager(DBProperties dbProperties)
    {
        DriverManagerDataSource driverManager = new DriverManagerDataSource();
        driverManager.setDriverClassName(dbProperties.getDriverClassName());
        driverManager.setUrl(dbProperties.getUrl());
        driverManager.setUsername(dbProperties.getUsername());
        driverManager.setPassword(dbProperties.getPassword());
        return driverManager;
    }

    /**Метод возвращает бин-источник данных для целей тестирования*/
    @Bean("dataSource")
    @Profile("test")
    public DriverManagerDataSource getDriverManagerTest()
    {
        DriverManagerDataSource driverManager = new DriverManagerDataSource();
        driverManager.setDriverClassName("org.postgresql.Driver");
        driverManager.setUrl("jdbc:postgresql://ec2-54-246-84-100.eu-west-1.compute.amazonaws.com/d4ndial3c74jlm?sslmode=require");
        driverManager.setUsername("wurwswhmoyajtc");
        driverManager.setPassword("5acb7f7a2d16451994a9d4697b7c3f5d7ffb228bba64ad97e9bcb539fb0e121c");
        return driverManager;
    }

    /*JdbcTemplate это базовый класс, который управляет обработкой всех событий и связями с БД.
     Он выполняет SQL-запросы, выполняет итерации по ResultSet и извлекает вызываемые значения,
     обновляет инструкции и вызовы процедур, “ловит” исключения и транслирует их в исключения,
     определённые в пакете org.springframwork.dao .*/
    @Bean("jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }

}