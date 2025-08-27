package io.github.jeffmmartins.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//Bean, no configuration não preciso usar o private nos atributos
//não é otimizado usar em produção , pois pois pode quebrar ficar lento.
@Configuration
public class DataBaseConfiguration {

    //vindo fo yml
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    //não é otimizado usar em produção , pois pois pode quebrar ficar lento.
    public DataSource datasource(){
        //implementação de datasource
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    //correto
    @Bean
    public DataSource hikariDataSource(){
        //configuração basica
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        //Liberando conexões, usuários acessando maximo.
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(1); //tamanho inicial de pool
        config.setPoolName("Library-db-pool");
        config.setMaxLifetime(600000); //conexão no maximo 10 minutos logo apos morre
        config.setConnectionTimeout(100000);//timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); //query de teste para ver se ta conectando com o banco.

        return new HikariDataSource(config);
    }

}
