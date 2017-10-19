package com.tyunsoft.base.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;

/**
 * http cookie store,
 * it's a singleton class
 * 
 * @author qintx
 */
public class HttpCookie {
    private static HttpCookie instance;
    private Map<String, Cookie[]> store = new HashMap<String, Cookie[]>();

    private HttpCookie() {
        load();
    }

    /**
     * get cookie by specified host
     * 
     * @param host
     * @return
     */
    public Cookie[] getCookie(String host) {
        synchronized(store) {
            return store.get(host);
        }
    }

    /**
     * save cookie of specified host
     * 
     * @param host
     * @param cookies
     */
    public void setCookie(String host, Cookie[] cookies) {
        synchronized(store) {
            store.put(host, cookies);
            persist();
        }
    }

    private void load() {
      //TODO read cookie from file or db
    }

    private void persist() {
        //TODO write cookie to file or db
    }

    public static HttpCookie newInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (HttpCookie.class) {
            if (instance != null) {
                return instance;
            }

            instance = new HttpCookie();
            return instance;
        }
    }
}
