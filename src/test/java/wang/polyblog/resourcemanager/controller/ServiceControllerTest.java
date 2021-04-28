package wang.polyblog.resourcemanager.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;

import java.lang.reflect.Array;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.polyblog.resourcemanager.BaseSpringBootTest;
import wang.polyblog.resourcemanager.entity.Pager;
import wang.polyblog.resourcemanager.entity.Service;
import wang.polyblog.resourcemanager.service.ServiceService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： Wang Xiaotao
 * @date： 2021/4/26 16:50
 * @description： ServiceController单元测试
 * @modifiedBy：
 * @version: 1.0
 */
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
class ServiceControllerTest extends BaseSpringBootTest {

    /*
     * 被测对象，用@InjectMocks标注，那些被@mock标注的对象就会自动注入其中。
     * 另一个注意点是这里的MatchingServiceImpl是直接new出来(Mockito 1.9版本后不new也可以)，而不是通过spring容器注入的。因为这里我不需要从spring容器中
     * 获得其他依赖，不需要database ，redis ，zookeeper，mq，啥都不依赖，所以直接new
     */
    @InjectMocks
    private ServiceController serviceController;

    @Autowired
    private MockMvc mockMvc;

    /**
     * 被@Mock标注的对象会自动注入到被@InjectMocks标注的对象中
     */
    @Mock
    private ServiceService serviceService;

