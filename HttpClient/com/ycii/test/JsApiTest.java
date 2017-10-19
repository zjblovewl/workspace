package com.ycii.test;

import junit.framework.Assert;
import net.ycii.api.JsApi;
import net.ycii.entity.JsTicket;

import org.junit.Test;

public class JsApiTest extends BaseBean
{
    @Test
    public void getJSTicketByAccessTokenTest()
    {
        JsTicket ticket = JsApi.getJSTicketByAccessToken( token_key );
        Assert.assertNotNull( ticket );
        System.out.println(ticket.getTicket());
    }
}
