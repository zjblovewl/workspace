package com.tyunsoft.base.entity;

/**
 * 文件对象，上传文件是返回的对象
 * @author flymz
 *
 */
public class FileEntity 
{
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件大小
	 */
	private byte[] fileBytes;
	
	/**
	 * 文件后缀
	 */
	private String fileSuffix;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }
	
}
