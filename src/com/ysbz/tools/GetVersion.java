package com.ysbz.tools;

import com.ysbz.model.ConfigData;
import com.ysbz.model.ConnStatus;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class GetVersion {

    private static Logger logger = Logger.getLogger("unixversion");
    //属性文件的路径
    static String profilepath = ConfigData.down_path + "version.properties";
    private static Properties props = new Properties();

    static {

        InputStream in = null;

        try {
            in =  new FileInputStream(profilepath);
            props.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.exit(-1);
        }finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //读取
    public static String getKeyValue(String key) {

        return props.getProperty(key);
    }

    //更新
    public static String writeProperties(String keyname,String keyvalue) {

        try {
            FileOutputStream fos = new FileOutputStream(profilepath);
            props.setProperty(keyname, keyvalue);
            props.store(fos, "Update '" + keyname + "' value");
            fos.flush();
            fos.close();
            logger.info("\t" + "version file update success");
            return ConnStatus.SUCCESS;
        } catch (IOException e) {
            logger.info("\t" + "version file update error");
            return ConnStatus.FAILED;
        }
    }

}
