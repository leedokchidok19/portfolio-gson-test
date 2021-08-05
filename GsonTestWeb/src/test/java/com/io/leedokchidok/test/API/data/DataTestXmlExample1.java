package com.io.leedokchidok.test.API.data;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class DataTestXmlExample1 {

	@Test
	public void TestXmlDataUrlBuilder() throws Exception  {

		String dataUrl = "경로";
		final String SERVICE_KEY = "서비스키";
//		String parm = null;

		StringBuilder urlBuilder = new StringBuilder(dataUrl); /*URL*/
		urlBuilder.append("?"	+	URLEncoder.encode("ServiceKey",	"UTF-8")	+	"="	+	SERVICE_KEY); /*Service Key*/
//		urlBuilder.append("&"	+	URLEncoder.encode("파라미터명",	"UTF-8")	+	"="	+	URLEncoder.encode(parm,	"UTF-8")); /*파라미터명*/
		//IOException
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;

		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());//json.toSting 데이터

		//ParserConfigurationException, SAXException
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.parse(new InputSource(new StringReader(sb.toString())));

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		String[] rmList = {
				  "citycode"	//찾을 파라미터 1
				, "cityname"	//찾을 파라미터 2
				};

		for(String rm : rmList){

			// XPath를 컴파일
			XPathExpression expr = xpath.compile("//"+rm);
			// XPath에서 XML 문서를 검색
			Object result = expr.evaluate(document, XPathConstants.NODESET);
			//찾을 노드가 
			NodeList nodes = (NodeList)result;

			for(int i = 0; i < nodes.getLength(); i++){
				Element element = (Element)nodes.item(i);
				String key = element.getNodeName();
				String value = element.getFirstChild().getNodeValue();
				System.out.println("key: "+key+" value: "+value);
			}//for

		}//for - rmList

	}//TestXmlData

}//DateTestXmlExample