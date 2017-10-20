package cn.linkage.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants
{
    private static String version;

    public static String getVersion()
    {
        return version;
    }

    @Value("#{appPropsBaseConstant['upload-version']}")
    public void setVersion( String version )
    {
        this.version = version;
    }

}
