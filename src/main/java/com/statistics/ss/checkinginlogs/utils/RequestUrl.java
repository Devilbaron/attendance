package com.statistics.ss.checkinginlogs.utils;

import com.statistics.ss.checkingin.entity.Attendances;
import com.statistics.ss.checkingin.entity.RequestData;
import com.statistics.ss.checkinginlogs.entity.Kqjls;
import com.thinkgem.jeesite.common.config.Global;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.RequestDate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/10/8.
 */
public class RequestUrl {

    private static Logger logger = LoggerFactory.getLogger(RequestUrl.class);

    public String RequestUrl(String r) {
//        // 先调用登录接口获取cookie
//        String cookie = getCookie("http://192.168.1.90/fastgate/user/login");
//
//        StringBuffer document = new StringBuffer();
//        try {
//            URL url = new URL(r);//远程url地址
//            URLConnection conn = url.openConnection();
//            if (null != cookie && !"".equals(cookie)) {
//                conn.setRequestProperty("Cookie", cookie);
//            }
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
////            conn.setRequestMethod(requestMethod);
//            conn.connect();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//            String line = null;
//            while ((line = reader.readLine()) != null)
//                document.append(line + " ");
//            reader.close();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return document.toString();//返回值

        // 登陆 Url
        String loginUrl = Global.getConfig("updataloginurl");
        String cookie = null;
        // 全局客户端
        CloseableHttpClient httpClient = null;
        HttpResponse httpResponse = null;

        // 模拟登录
        try {
            org.apache.http.client.CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
            HttpPost postMethod = new HttpPost(loginUrl);
            postMethod.addHeader("Content-Type", "application/json; charset=utf-8");

            // 设置登陆时要求的信息，用户名和密码
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id", "admin");
            jsonObject.put("Pwd", "21232f297a57a5a743894a0e4a801fc3");
//            jsonObject.put("Pwd", "admin");
            String params = jsonObject.toString();
            postMethod.setEntity(new StringEntity(params, Charset.forName("UTF-8")));
            // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
//            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpResponse = httpClient.execute(postMethod);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                // 接口返回的数据
                HttpEntity httpLoginEntity = httpResponse.getEntity();
                if (httpLoginEntity != null) {
                    StringBuilder loginEntityStringBuilder = new StringBuilder();
                    BufferedReader loginBufferedReader = new BufferedReader
                            (new InputStreamReader(httpLoginEntity.getContent(), "UTF-8"), 8 * 1024);
                    try {
                        String line = null;
                        while ((line = loginBufferedReader.readLine()) != null) {
                            loginEntityStringBuilder.append(line + " ");
                        }
                        // 转成字符串
                        String loginResult = loginEntityStringBuilder.toString();
                        logger.debug("loginResult: " + loginResult);
                        loginBufferedReader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (null != loginBufferedReader) {
                            // 关闭reader
                            loginBufferedReader.close();
                        }
                    }
                }
                // 获得登陆后的 Cookie
                List<Cookie> cookies = cookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
                    if (cookies.get(i).getName().equals("JSESSIONID")) {
                        cookie = "JSESSIONID=" + cookies.get(i).getValue();
                    }

                }

            }


        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


        // 调用查询数据接口
        // 返回结果
        String result = null;
        try {
//            org.apache.http.client.CookieStore cookieStore = new BasicCookieStore();
//            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
            // url参数部分转码
            String url_base = r.substring(0, r.indexOf("=") + 1);
            String url_param = r.substring(r.indexOf("=") + 1, r.length());
            String url_encode = url_base + URLEncoder.encode(url_param, "UTF-8");
            HttpGet getMethod = new HttpGet(url_encode);
//            postMethod2.addHeader("Content-Type", "application/json; charset=utf-8");


            HttpResponse httpResponse2 = httpClient.execute(getMethod);
            int statusCode2 = httpResponse2.getStatusLine().getStatusCode();
            if (statusCode2 == HttpStatus.SC_OK) {
                // 登录成功
                HttpEntity httpEntity = httpResponse2.getEntity();
                if (httpEntity != null) {
                    StringBuilder entityStringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader
                            (new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
                    try {
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            entityStringBuilder.append(line + " ");
                        }
                        // 转成字符串
                        result = entityStringBuilder.toString();
                        bufferedReader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (null != bufferedReader) {
                            // 关闭reader
                            bufferedReader.close();
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;

    }

    public RequestData JsonResult(String url) {
        String urlDemon = RequestUrl(url);
        return JsonRequestUrl.getJsonString(urlDemon);
    }

    public List<Kqjls> Result(String url) {

        List<Kqjls> list = new ArrayList<Kqjls>();
//        "http://www.proxa.cn:8080/proxa/checking"
        String urlDemon = RequestUrl(url);
        // 解析.xml文件
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();

        Kqjls kqjls = null;
        InputSource in = new InputSource(new StringReader(urlDemon));
        in.setEncoding("UTF-8");
        Document document;

        try {
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
//            Document document = DocumentHelper.parseText(urlDemon);
            document = reader.read(in);
            // 通过document对象获取根节点bookstore
            Element kqjlsElement = document.getRootElement();

            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = kqjlsElement.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                Element kqjl = (Element) it.next();
                //转化对象
                kqjls = (Kqjls) XmlUtil.fromXmlToBean(kqjl, Kqjls.class);
                list.add(kqjls);
                // 获取kqjl的属性名以及 属性值
//                List<Attribute> kqjlAttrs = kqjl.attributes();
            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    /**
     * 获取cookie的值
     *
     * @param loginUrl
     * @return
     */
    public String getCookie(String loginUrl) {
        // 登陆 Url
        if (null == loginUrl || "".equals(loginUrl)) {
            loginUrl = "http://192.168.1.90:8088/fastgate/user/login";
        }
        String cookie = null;
        CloseableHttpClient httpClient = null;
        HttpResponse httpResponse = null;


        try {
            org.apache.http.client.CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
            HttpPost postMethod = new HttpPost(loginUrl);
            postMethod.addHeader("Content-Type", "application/json; charset=utf-8");

            // 设置登陆时要求的信息，用户名和密码
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id", "admin");
            jsonObject.put("Pwd", "admin");
            String params = jsonObject.toString();
            postMethod.setEntity(new StringEntity(params, Charset.forName("UTF-8")));
            // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
//            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpResponse = httpClient.execute(postMethod);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                // 获得登陆后的 Cookie
                List<Cookie> cookies = cookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
                    if (cookies.get(i).getName().equals("JSESSIONID")) {
                        cookie = "JSESSIONID=" + cookies.get(i).getValue();
                    }

                }

            }


        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return cookie;

    }
}
