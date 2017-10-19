package net.ycii.fc.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.ycii.fc.entity.WxParty;

public abstract interface ICoreService
{
  public abstract String processRequest(long paramLong, HttpServletRequest paramHttpServletRequest);

  public abstract WxParty getWxPartyByAppId(long paramLong);

  public abstract List<WxParty> getWxParty();
}