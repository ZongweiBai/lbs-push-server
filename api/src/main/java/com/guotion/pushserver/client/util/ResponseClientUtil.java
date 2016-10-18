package com.guotion.pushserver.client.util;

import com.google.gson.Gson;
import com.guotion.pushserver.client.message.NetMessage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseClientUtil {

    private static Gson gson = new Gson();

    public static void response(HttpServletResponse response, NetMessage message) {
        System.out.println("响应消息:" + gson.toJson(message.getDatas()));
        System.out.println();
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(gson.toJson(message.getDatas()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}