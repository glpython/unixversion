package com.ysbz.tools;


import com.ysbz.model.ConfigData;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReRun {

    private static Logger logger = Logger.getLogger("unixversion");

    private static String jar_path = ConfigData.down_path;

    public static String restartJar(String version,String nversion){

        Process gprocess;
        List<String> processList = new ArrayList<String>();
        try {
            gprocess = Runtime.getRuntime().exec(new String[]{"sh","-c","ps aux | grep " + version + ".jar | awk '{print $1}'"});
            BufferedReader input = new BufferedReader(new InputStreamReader(gprocess.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (String.valueOf(processList.get(0)).equals("root")){
            logger.info("\t" + "platform type : 0");
            Process gprocess0;
            List<String> processList0 = new ArrayList<String>();
            try {
                gprocess0 = Runtime.getRuntime().exec(new String[]{"sh","-c","ps aux | grep " + version + ".jar | awk '{print $2}'"});
                BufferedReader input = new BufferedReader(new InputStreamReader(gprocess0.getInputStream()));
                String line = "";
                while ((line = input.readLine()) != null) {
                    processList0.add(line);
                }
                input.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Process kprocess;
            try {
                kprocess = Runtime.getRuntime().exec("kill -9 "+String.valueOf(processList0.get(0)));
                logger.info("\t" + "jar package kill status:" + kprocess.waitFor());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else {
            logger.info("\t" + "platform type : 1");
            Process kprocess;
            try {
                kprocess = Runtime.getRuntime().exec("kill -9 "+String.valueOf(processList.get(0)));
                logger.info("\t" + "jar package kill status:" + kprocess.waitFor());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        Process rprocess;
        try {
            rprocess = Runtime.getRuntime().exec("nohup " + ConfigData.java_path + " -jar " + jar_path + nversion + ".jar &");
        } catch (IOException e) {
            logger.info("\t" + "jar_run_exception:" + e.getMessage());
            e.printStackTrace();
        }

        return "SUCCESS";
    }

    public static String verifyProcess(String nversion){

        Process vprocess;
        List<String> processList = new ArrayList<String>();

        Process vprocess0;

        String jar_path = ConfigData.down_path;

        try {
            vprocess = Runtime.getRuntime().exec(new String[]{"sh","-c","ps aux | grep  "+ jar_path + nversion +".jar | awk '{print $6}'"});
            BufferedReader input = new BufferedReader(new InputStreamReader(vprocess.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();

            vprocess0 = Runtime.getRuntime().exec(new String[]{"sh","-c","ps aux | grep  "+ jar_path + nversion +".jar | awk '{print $13}'"});
            BufferedReader input0 = new BufferedReader(new InputStreamReader(vprocess0.getInputStream()));
            String line0 = "";
            while ((line0 = input0.readLine()) != null) {
                processList.add(line0);
            }
            input0.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(processList.contains(jar_path + nversion + ".jar")){
            logger.info("\t" + "The new version of the jar package starts SUCCESS");
            return "SUCCESS";
        }else {
            logger.info("\t" + "The new version of the jar package starts FALSE");
            return "FALSE";
        }
    }

    public static void startJar(String version){

        Process process;
        try {
            process = Runtime.getRuntime().exec("nohup " + ConfigData.java_path + " -jar " + jar_path + version + ".jar &");
        } catch (IOException e) {
            logger.info("\t" + "jar_run_exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

}
