package com.ysbz.model;


public class ConfigData {

    public static final String flag = "1"; //设备标识(android端为0,linux端为1)
    public static final String version_url = "http://10.20.10.176/wifi/terminal/program/"; //版本URL
    public static final String content_url = "http://10.20.10.176/wifi/content/program/";  //下载jar包URL
    public static final String complete_url = "http://10.20.10.176/wifi/content/completeProDown/";  //下载状态URL
    public static final String down_path = "/usr/lib/jvm/ji.hao/unixJar/"; //JAR包保存目录
    public static final String java_path = "/usr/lib/jvm/java/jre/bin/java"; //java目录

}
