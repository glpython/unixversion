package com.ysbz.tools;

import com.ysbz.model.ConnStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Connect {
    public String getContent(String url) {

        String result = "";
        URL realUrl;

        try {
            realUrl = new URL(url);

        } catch (MalformedURLException e) {

            return ConnStatus.URI_ERROR;
        }

        URLConnection connection;

        try {
            // 打开和URL之间的连接
            connection = realUrl.openConnection();
            // 建立实际的连接
            connection.setConnectTimeout(1000*10);
            connection.setReadTimeout(1000*10);
            connection.connect();

        } catch (IOException e) {

            return ConnStatus.NETWORK_ERROR;
        }

        BufferedReader br = null;
        InputStreamReader in = null;

        try {

            // 定义 BufferedReader输入流来读取URL的响应
            in = new InputStreamReader(connection.getInputStream());

            br = new BufferedReader(in);

            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {

            return ConnStatus.NETWORK_DISCONNECT;
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }

                if (br != null) {
                    br.close();
                }
            } catch (Exception e2) {
                return ConnStatus.UNKNOWN_ERROR;
            }

        }

        return result;
    }
}
