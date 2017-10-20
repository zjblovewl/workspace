package cn.linkage.util;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;
/**
 * SpringJUnit4ClassRunner默认加载classpath根目录下的log4j配置，我们的项目log4j配置不在根目录下，所以springtest会找不到。
 * 该类仅是为了指定log4j配置
 * @author caihz
 *
 */
public class SpringJUnit4ClassRunnerWithLog4j extends SpringJUnit4ClassRunner {

    static {
        try {
            Log4jConfigurer
                    .initLogging("classpath:config/base/logback.xml");
        } catch (FileNotFoundException ex) {
            System.err.println("Cannot Initialize log4j");
        }
    }

    public SpringJUnit4ClassRunnerWithLog4j(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

}
