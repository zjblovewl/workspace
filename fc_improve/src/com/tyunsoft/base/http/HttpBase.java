package com.tyunsoft.base.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

import com.tyunsoft.base.utils.Logger;

/**
 * simple HTTP request/response helper class. provide 2 method: get and post.
 * just send HTTP request and return response text.
 * 
 * @author qintx
 */
public class HttpBase {
    private static enum METHOD {
        GET, POST
    };

    /**
     * request by GET method
     * 
     * @param url
     * @param header
     * @param params
     * @return response text
     */
    public String get(String url, List<NameValuePair> header, List<NameValuePair> params) {
        HttpMethod method = getHttpMethod(METHOD.GET, url, header, params);
        String response = doRequest(method);
        method.releaseConnection();
        return response;
    }

    /**
     * request by POST method
     * 
     * @param url
     * @param header
     * @param params
     * @return response text
     */
    public String post(String url, List<NameValuePair> header, List<NameValuePair> params) {
        HttpMethod method = getHttpMethod(METHOD.POST, url, header, params);
        String response = doRequest(method);
        method.releaseConnection();
        Logger.getLogger().consoleDebug("http response:" + response);
        return response;
    }

    public String post(String url, List<NameValuePair> header, String paramBody) {
        HttpMethod method = getHttpMethod(METHOD.POST, url, header, paramBody);
        String response = doRequest(method);
        method.releaseConnection();
        Logger.getLogger().consoleDebug("http response:" + response);
        return response;
    }

    /**
     * send HTTP request and get the response text
     * 
     * @param method
     * @return
     */
    private String doRequest(HttpMethod method) {
        if (method == null) {
            Logger.getLogger().consoleError(
                    "http request failed: method is null", "qintx");
            return null;
        }

        try {
            HttpClient client = getHttpClient(method.getURI().getHost());
            // add SSL certification
            if (method.getURI().getURI().startsWith("https")) {
                setSSL();
            }

            addCookie(method.getURI().getHost(), client);
            Logger.getLogger().consoleDebug(
                    "send http request:" + method.getURI());
            //client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
            int retCode = client.executeMethod(method);
            if (retCode == 200) {
                saveCookie(method.getURI().getHost(), client);
                return method.getResponseBodyAsString();
            } else {
                return null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void saveCookie(String host, HttpClient client) {
        HttpCookie.newInstance()
                .setCookie(host, client.getState().getCookies());
    }

    private void addCookie(String host, HttpClient client) {
        client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        Cookie[] cookies = HttpCookie.newInstance().getCookie(host);
        if (cookies != null) {
            client.getState().addCookies(cookies);
        }
    }

    private HttpClient getHttpClient(String host) {
        HttpClient client = new HttpClient();
        return client;
    }

    private HttpMethod getHttpMethod(METHOD method, String url,
            List<NameValuePair> header, List<NameValuePair> params) {
        HttpMethod httpMethod = null;

        if (method == METHOD.GET) {
            httpMethod = new GetMethod(url);
        } else {
            httpMethod = new PostMethod(url);
        }

        // set request header and params
        setHeader(httpMethod, header);
        setParams(httpMethod, params);

        return httpMethod;
    }

    private HttpMethod getHttpMethod(METHOD method, String url,
            List<NameValuePair> header, String paramBody) {
        PostMethod httpMethod = new PostMethod(url);
        setHeader(httpMethod, header);
        httpMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        setParams(httpMethod, paramBody);
        return httpMethod;
    }

    /**
     * set HTTP request params
     * 
     * @param method
     * @param params
     */
    private void setParams(HttpMethod method, List<NameValuePair> params) {
        if (params == null || params.size() == 0) {
            return;
        }

        if (method instanceof PostMethod) {
            NameValuePair[] nvps = new NameValuePair[params.size()];
            ((PostMethod) method).setRequestBody(params.toArray(nvps));
        } else {
            HttpMethodParams httpparams = new HttpMethodParams();
            for (int i = 0; i < params.size(); i++) {
                NameValuePair param = params.get(i);
                httpparams.setParameter(param.getName(), param.getValue());
            }

            method.setParams(httpparams);
        }
    }

    private void setParams(PostMethod method, String paramBody) {
        RequestEntity requestEntity;
        try {
            requestEntity = new ByteArrayRequestEntity(paramBody.getBytes("utf-8"));
            method.setRequestEntity(requestEntity );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * set HTTP request header
     * 
     * @param method
     * @param header
     */
    private void setHeader(HttpMethod method, List<NameValuePair> header) {
        if (header == null || header.size() == 0) {
            return;
        }

        for (int i = 0; i < header.size(); i++) {
            NameValuePair param = header.get(i);
            method.addRequestHeader(param.getName(), param.getValue());
        }
    }

    private void setSSL() {
        Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);   
        Protocol.registerProtocol("https", myhttps);   
    }
}