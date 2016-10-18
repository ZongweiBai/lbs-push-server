package com.guotion.pushserver.client.handler;

import com.guotion.pushserver.client.util.ResponseDataHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class ExceptionHandler implements HandlerExceptionResolver {
    private Log log = LogFactory.getLog("ERROR");

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        Map<String, Object> map = httpServletRequest.getParameterMap();
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry entry : map.entrySet()) {
            try {
                jsonObject.put((String) entry.getKey(), entry.getValue());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        this.log.error("---------------------SPLIT LINE---------------------------");
        this.log.error("request uri :" + httpServletRequest.getRequestURI());
        this.log.error("request params :" + jsonObject.toString());
        this.log.error("err message :" + e.getMessage());
        ResponseDataHandler.dealBoolean(false, "SERVER ERROR", httpServletResponse);
        return null;
    }

}