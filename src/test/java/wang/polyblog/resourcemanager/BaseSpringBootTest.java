package wang.polyblog.resourcemanager;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： Wang Xiaotao
 * @date： 2021/4/26 16:28
 * @description： 单元测试公共父类，用于规定一些公共配置
 * @modifiedBy：
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public abstract class BaseSpringBootTest {

    protected Logger logger = LoggerFactory.getLogger(BaseSpringBootTest.class);

    @Before
    public void init() {
        logger.info("开始测试...");
    }

    @After
    public void after() {
        logger.info("测试结束...");
    }
}