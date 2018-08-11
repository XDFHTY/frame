package com.cj.common.utils.http;

import com.cj.common.utils.domain.ApiResult;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public  class  HttpUtil {

    /**
     * @param response
     * @param apiResult
     * @throws IOException
     */
    public static void doReturn(HttpServletResponse response, ApiResult apiResult)  {
        try {

            Gson gson =new Gson();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;
            out = response.getWriter();
            out.append(gson.toJson(apiResult.toMap()));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
