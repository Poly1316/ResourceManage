package wang.polyblog.resourcemanager;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

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
@TestPropertySource("classpath:application.properties") //读取项目配置文件
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public abstract class BaseSpringBootTest {

    protected Logger logger = LoggerFactory.getLogger(BaseSpringBootTest.class);

    @Before
    public void setup() throws IOException {
        logger.info("开始测试...");
    }

    @After
    public void after() {
        logger.info("测试结束...");
    }
}
