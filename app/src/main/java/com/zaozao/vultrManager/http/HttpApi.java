package com.zaozao.vultrManager.http;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zaozao.vultrManager.R;
import com.zaozao.vultrManager.utils.ApiKeyStore;
import com.zaozao.vultrManager.utils.AppUtil;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created by chenqisheng on 14/11/9.
 */
public class HttpApi {
    public static final String BASE_URL = "https://api.vultr.com/v1/";
    private static AsyncHttpClient httpClient = new AsyncHttpClient();

    /**
     * /v1/server/list
     * <p>
     * GET https://api.vultr.com/v1/server/list?api_key=EXAMPLE
     *
     * @param mContext
     * @param handler
     */
    public static void getInstances(Context mContext, TextHttpResponseHandler handler) {
        if (ApiKeyStore.getInstance().getApiKey(mContext).equals("")) {
            AppUtil.showToast((Activity) mContext, mContext.getString(R.string.api_key_blank));
            return;
        }
        String url = BASE_URL + "server/list";
        RequestParams requestParams = new RequestParams();
        requestParams.add("api_key", ApiKeyStore.getInstance().getApiKey(mContext));
        httpClient.get(mContext, url, requestParams, handler);
    }

    public static void getOneInstnce(Context mContext, String subId, TextHttpResponseHandler handler) {
        if (ApiKeyStore.getInstance().getApiKey(mContext).equals("")) {
            AppUtil.showToast((Activity) mContext, mContext.getString(R.string.api_key_blank));
            return;
        }
        String url = BASE_URL + "server/list";
        RequestParams requestParams = new RequestParams();
        requestParams.add("api_key", ApiKeyStore.getInstance().getApiKey(mContext));
        requestParams.add("SUBID", subId);
        httpClient.get(mContext, url, requestParams, handler);
    }

    //操作方法
    public static void startServer(Context mContext, String subId, TextHttpResponseHandler handler) {
        operateOneInstnce(mContext, "start", subId, handler);
    }

    public static void stopServer(Context mContext, String subId, TextHttpResponseHandler handler) {
        operateOneInstnce(mContext, "halt", subId, handler);
    }

    public static void rebootServer(Context mContext, String subId, TextHttpResponseHandler handler) {
        operateOneInstnce(mContext, "reboot", subId, handler);
    }

    private static void operateOneInstnce(Context mContext, String method, String subId, TextHttpResponseHandler handler) {
        if (ApiKeyStore.getInstance().getApiKey(mContext).equals("")) {
            AppUtil.showToast((Activity) mContext, mContext.getString(R.string.api_key_blank));
            return;
        }
        String url = BASE_URL + "server/" + method;
        Header[] headers = new Header[]{new BasicHeader("API-Key", ApiKeyStore.getInstance().getApiKey(mContext))};
        RequestParams requestParams = new RequestParams();
        requestParams.add("api_key", ApiKeyStore.getInstance().getApiKey(mContext));
        requestParams.add("SUBID", subId);
        httpClient.post(mContext, url, headers, requestParams, null, handler);
        Log.d("sean", "url = " + url + " key = " + ApiKeyStore.getInstance().getApiKey(mContext) + " id = " + subId + " timeout = " + httpClient.getTimeout());
    }

    public static void setTimeout(int timeout) {
        httpClient.setTimeout(timeout);
    }
}
