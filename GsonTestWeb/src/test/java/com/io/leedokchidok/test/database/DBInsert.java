package com.io.leedokchidok.test.database;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

/*
 * DB Connection(연결) 후 Table insert문 실행
 * doInsert 함수의 insertSql:String 변수에
 * 생성할 Table 정보 입력 후 JUnit 실행
 * DB_Schema : DB 스키마
 * DB_username : 유저명
 * DB_password : 비밀번호
 * */
public class DBInsert {

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
			//insert
			int executeSql = doInsert(con);
			System.out.println("총 "+executeSql+"행 실행했습니다.");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}//testConnection

	private int doInsert(Connection con) {

		//INSERT INTO 테이블명(컬럼) VALUES(값);
		final String insertSql	= "INSERT INTO Test (Idx, Name, InputDay)"
								+ "VALUES(?, ?, ?);";

		try {
			PreparedStatement pstmt = con.prepareStatement(insertSql);
			pstmt.setInt(1, 1);//Idx
			pstmt.setString(2, "test");//Name
			pstmt.setString(3, getToday());//InputDay
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			return 0;
		}

	}//doInsert

	//오늘 날짜 
	private String getToday() {
		//yyyy-MM-dd HH:mm:ss 중 MM, HH 대문자
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String getToday = sdf.format(calendar.getTime());
		return getToday;
	}//getToday

}//DBInsert
