package net.ycii.fc.entity;

public class MenuMsg
{
  private int errcode;
  private String errmsg;

  public int getErrcode()
  {
    return this.errcode;
  }
  public void setErrcode(int errcode) {
    this.errcode = errcode;
  }
  public String getErrmsg() {
    return this.errmsg;
  }
  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }
}