package com.xie.control;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class testchat {

	@Test
	public void test1() {
		// String
		// xmldata="<cmd id='5802'><ClientType val='1'/><TerminalType val='1'/><ActivityID val='A2015111200777'/></cmd>";
		String xmldata = "<?xml version='1.0' encoding='UTF-8'?><cmd id='5802'><ClientType val='1'/><TerminalType val='1'/><ActivityID val='110'/></cmd>";
		try {
			System.out.println(xmldata);
			byte[] bb = xmldata.getBytes();
			// 请求地址
			// URL url = new URL("http://120.25.151.98?wsdl");
			URL url = new URL("http://120.25.151.98:5998");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);// 设置超时的时间
			conn.setDoInput(true);
			conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
			conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			conn.setRequestProperty("Content-Length", String.valueOf(bb.length));
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(xmldata); // 写入请求的字符串
			out.flush();
			out.close();
			// 请求返回的状态
			if (conn.getResponseCode() == 200) {
				System.out.println("yes++");
				// 请求返回的数据
				InputStream in = conn.getInputStream();
				String a = null;
				try {
					byte[] data1 = new byte[in.available()];
					in.read(data1);
					// 转成字符串
					a = new String(data1);
					System.out.println(a);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				System.out.println("no++");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		DefaultHttpClient dhc = new DefaultHttpClient();

		String url = "http://120.25.151.98:5998?<cmd id='5802'><ClientType val='1'/><TerminalType val='2'/><ActivityID val='110'/></cmd>";

		try {
			url = URLEncoder.encode(url, "utf-8");
			HttpGet get = new HttpGet(url);

			CloseableHttpResponse res = dhc.execute(get);

			String content = EntityUtils.toString(res.getEntity());

			System.out.println(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void test3() {
		String url = "http://120.25.151.98:5998";

		String xmlString = getXmlString();

		HttpClient client = new HttpClient();
		PostMethod myPost = new PostMethod(url);
		client.getParams().setSoTimeout(300 * 1000);
		String responseString = null;
		try {
			myPost.setRequestEntity(new StringRequestEntity(xmlString,
					"text/xml", "utf-8"));
			int statusCode = client.executeMethod(myPost);
			if (statusCode == HttpStatus.SC_OK) {
				BufferedInputStream bis = new BufferedInputStream(
						myPost.getResponseBodyAsStream());
				byte[] bytes = new byte[1024];
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int count = 0;
				while ((count = bis.read(bytes)) != -1) {
					bos.write(bytes, 0, count);
				}
				byte[] strByte = bos.toByteArray();
				responseString = new String(strByte, 0, strByte.length, "utf-8");
				bos.close();
				bis.close();
			}
		} catch (Exception e) {
//			log.error(e.getMessage(), e);
		}
		myPost.releaseConnection();
		client.getHttpConnectionManager().closeIdleConnections(0);
		System.out.println("responseString:" + responseString);

	}

	@Test
	public void test4() {
		String result = "";
        BufferedReader in = null;
        try {
//            String urlNameString  = "http://120.25.151.98:5998?<cmd id='5802'><ClientType val='1'/><TerminalType val='2'/><ActivityID val='110'/></cmd>";
//        	String urlNameString="http://120.25.151.98:5998/?%3Ccmd%20id=%275803%27%3E%3CUserID%20val=%271000%27/%3E%3CActivityID%20val=%27110%27/%3E%3C/cmd%3E";
//        	String urlNameString="http://120.25.151.98:5998/?%3Ccmd%20id=%275803%27%3E%3CUserID%20val=%271000%27/%3E%3CActivityID%20val=%27110%27/%3E%3C/cmd%3E";
        	
//            byte[] url = urlNameString.getBytes("UTF-8");
            
//            urlNameString=url.toString();
            String urlNameString  = "http://120.25.151.98:5998?";
            String params = "<cmd id='5802'><ClientType val='1'/><TerminalType val='2'/><ActivityID val='110'/></cmd>";
            params = URLEncoder.encode(params, "utf-8");
            URL realUrl = new URL(urlNameString+params);
           
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Content-Type", "text/xml; UTF-8");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");//ISO-8859-1
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
	}

	protected void doPost() {
		HttpServletRequest request=null;
		HttpServletResponse response=null;
		try {
			int len = request.getContentLength();
			System.out.println("数据流长度:" + len);
			// 获取HTTP请求的输入流
			InputStream is = request.getInputStream();
			// 已HTTP请求输入流建立一个BufferedReader对象
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			// 读取HTTP请求内容
			String buffer = null;
			StringBuffer sb = new StringBuffer();
			while ((buffer = br.readLine()) != null) {
				// 在页面中显示读取到的请求参数
				sb.append(buffer + "\n");
			}
			System.out.println("接收post发送数据:\n" + sb.toString().trim());

			PrintWriter out = response.getWriter();
			StringBuffer stringBuffer = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			stringBuffer.append("<response>");
			stringBuffer.append("<status>800</status>");
			stringBuffer
					.append("<tokenPwd>jkuiowerncxuidafjkfdaouifdaljkn</tokenPwd>");
			stringBuffer.append("</response>");
			out.write(stringBuffer.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			// log.error(e.getMessage(), e);
		}
	}

	public String getXmlString() {
		String xmldata = "<?xml version='1.0' encoding='UTF-8'?><cmd id='5802'><ClientType val='1'/><TerminalType val='1'/><ActivityID val='A2015111200777'/></cmd>";
		return xmldata;
		
//		StringBuffer stringBuffer = new StringBuffer(
//				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//		stringBuffer.append("<response>");
//		stringBuffer.append("<status>800</status>");
//		stringBuffer
//				.append("<tokenPwd>jkuiowerncxuidafjkfdaouifdaljkn</tokenPwd>");
//		stringBuffer.append("</response>");
//		return stringBuffer.toString();
		
	}
}
