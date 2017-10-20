package cn.linkage.util;

import java.io.Serializable;
import java.util.List;

import cn.linkage.entity.Message;

/**
 * 
 * @author liuwj
 *	用户操作结果统一返回值
 */
public class ResultVo implements Serializable {

	private static final long serialVersionUID = -4893196859945933827L;
	/**
	 * result：返回结果 -0操作成功，1失败
	 * msg：返回消息提示
	 */
	public Integer ret;
	public Object msg;
	public List<Message> list;
	
	public List<Message> getList()
    {
        return list;
    }

    public void setList( List<Message> list )
    {
        this.list = list;
    }

    public ResultVo(){
		
	}
	
	public ResultVo(Integer result, Object msg,List<Message> list) {
		this.ret = result;
		this.msg = msg;
		this.list = list;
	}

	public Integer getRet() {
		return ret;
	}

	public void setRet(Integer ret) {
		this.ret = ret;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}
	
}
