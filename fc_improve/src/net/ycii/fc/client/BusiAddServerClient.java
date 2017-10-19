
package net.ycii.fc.client;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import net.ycii.fc.entity.Sign;
import net.ycii.fc.entity.ThingReport;
import net.ycii.fc.service.impl.SignServiceImpl;
import net.ycii.fc.service.impl.ThingReportServiceImpl;
import net.ycii.fc.util.PropertiesUtil;

import org.apache.cxf.common.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class BusiAddServerClient
{

    // private String url =
    // "http://60.213.147.106:8084/slsb/services/BusiAddServer";
    private String url = PropertiesUtil.getProperty( "set.properties",
            "slsbWebService" );

    // xml log
    // private WsLogSave wsLogSave = new WsLogSave();

    public BusiAddServerClient()
    {
        // TODO Auto-generated constructor stub
    }

    // BusniessLogTool logTool = new BusniessLogTool();

    @SuppressWarnings( "finally" )
    public String busiAddServer( ThingReport report )
            throws MalformedURLException, ServiceException, RemoteException
    {

        String inputXml = "";
        String outputXml = "";
        String systemId = "123123123";
        String signId = report.getSignId();

        Sign sign = (Sign)SignServiceImpl.getIntance().queryById( signId );
        Map<String, String> tr = ThingReportServiceImpl.getIntance()
                .getCompanyInfo( report.getReporter() );
        String handAddres = sign.getHandAddress();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String cusVall = "";
        try
        {
            String cusT ="";
            if(!StringUtils.isEmpty( handAddres ))
            {
                cusT = handAddres.split( "," )[0];
                try
                {
                    cusVall = handAddres.split( "," )[1];
                } catch ( Exception e )
                {
                    cusVall = "";
                }
            }
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement( "DATA" );// ����ĵ���
            Element system = root.addElement( "SYSTEM" );
            system.addElement( "SYSTEMID" ).addText( systemId );
            system.addElement( "ERRCODE" ).addText( "0" );
            system.addElement( "ERRMSG" ).addText( "" );
            system.addElement( "FROMSYS" ).addText( "123123" );
            Element content = root.addElement( "CONTENT" );
            Element contentChild = content.addElement( "CONTENT" );
            Element form = contentChild.addElement( "FORM" );

            String formId = StringUtils.isEmpty( report.getSlsbId() )?"":report.getSlsbId();
            form.addElement( "FORM_ID" ).addText(formId);
            System.out.println( "================formId>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formId );
            form.addElement( "CUS_NAME" ).addText( report.getAppealerName() );
            form.addElement( "CUS_PHONE" ).addText( report.getAppealerPhone() );
            form.addElement( "INCOMING_TIME" ).addText(
                    sdf.format( report.getReportTime() ) );
            String appealerAddress = report.getAppealerAddress();
            if(null != appealerAddress && appealerAddress.indexOf( "," ) != -1)
            {
                String[] aa = appealerAddress.split( "," );
                form.addElement( "CUS_T" ).addText( aa[0] );
                form.addElement( "CUS_VALL" ).addText( aa[1] );
            }
            else
            {
                form.addElement( "CUS_T" ).addText( "" );
                form.addElement( "CUS_VALL" ).addText( "" );
            }
            //form.addElement( "CUS_ADDRESS" ).addText( StringUtils.isEmpty(report.getAppealerAddress())?"":report.getAppealerAddress());
            form.addElement( "CONTENT_TEXT" ).addText( report.getDescription() );
            form.addElement( "CADRESL_NEMA" ).addText( tr.get( "userName" ) );
            form.addElement( "COMPANY_PHONE" ).addText( tr.get( "userPhone" ) );
            form.addElement( "COMPANY_ADDRESS" ).addText(
                    !StringUtils.isEmpty( tr.get( "userAddress" ) ) ? tr
                            .get( "userAddress" ) : "" );
            form.addElement( "COMPANY" ).addText( tr.get( "deptName" ) );

            // form.addElement("FORM_ID").addText( "33333" );
            // form.addElement("CUS_NAME").addText( "123123" );
            // form.addElement("CUS_PHONE").addText( "12312312" );
            // form.addElement("INCOMING_TIME").addText( "1231231" );
            // form.addElement("CUS_T").addText( "123123" );
            // form.addElement("CUS_VALL").addText( "123123" );
            // form.addElement("CONTENT_TEXT").addText( "12312312" );
            // form.addElement("CADRESL_NEMA").addText( "12344ddda" );
            // form.addElement("COMPANY_PHONE").addText( "12414253" );
            // form.addElement("COMPANY_ADDRESS").addText( "asdfasdfasd" );
            // form.addElement("COMPANY").addText( "asdfasd" );
            inputXml = document.asXML();

            System.out.println( "^^^^^^^^^^^^^^^^^^^^^" + inputXml );

            BusiAddServerPortType server;

            server = new BusiAddServerLocator()
                    .getBusiAddServerHttpPort( new URL( url ) );

            outputXml = server.addFormBusi( inputXml );
            System.out.println( "================appealerAddress>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + appealerAddress );

            Document doc = DocumentHelper.parseText( outputXml.toString() );
            Element root1 = doc.getRootElement();
            Element content1 = root1.element( "CONTENT" );
            Element form1 = content1.element( "FORM" );
            System.out.println( form1.element( "FORM_ID" ).getText() );

        } catch ( Exception e )
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                // wsLogSave.save(systemId, inputXml, outputXml, "1");
            } catch ( Exception e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return outputXml;
        }
    }

    @SuppressWarnings( "finally" )
    public String dealReport( ThingReport report )
            throws MalformedURLException, ServiceException, RemoteException
    {

        String inputXml = "";
        String outputXml = "";
        String systemId = "123123123";
        String signId = report.getSignId();

        try
        {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement( "DATA" );// ����ĵ���
            Element system = root.addElement( "CONTENT" );
            system.addElement( "FORM_ID" ).addText( "15032015581758170050" );
            system.addElement( "FLAG" ).addText( "0" );
            system.addElement( "DESCRIPTION" ).addText( "12312" );

            inputXml = document.asXML();

            System.out.println( "^^^^^^^^^^^^^^^^^^^^^" + inputXml );

            BusiAddServerPortType server;

            server = new BusiAddServerLocator()
                    .getBusiAddServerHttpPort( new URL( url ) );

            outputXml = server.dealreport( inputXml );

            System.out.println( "##########################################"
                    + outputXml );

            StringReader xmlReader = new StringReader( outputXml.trim() );
            /** **创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入 */
            InputSource xmlSource = new InputSource( xmlReader );
            /** *创建一个SAXBuilder* */
            SAXBuilder builder = new SAXBuilder();
            /** *通过输入源SAX构造一个Document** */
            org.jdom.Document doc = builder.build( xmlSource );
            org.jdom.Element e = doc.getRootElement();
            System.out.println( "========================================"
                    + e.getChild( "CONTENT" ).getChild( "FORM_ID" ).getText() );

        } catch ( Exception e )
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                // wsLogSave.save(systemId, inputXml, outputXml, "1");
            } catch ( Exception e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return outputXml;
        }

    }

    public static void main( String[] args ) throws MalformedURLException,
            RemoteException, ServiceException
    {
        BusiAddServerClient client = new BusiAddServerClient();
        try
        {
            ThingReport re = new ThingReport();
            re.setAppealerName( "张三" );
            re.setAppealerPhone( "12312123" );
            re.setReportTime( new Date() );
            re.setSignId( "14C31697FAD043EA004218FDCC9F13CD" );
            re.setDescription( "123123123" );
            re.setReporter( "flymz" );
            String a = client.dealReport( re );
            System.out.println( a );
        } catch ( Exception e )
        {
            e.printStackTrace();

        }
        System.out.println( "cehsiceshi" );
    }

}
