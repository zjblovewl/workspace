package net.ycii.fc.web.wx;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.api.UserApi;
import net.ycii.entity.WXUserAuthorization;
import net.ycii.entity.WeiXinAuthorUser;
import net.ycii.entity.WeiXinUser;
import net.ycii.fc.entity.AccessToken;
import net.ycii.fc.entity.MenuMsg;
import net.ycii.fc.entity.WxParty;
import net.ycii.fc.service.ICoreService;
import net.ycii.fc.util.SignUtil;
import net.ycii.fc.util.WeixinUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"core"})
public class CoreController
{
  Logger log = LoggerFactory.getLogger(CoreController.class);

  @Autowired
  private ICoreService coreService;

  @RequestMapping(value={"/{partyId}.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void partyGet(@PathVariable Long partyId, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    wxGetCheck(partyId.longValue(), request, response);
  }

  @RequestMapping(value={"/{partyId}.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void partyPost(@PathVariable Long partyId, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String respMessage = this.coreService.processRequest(partyId.longValue(), request);
    this.log.info(respMessage);
    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
  }

  private void wxGetCheck(long partyId, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String signature = request.getParameter("signature");

    String timestamp = request.getParameter("timestamp");

    String nonce = request.getParameter("nonce");

    String echostr = request.getParameter("echostr");

    WxParty wxParty = this.coreService.getWxPartyByAppId(partyId);
    if (wxParty != null) {
      PrintWriter out = response.getWriter();

      if (SignUtil.checkSignature(wxParty.getToken(), signature, timestamp, nonce)) {
        out.print(echostr);
      }
      out.close();
      out = null;
    }
  }

  @RequestMapping(value={"forwardList.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String memu(HttpServletRequest request, HttpServletResponse response, Model model)
    throws ServletException, IOException
  {
    List partys = this.coreService.getWxParty();
    model.addAttribute("partys", partys);
    return "wx/menu/menu";
  }

  @RequestMapping(value={"/menuSubmit.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void menuSubmit(HttpServletRequest request, HttpServletResponse response, Model model)
    throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String partyId = request.getParameter("partyId");
    WxParty party = this.coreService.getWxPartyByAppId(Long.parseLong(partyId));
    String menu = request.getParameter("menu");

    String appId = party.getAppId();

    String appSecret = party.getAppSecret();

    String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

    String url = menu_create_url.replace("ACCESS_TOKEN", at.getAccess_token());

    PrintWriter out = response.getWriter();

    String json = WeixinUtil.httpRequest(url, "POST", menu);
    MenuMsg msg = (MenuMsg)new Gson().fromJson(json, MenuMsg.class);
    if ((msg != null) && (msg.getErrcode() == 0))
      out.print("success");
    else {
      out.print("error");
    }

    out.close();
  }
  
  @RequestMapping(value={"getOpenUserInfoByWeb.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String getOpenUserInfoByWeb(HttpServletRequest request, HttpServletResponse response, Model model)
    throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    
    String code = request.getParameter( "code" );
    WxParty party = this.coreService.getWxPartyByAppId(3);
    WXUserAuthorization userAuthorization  = UserApi.getUserAuthorizationCode( party.getAppId(), party.getAppSecret(), code );
    WeiXinAuthorUser wxUser = UserApi.getAhtorWeiXinUserInfo( userAuthorization.getOpenid(), userAuthorization.getAccess_token() );
    request.setAttribute( "user", wxUser );
    return "h5/allot/showuser";
  }
}