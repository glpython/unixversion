package com.ysbz.main;

import com.ysbz.model.ConfigData;
import com.ysbz.model.ConnStatus;
import com.ysbz.tools.Connect;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.TimerTask;

import static com.ysbz.tools.Download.download;
import static com.ysbz.tools.GetMac.getMAC;
import static com.ysbz.tools.GetVersion.getKeyValue;
import static com.ysbz.tools.GetVersion.writeProperties;
import static com.ysbz.tools.HttpBack.httpback;
import static com.ysbz.tools.ReRun.restartJar;
import static com.ysbz.tools.ReRun.startJar;
import static com.ysbz.tools.ReRun.verifyProcess;


public class Task extends TimerTask {

    private static Logger logger = Logger.getLogger("unixversion");

    public void run(){

        //初始版本
        String init_version = getKeyValue("local_version");

        //获取MAC地址
        String mac = getMAC();

        //创建链接
        Connect connect = new Connect();
        logger.info("\t" + "Request version is updated URL:" + ConfigData.version_url + mac + "/" + ConfigData.flag + "/" + init_version);

        String content = connect.getContent(ConfigData.version_url + mac + "/" + ConfigData.flag + "/" + init_version);
        logger.info("\t" + "Online Version Information:" + content);

        if(ConnStatus.NETWORK_ERROR.equals(content) || ConnStatus.NETWORK_DISCONNECT.equals(content)){
            logger.info("\t" + "Heartbeat:" + content);

        }else {
            JSONObject jsonObject = JSONObject.fromObject(content);
            logger.info("\t" + "The online version of the content:" + jsonObject);

            String status = jsonObject.getString("status");

            if (status.equals("success")){

                int version = JSONObject.fromObject(jsonObject.getJSONArray("data").get(0)).getInt("version");
                logger.info("\t" + "Online version number:" + String.valueOf(version));

                if (Integer.parseInt(init_version) < version){
                    logger.info("\t" + "Download the updated version"+ String.valueOf(version) +" start>>");

                    int d_status = download(String.valueOf(version), mac);
                    logger.info("\t" + "Download the updated version" + String.valueOf(version) + " end>>,d_status:" + String.valueOf(d_status));

                    if (d_status == 0){
                        String up_status = writeProperties("local_version",String.valueOf(version));
                        logger.info("\t" + "Update version.properties state up_status:" + up_status);

                        String re_status = restartJar(init_version,String.valueOf(version));
                        logger.info("\t" + "Restart the new version jar package state re_status:" + re_status);

                        String verify_status = verifyProcess(String.valueOf(version));
                        logger.info("\t" + "Verify that the new version of the jar package program is running state verify_status:" + verify_status);

                        if(up_status.equals("SUCCESS") && re_status.equals("SUCCESS") && verify_status.equals("SUCCESS")) {

                            String back_status = httpback(ConfigData.complete_url + mac + "/" + String.valueOf(version) + "/1");
                            logger.info("\t" + "http back status back_status:" + back_status);
                        }else {
                            logger.info("\t" + "up_status, re_status, verify_status not all SUCCESS");

                        }
                    }

                }else if(Integer.parseInt(init_version) == version){
                    logger.info("\t" + String.valueOf(version) + " version no update , check current version status");
                    String run_status = verifyProcess(String.valueOf(version));
                    if(run_status.equals("FALSE")){
                        logger.info("\t" + String.valueOf(version) + " version rerun");
                        startJar(String.valueOf(version));
                    }else {
                        logger.info("\t" + String.valueOf(version) + " version run");
                    }
                }
            }else if (status.equals("noRecord")){
                logger.info("\t" + "Return status noRecord");

            }else {
                logger.info("\t" + "Version Information request return status:" + status + ",Callback run");

            }
        }

    }
}
