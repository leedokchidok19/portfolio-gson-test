package com.io.leedokchidok.test.database;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

/*
 * DB Connection(연결) 후 Table Update문 실행
 * doUpdate 함수의 updateSql:String 변수에
 * 생성할 Table 정보 입력 후 JUnit 실행
 * DB_Schema : DB 스키마
 * DB_username : 유저명
 * DB_password : 비밀번호
 * */
public class DBUpdate {

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
			int executeSql = doUpdate(con);
			System.out.println("총 "+executeSql+"행 실행했습니다.");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}//testConnection

	private int doUpdate(Connection con) {

		//UPDATE 테이블명(컬럼) SET 컬러명=값 WHERE 조건;
		final String updateSql	= "UPDATE Test "//Table 명 뒤 꼭 공백이 있어야 한다
								+ "SET Name=?,"//Name은 예약어로 SQL tool 사용 시 주의
								+ "InputDay=? "//WHERE 앞  혹은 컬럼 마지막 뒤 공백 포함해야 한다
								+ "WHERE Idx=?";//공백이 없을 경우 마지막 컬럼으로 인식 실행문 문법 에러 발생
		try {
			PreparedStatement pstmt = con.prepareStatement(updateSql);
			pstmt.setString(1, "doUpdate");//Name
			pstmt.setString(2, getToday());//InputDay
			pstmt.setInt(3, 1);//Idx
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			return 0;
		}

	}//doUpdate

	//오늘 날짜 
	private String getToday() {
		//yyyy-MM-dd HH:mm:ss 중 MM, HH 대문자
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String getToday = sdf.format(calendar.getTime());
		return getToday;
	}//getToday

}//DBUpdate
