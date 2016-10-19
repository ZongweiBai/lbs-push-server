package com.guotion.pushserver.client.request.log;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class RequestLogListener implements ServletRequestListener {

    private Logger logger = Logger.getLogger(RequestLogListener.class);

    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        Map<String, Object> map = request.getParameterMap();
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry entry : map.entrySet()) {
            try {
                jsonObject.put((String) entry.getKey(), entry.getValue());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        logger.error(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())) + " 来访者IP：" + request.getRemoteHost() + ",访问的URI：" + request.getRequestURI() + ",请求参数：" + jsonObject.toString());
    }
}