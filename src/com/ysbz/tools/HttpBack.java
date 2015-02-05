package com.ysbz.tools;


import com.ysbz.model.ConnStatus;
import net.sf.json.JSONObject;

public class HttpBack {
    public static String httpback(String backurl){
        Connect connect = new Connect();
        String content = connect.getContent(backurl);
        if(ConnStatus.NETWORK_ERROR.equals(content) || ConnStatus.NETWORK_DISCONNECT.equals(content)){

            return ConnStatus.FAILED;

        }else{

            JSONObject jsonObject = JSONObject.fromObject(content);
            String status = jsonObject.getString("status");

            if(status.equals("success")){
                return ConnStatus.SUCCESS;
            }else {
                return ConnStatus.FAILED;
            }
        }
    }
}
