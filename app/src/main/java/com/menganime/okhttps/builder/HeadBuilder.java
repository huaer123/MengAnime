package com.menganime.okhttps.builder;


import com.menganime.okhttps.OkHttpUtils;
import com.menganime.okhttps.request.OtherRequest;
import com.menganime.okhttps.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
