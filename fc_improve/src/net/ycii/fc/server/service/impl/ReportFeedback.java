
package net.ycii.fc.server.service.impl;

import java.util.Date;

import net.ycii.fc.entity.ThingReport;
import net.ycii.fc.server.service.IReportFeedback;
import net.ycii.fc.service.impl.ThingReportServiceImpl;

import org.apache.cxf.common.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class ReportFeedback implements IReportFeedback
{

    public ReportFeedback()
    {
        
    }
    
    public String dealreport( String obj )
    {
        Document doc;
        boolean flagRes = true;
        try
        {
            String formId = "";
            String flag = "";
            doc = DocumentHelper.parseText(obj.toString());
            Element root = doc.getRootElement();
            Element content = root.element("CONTENT");
            formId = content.element("FORM_ID").getText(); 
            flag = content.element("FLAG").getText(); 
            System.out.println("====================返回报文====================\n："+root.asXML());
            ThingReport thingReport = (ThingReport)ThingReportServiceImpl.getIntance().queryByFormId(formId);
            String doAction = StringUtils.isEmpty(thingReport.getAppealResult())?"":thingReport.getAppealResult();
            String dealUser = StringUtils.isEmpty(thingReport.getDealUser())?"":thingReport.getDealUser();
            String description = StringUtils.isEmpty(thingReport.getDealSuggesttion())?"":thingReport.getDealSuggesttion();
            if ( "1".equals( flag ) )
            {
                String actionTemp = content.element("DO_ACTION").getText();
                String dealUserTemp = content.element("ACTOR").getText(); 
                String descriptionTemp = content.element("DESCRIPTION").getText();         
                if("办结(c)".equals(doAction))
                {
                    if("办结(c)".equals(actionTemp))
                    {
                        doAction = actionTemp; 
                        dealUser = dealUserTemp;
                        description = descriptionTemp;
                    }
                }else
                {
                    doAction = actionTemp; 
                    dealUser = dealUserTemp;
                    description = descriptionTemp;
                }                  
                thingReport.setDealTime( new Date() );
            }
            else if("2".equals( flag ))
            {
                //删除
                ThingReportServiceImpl.getIntance().deleteBySlsbId( formId );
            }
            else
            {
                String descriptionTemp = content.element("DESCRIPTION").getText(); 
                doAction = "回退";
                dealUser = "";
                description = descriptionTemp;
            }
            thingReport.setAppealResult( doAction );
            thingReport.setDealSuggesttion( description );
            thingReport.setDealUser( dealUser );            
            System.out.println("===============================>>>>>>>>>>>>>>>>>>>>>"+thingReport);
//            thingReport.setDescription( description );
            flagRes = ThingReportServiceImpl.getIntance().updateById( thingReport );
        } catch ( DocumentException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return String.valueOf( flagRes );
    }
}