    @BeforeEach //这个方法在每个方法执行之前都会执行一遍。这个注解很重要，mock对象生效必须使用BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(serviceService);
//        ReflectionTestUtils.setField(serviceController, "serviceService", serviceService);    //之前为解决mock不生效问题，不需要
        mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();   //初始化MockMvc对象
    }

    /*
    获取服务器首页列表单元测试方法
     */
    @Test
    void getAllService() throws Exception {
        Service serviceParams = new Service();
        Pager<Service> pager = new Pager<>();
        Optional<Pager<Service>> optionalPager;

        //构造数据过程
        serviceParams.setId(9999L); //提供ID方便后续更新和删除操作
        serviceParams.setIp("172.16.40.189");
        serviceParams.setProject("单元测试");
        serviceParams.setStatus("在用");
        serviceParams.setUsername("root");
        serviceParams.setPassword("password");
        serviceParams.setRole("单元测试角色");
        serviceParams.setSystem_version("Centos 6.7");
        serviceParams.setCpu("Intel(R) Xeon(R) Platinum 8163 CPU @ 2.50GHz");
        serviceParams.setMemory("2.0G");
        serviceParams.setDisk("/dev/vda:40GiB\\ntype:dos");
        serviceParams.setRemark("单元测试正常用例");
        logger.info("构造的Service层mock数据为：" + serviceParams.toString());
        pager.setPage(1);
        pager.setSize(10);
        pager.setTotal_page(1);
        pager.setTotal(1L);
        pager.setList(Collections.singletonList(serviceParams));
        logger.info("构造的Pager层的Mock数据为：" + pager.toString());
        optionalPager = Optional.of(pager);
        logger.info("构造的Optional层的Mock数据为：" + optionalPager.toString());

        //mock模拟serviceService返回
        Mockito.when(serviceService.getAll(any(), any(), any(), any(), any())).thenReturn(optionalPager);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/service/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json")) //验证响应contentType == application/json;charset=UTF-8
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) //验证code是否为200，jsonPath的使用
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("操作成功!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0]['ip']").value("172.16.40.189"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0]['id']").value(9999L));;

        //解决控制台打印body中文乱码的问题
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        resultActions.andDo(MockMvcResultHandlers.print());
        // 验证调用上面的service 方法后是否 serviceService.getAll() 调用过，
        Mockito.verify(serviceService).getAll(any(), any(), any(), any(), any());
        logger.info(response.getContentAsString());
    }

    /*
    添加服务器单元测试方法-提供id路径测试
     */
    @Test
    void addService() throws Exception {
        doNothing().when(serviceService).addService(Mockito.any(Service.class));    //mock addService方法，doNothing
        Service serviceParams = new Service();
        serviceParams.setId(9999L); //提供ID方便后续更新和删除操作
        serviceParams.setIp("172.16.40.189");
        serviceParams.setProject("单元测试");
        serviceParams.setStatus("在用");
        serviceParams.setUsername("root");
        serviceParams.setPassword("password");
        serviceParams.setRole("单元测试角色");
        serviceParams.setSystem_version("Centos 6.7");
        serviceParams.setCpu("Intel(R) Xeon(R) Platinum 8163 CPU @ 2.50GHz");
        serviceParams.setMemory("2.0G");
        serviceParams.setDisk("/dev/vda:40GiB\\ntype:dos");
        serviceParams.setRemark("单元测试正常用例");
        logger.info("单元测试/service/add接口添加的数据为：" + serviceParams.toString());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/service/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(serviceParams)))    //添加post参数
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) //验证code是否为200，jsonPath的使用
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("操作成功!"));
        //解决控制台打印body中文乱码的问题
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        resultActions.andDo(MockMvcResultHandlers.print());
        logger.info(response.getContentAsString());
        //验证addService方法被调用过
        Mockito.verify(serviceService).addService(Mockito.any(Service.class));
        //保证这个测试用例中所有被Mock的对象的相关方法都已经被Verify过了
        Mockito.verifyNoMoreInteractions(serviceService);
    }

    /*
    添加服务器单元测试方法-不提供id路径测试
     */
    @Test
    void addServiceNoId() throws Exception {
        doNothing().when(serviceService).addService(Mockito.any(Service.class));    //mock addService方法，doNothing
        Service serviceParams = new Service();
        serviceParams.setIp("172.16.40.189");
        serviceParams.setProject("单元测试");
        serviceParams.setStatus("1");
        serviceParams.setUsername("root");
        serviceParams.setPassword("password");
        serviceParams.setRole("单元测试角色");
        serviceParams.setSystem_version("Centos 6.7");
        serviceParams.setCpu("Intel(R) Xeon(R) Platinum 8163 CPU @ 2.50GHz");
        serviceParams.setMemory("2.0G");
        serviceParams.setDisk("/dev/vda:40GiB\\ntype:dos");
        serviceParams.setRemark("单元测试正常用例");
        logger.info("单元测试/service/add接口添加的数据为：" + serviceParams.toString());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/service/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(serviceParams)))    //添加post参数
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) //验证code是否为200，jsonPath的使用
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("操作成功!"));
        //解决控制台打印body中文乱码的问题
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        resultActions.andDo(MockMvcResultHandlers.print());
        logger.info(response.getContentAsString());
        //验证addService方法被调用过
        Mockito.verify(serviceService).addService(Mockito.any(Service.class));
        //保证这个测试用例中所有被Mock的对象的相关方法都已经被Verify过了
        Mockito.verifyNoMoreInteractions(serviceService);
    }

    /*
    服务器列表单条数据更新方法单元测试。提供id=9999的数据供更新用
     */
    @Test
    void updateService() throws Exception {
        Service serviceParams = new Service();
        serviceParams.setIp("172.16.40.190");
        serviceParams.setProject("更新接口单元测试");
        serviceParams.setStatus("1");
        serviceParams.setUsername("root1");
        serviceParams.setPassword("password1");
        serviceParams.setRole("更新功能单元测试角色");
        serviceParams.setSystem_version("update-Centos 6.7");
        serviceParams.setCpu("update-Intel(R) Xeon(R) Platinum 8163 CPU @ 2.50GHz");
        serviceParams.setMemory("update-2.0G");
        serviceParams.setDisk("update-/dev/vda:40GiB\\ntype:dos");
        serviceParams.setRemark("update-更新功能单元测试正常用例");
        logger.info("单元测试/service/update接口更新的数据为：" + serviceParams.toString());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/service/update/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(serviceParams)))    //添加post参数
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) //验证code是否为200，jsonPath的使用
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("操作成功!"));

        //解决控制台打印body中文乱码的问题
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        resultActions.andDo(MockMvcResultHandlers.print());
        logger.info(response.getContentAsString());
    }

    /*
    导出功能正向用例
     */
    @Test
    void exportServiceExcel() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/service/export"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/octet-stream"));
        //解决控制台打印body中文乱码的问题
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        resultActions.andDo(MockMvcResultHandlers.print());
        logger.info(response.getContentAsString());
    }

    /*
    删除接口正常用例
     */
    @Test
    void delService() throws Exception {
        doNothing().when(serviceService).delService(anyLong());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/service/delete/9999"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) //验证code是否为200，jsonPath的使用
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("删除成功！"));

        //解决控制台打印body中文乱码的问题
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        resultActions.andDo(MockMvcResultHandlers.print());
        logger.info(response.getContentAsString());
        //验证delService方法被调用过
        Mockito.verify(serviceService).delService(anyLong());
        //保证这个测试用例中所有被Mock的对象的相关方法都已经被Verify过了
        Mockito.verifyNoMoreInteractions(serviceService);
    }

    /*
    批量删除接口正向用例——传入参数[4,1000]
     */
    @Test
    void delMoreService() throws Exception {
        doNothing().when(serviceService).delMoreServiceById(any());
        String[] ids = new String[]{"4", "1000"};
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/service/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .param("ids", ids))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //解决控制台打印body中文乱码的问题
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        resultActions.andDo(MockMvcResultHandlers.print());
        logger.info(response.getContentAsString());
        Mockito.verify(serviceService).delMoreServiceById(any());
        Mockito.verifyNoMoreInteractions(serviceService);
    }
}