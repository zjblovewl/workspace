package net.ycii.fc.util;

public class Music
{
  private String Title;
  private String Description;
  private String MusicUrl;
  private String HQMusicUrl;

  public String getTitle()
  {
    return this.Title;
  }

  public void setTitle(String title) {
    this.Title = title;
  }

  public String getDescription() {
    return this.Description;
  }

  public void setDescription(String description) {
    this.Description = description;
  }

  public String getMusicUrl() {
    return this.MusicUrl;
  }

  public void setMusicUrl(String musicUrl) {
    this.MusicUrl = musicUrl;
  }

  public String getHQMusicUrl() {
    return this.HQMusicUrl;
  }

  public void setHQMusicUrl(String musicUrl) {
    this.HQMusicUrl = musicUrl;
  }
}