package wang.polyblog.resourcemanager.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.polyblog.resourcemanager.BaseSpringBootTest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： Wang Xiaotao
 * @date： 2021/4/26 16:50
 * @description： ServiceController单元测试
 * @modifiedBy：
 * @version: 1.0
 */
class ServiceControllerTest extends BaseSpringBootTest {

    @Autowired
    private ServiceController serviceController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
    }

    @Test
    void getAllService() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/service/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void addService() {
    }

    @Test
    void updateService() {
    }

    @Test
    void delService() {
    }

    @Test
    void delMoreService() {
    }

    @Test
    void exportServiceExcel() {
    }
}