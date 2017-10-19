package net.ycii.fc.exception;

/**
 * Copyright(c) 2013 hofort. All Rights Reserved. Compiler: JDK1.6.0_23
 * 
 * @author Kaylves
 * @create_date 2013-12-6 下午02:43:57
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2013-12-6 下午02:43:57
 * @description 异常类 项目自定义异常需基础该类
 */
public class BaseException extends RuntimeException
{

    private static final long serialVersionUID = 9034355306672002412L;

    public BaseException( String message )
    {
        super( message );
    }

    public BaseException( Throwable e )
    {
        super( e );
    }

}
