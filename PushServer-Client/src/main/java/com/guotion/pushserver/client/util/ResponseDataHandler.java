package com.guotion.pushserver.client.util;

import com.guotion.pushserver.client.message.MapMessage;
import com.guotion.pushserver.client.message.NetMessage;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ResponseDataHandler {
    public static void dealList(List list, String attach, HttpServletResponse response) {
        NetMessage message = new MapMessage();
        message.setState(0);
        message.putData("response_data_key", list);
        ResponseClientUtil.response(response, message);
    }

    public static void dealObject(Object object, String attach, HttpServletResponse response) {
        NetMessage message = new MapMessage();
        if (object != null) {
            message.setState(0);
            message.putData("response_data_key", object);
        } else {
            message.setState(1);
            message.putData("attach_text", attach);
        }
        ResponseClientUtil.response(response, message);
    }

    public static void dealBoolean(boolean bool, String attach, HttpServletResponse response) {
        NetMessage message = new MapMessage();
        if (bool) {
            message.setState(0);
        } else {
            message.setState(1);
            message.putData("attach_text", attach);
        }
        ResponseClientUtil.response(response, message);
    }
}