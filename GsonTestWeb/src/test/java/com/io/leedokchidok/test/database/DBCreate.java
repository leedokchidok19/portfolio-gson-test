package com.io.leedokchidok.test.database;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.Test;

/*
 * DB Connection(연결) 후 Table Create문 실행
 * doCreate 함수의 createSql:String 변수에
 * 생성할 Table 정보 입력 후 JUnit 실행
 * DB_Schema : DB 스키마
 * DB_username : 유저명
 * DB_password : 비밀번호
 * */
public class DBCreate {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//static

	@Test
	public void testConnection() {
		try (
				Connection con =
				DriverManager.getConnection(//mysql 연결 : DB_Schema/DB_username/DB_password
						"JDBC:MYSQL://127.0.0.1:3306/DB_Schema?serverTimezone=UTC&useSSL=false",
						"DB_username",
						"DB_password")){
			System.out.println("DB connection"+con);
			//create
			doCreate(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}//testConnection

	private int doCreate(Connection con) {

		final String createSql	= "CREATE TABLE Test ("
								+ "Idx INT,"
								+ "Name VARCHAR(30),"
								+ "InputDay DATETIME"
								+");";

		try {
			PreparedStatement pstmt = con.prepareStatement(createSql);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			return 0;
		}

	}//doCreate

}//DBCreate
