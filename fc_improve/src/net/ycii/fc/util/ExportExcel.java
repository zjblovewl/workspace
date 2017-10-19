package net.ycii.fc.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcel {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void export(String filename, Map dataMap, String model, HttpServletResponse response){
		XLSTransformer transformer = new XLSTransformer();
		HSSFWorkbook workbook = null;
		try {
			InputStream in= getClass().getResource(model).openStream();
			long time1 = System.currentTimeMillis();
			workbook = transformer.transformXLS(in, dataMap);
			long time2 = System.currentTimeMillis();
			logger.debug("transformXLS耗时：" + (time2 - time1) + "ms");
			String ifWebPageDownLoad = (String)dataMap.get("ifWebPageDownLoad");
			if(!StringUtils.isEmpty( ifWebPageDownLoad ) && "notWebPageDownLoad".equals( ifWebPageDownLoad ) )
			{
				outExcelToFixDir(workbook,(String)dataMap.get( "fileFixDir" ) ,filename);
				return;
			}
			outExcel(workbook, response, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 把文件上传到指定位置
	 * <功能详细描述>
	 * @param workbook
	 * @param path
	 * @param filename [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void outExcelToFixDir(HSSFWorkbook workbook,String path,String filename)
	{
		try
        {
	        OutputStream outf = new FileOutputStream( path);
			try
			{
				workbook.write( outf );
				outf.close();
			} 
			catch ( Exception e )
			{
				logger.error( e );
			}
        } 
		catch ( FileNotFoundException e )
        {
        	logger.error( e );
        }
	}
	
	protected void outExcel(HSSFWorkbook workbook, HttpServletResponse response,
		    String fileName) throws UnsupportedEncodingException {
	       fileName = new String( fileName.getBytes( "utf-8" ), "ISO-8859-1" );
		   response.setContentType("application ns.ms-excel");
		   response.setHeader("Expires", "0");
		   response.setHeader("Cache-Control",
		     "must-revalidate, post-check=0, pre-check=0");
		   response.setHeader("Pragma", "public");
		   response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");

		   try {
			    //long time1 = System.currentTimeMillis();
				workbook.write(response.getOutputStream());
				//long time2 = System.currentTimeMillis();
				//logger.debug("生成文件耗时：" + (time2 - time1) + "ms");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
