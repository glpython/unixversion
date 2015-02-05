package com.ysbz.tools;


import com.ysbz.model.ConfigData;
import com.ysbz.FileDownManager;
import com.ysbz.RemoteFile;
import org.apache.log4j.Logger;

public class Download {

    private static Logger logger = Logger.getLogger("unixversion");

    public static Integer download(String version,String mac){

        RemoteFile remoteFile = new RemoteFile();

        remoteFile.setSavePath(ConfigData.down_path + version + ".jar");
        remoteFile.setUrl(ConfigData.content_url + mac + "/" + version);

        logger.info("\t" + "download url:" + ConfigData.content_url + mac + "/" + version);

        FileDownManager fileDownManager = new FileDownManager(remoteFile);

        int status = fileDownManager.down();

        logger.info("\t" + "download status:" + String.valueOf(status));

        return status;

    }

}
