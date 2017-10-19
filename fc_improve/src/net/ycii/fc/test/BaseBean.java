package net.ycii.fc.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/applicationContext.xml","file:WebRoot/WEB-INF/spring-servlet.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true)
public class BaseBean extends AbstractTransactionalJUnit4SpringContextTests{

}
