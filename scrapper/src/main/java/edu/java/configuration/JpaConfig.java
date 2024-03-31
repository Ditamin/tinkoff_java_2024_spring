package edu.java.configuration;

import edu.java.service.jpa.JpaLinkService;
import edu.java.service.jpa.JpaTgChatService;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JpaConfig {
    @Autowired
    DataSource dataSource;

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("edu.java.model.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getProperties());
        return em;
    }

    @Bean
    Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        return properties;
    }

    @Bean
    JpaLinkService jpaLinkService() {
        return new JpaLinkService();
    }

    @Bean
    JpaTgChatService jpaTgChatService() {
        return new JpaTgChatService();
    }
}
