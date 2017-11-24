package cn.sf80.weixin.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class HttpsUtil {

    /**
     * 以https方式发送请求并将请求响应内容以String方式返回
     *
     * @param path   请求路径
     * @param method 请求方法
     * @param body   请求数据体
     * @return 请求响应内容转换成字符串信息
     */
    public static String httpsRequestToString(String path, String method, String body) {
        if (path == null || method == null) {
            return null;
        }

        String response = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection conn = null;
        try {
            //创建SSLConrext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new JEEWeiXinX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            //从上述对象中的到SSLSocketFactory
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            System.out.println(path);

            URL url = new URL(path);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            //设置请求方式（git|post）
            conn.setRequestMethod(method);

            //有数据提交时
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            //将返回的输入流转换成字符串
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            response = buffer.toString();
        } catch (Exception e) {

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException execption) {

            }
        }
        return response;
    }


    /**
     * 以https方式发送请求并将请求响应内容以String方式返回
     *
     * @param path 请求路径
     * @param file 请求数据体
     * @return 请求响应内容转换成字符串信息
     */
    public static String upLoadFile(String path, MultipartFile file) throws Exception {

        if (path == null) {
            return null;
        }
        //创建SSLConrext对象，并使用我们指定的信任管理器初始化
        TrustManager[] tm = {new JEEWeiXinX509TrustManager()};
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());

        //从上述对象中的到SSLSocketFactory
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        System.out.println(path);

        URL url = new URL(path);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setSSLSocketFactory(ssf);

        String result = null;
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setUseCaches(false); // post方式不能使用缓存
// 设置请求头信息

        con.setRequestProperty("Connection", "Keep-Alive");

        con.setRequestProperty("Charset", "UTF-8");
// 设置边界

        String BOUNDARY = "----------" + System.currentTimeMillis();

        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary="

                        + BOUNDARY);

// 请求正文信息

// 第一部分：

        StringBuilder sb = new StringBuilder();

        sb.append("--"); // 必须多两道线

        sb.append(BOUNDARY);

        sb.append("\r\n");



        sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.getSize() + "\";filename=\""

                + file.getOriginalFilename() + "\"\r\n");

        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

// 获得输出流

        OutputStream out = new DataOutputStream(con.getOutputStream());

// 输出表头

        out.write(head);

// 文件正文部分

// 把文件已流文件的方式 推入到url中

        DataInputStream in = new DataInputStream(file.getInputStream());

        int bytes = 0;

        byte[] bufferOut = new byte[1024];

        while ((bytes = in.read(bufferOut)) != -1) {

            out.write(bufferOut, 0, bytes);

        }

        in.close();

// 结尾部分

        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

        out.write(foot);

        out.flush();

        out.close();

        StringBuffer buffer = new StringBuffer();

        BufferedReader reader = null;

        try {

            // 定义BufferedReader输入流来读取URL的响应

            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line = null;

            while ((line = reader.readLine()) != null) {

                buffer.append(line);

            }

            if (result == null) {

                result = buffer.toString();

            }

        } catch (IOException e) {

            System.out.println("发送POST请求出现异常！" + e);

            e.printStackTrace();

            throw new IOException("数据读取异常");

        } finally {

            if (reader != null) {

                reader.close();

            }

        }
        return result;

    }

}

class JEEWeiXinX509TrustManager implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}