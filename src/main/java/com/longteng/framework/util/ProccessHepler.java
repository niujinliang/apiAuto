package com.longteng.framework.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/19 0019.
 */
public class ProccessHepler {
    private static Logger logger = Logger.getLogger(ProccessHepler.class);

    /**
     * 关闭名为programName的进程
     *
     * @param programName
     */
    public static void CloseProcess(String programName) {
        Process listprocess;
        try {
            listprocess = Runtime.getRuntime().exec("cmd.exe /c tasklist");
            InputStream is = listprocess.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            String str = null;
            while ((str = r.readLine()) != null) {
                String id = null;
                Matcher matcher = Pattern.compile(programName + "[ ]*([0-9]*)")
                        .matcher(str);
                while (matcher.find()) {
                    if (matcher.groupCount() >= 1) {
                        id = matcher.group(1);
                        if (id != null) {
                            Integer pid = null;
                            try {
                                pid = Integer.parseInt(id);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            if (pid != null) {
                                Runtime.getRuntime().exec(
                                        "cmd.exe /c taskkill /f /pid " + pid);
                                logger.info("执行shell杀进程:"+"kill progress" + pid);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

