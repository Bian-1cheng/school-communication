package com.commucation.demo.common.config;

import com.alibaba.fastjson.JSON;
import com.commucation.demo.common.lang.Result;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class EncoderClassVo implements Encoder.Text<Result>{
    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(EndpointConfig arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public String encode(Result result) throws EncodeException {
        String string = JSON.toJSONString(result);

        int index = string.indexOf("gmt_create");

        if (index != -1) {
            String first = string.substring(0, index);
            String second = string.substring(index).replaceFirst("T", " ");
            return first + second;
        }

        return string;
    }
}
