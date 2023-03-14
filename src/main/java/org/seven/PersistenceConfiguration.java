package org.seven;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class PersistenceConfiguration {
	@Bean
	public DataSource dataSource(){
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/paytmdb?allowPublicKeyRetrieval=true");
	    dataSource.setUsername("root");
	    dataSource.setPassword("Swam2@mysql");
	    return dataSource;
	}
}
