package com.miaosha.common.request;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.miaosha.common.conf.App;
import com.miaosha.common.utils.MD5;
import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONObject;

@Log4j2
public class RequestCert {

    public static final String SIGN = "sign";

    public static boolean verify(HttpServletRequest request, String signKey) throws UnsupportedEncodingException{

        SortedMap<String, Object> parameters = getRequestParameters(request);

        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        uri = uri.substring(ctx.length());
        //log.info(App.serverDomain + uri + "?" + parameters.remove("PARAMETERS"));

        //测试不需要
        if (!App.debug) {
            String sign = (String)parameters.remove(SIGN);
            if(null == sign || sign.trim().length() <= 0){
                //log.info("Parameter Sign is NULL.");
                return false;
            }

            if(sign.equals(getSign(parameters, signKey))){
                return true;
            }
            //log.info("Request Verify Failure.");
        }
        return true;
    }

    public static SortedMap<String, Object> getRequestParameters(HttpServletRequest request) throws UnsupportedEncodingException{
        SortedMap<String,Object> params = new TreeMap<String,Object>();
        Map<String, String[]> requestParams = request.getParameterMap();
        String parameters = "";
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            parameters += name + "=" + valueStr + "&";
            params.put(name, valueStr);
        }

        params.put("PARAMETERS", parameters);
        return params;
    }

    public static String getSign(SortedMap<String, Object> parameters, String signKey){
        StringBuilder sb = new StringBuilder();
        Set<Entry<String, Object>> es = parameters.entrySet();
        Iterator<Entry<String, Object>> it = es.iterator();
        while(it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Entry entry = it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        sb.append("key=" + signKey);
        String sign = MD5.md5(sb.toString()).toLowerCase();
        return sign;
    }

    public static SortedMap<String, String[]> readJSONSortMap(HttpServletRequest request){
        SortedMap<String,String[]> params = new TreeMap<String, String[]>();
        try {
            StringBuffer json = new StringBuffer();
            String line = null;

            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }

            JSONObject jsonObject = JSONObject.fromObject(json.toString());
            @SuppressWarnings("rawtypes")
            Iterator iterator = jsonObject.keys();
            while(iterator.hasNext()){
                String name = (String) iterator.next();
                String value = jsonObject.getString(name);
                String[] values = value.split(",");
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, values);
            }

        } catch(Exception e) {
            System.out.println(e.toString());
        }

        return params;
    }
}
