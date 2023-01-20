package org.seven;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MySqlConfiguration {
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String userName;
	@Value("${spring.datasource.password}")
	private String password;
	
	
	@Bean(name = "mySqlConn")
	public Connection buildMySqlConnection(){
		log.debug("Created DataSource:{}");
		try(BasicDataSource ds = new BasicDataSource()){
			ds.setDriverClassName(driverClassName);
			ds.setUrl(url);
			ds.setUsername(userName);
			ds.setPassword(password);
			Connection conn = ds.getConnection();
			conn.setAutoCommit(false);
			return conn;
		}catch(SQLException sqle) {
			log.error("Error while obtaining exception:{}",sqle.getMessage());
		}		
		return null;
	}
}