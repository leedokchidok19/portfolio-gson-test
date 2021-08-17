package com.io.leedokchidok.test.database;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;


public class DBCon {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//static

	@Test
	public void testConnection() {
		try (Connection con =
				DriverManager.getConnection(//mysql 연결 : DB_Schema/DB_username/DB_password
						"JDBC:MYSQL://127.0.0.1:3306/DB_Schema?serverTimezone=UTC&useSSL=false",
						"DB_username",
						"DB_password")){
			System.out.println("DB connection"+con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}//testConnection

}//DBCon
