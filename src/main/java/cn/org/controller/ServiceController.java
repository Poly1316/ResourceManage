package cn.org.controller;

import cn.org.entity.JsonResult;
import cn.org.entity.Pager;
import cn.org.entity.Service;
import cn.org.service.ServiceService;
import cn.org.utils.ExcelUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("service")
public class ServiceController {

    @Resource
    private ServiceService serviceService;

    private final static Logger logger = LoggerFactory.getLogger(ServiceController.class);

    /**
     * 获取服务器列表
     * @return
     */
    @RequestMapping("/list")
    public JsonResult<?> getAllService(@Param("ip") String ip,
                                       @Param("project") String project,
                                       @Param("status") String status,
                                       @Param("page") Integer page,
                                       @Param("size") Integer size) {
        JsonResult jsonResult = new JsonResult<>();

        Optional<Pager<Service>> optionalPager = serviceService.getAll(ip, project, status, page, size);

        if (optionalPager.isPresent()) {
            jsonResult.setCode("0");
            jsonResult.setData(optionalPager.get());
        }
        logger.info("后台查询返回结果为：" + jsonResult);
        return jsonResult;
    }


    /**
     * 新增服务器方法
     * @param service 实体列表
     * @return 返回添加成功信息
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public JsonResult<Service> addService(@RequestBody Service service) {
        logger.info("服务器新增接口/service/add接收到传入的参数为：" + service);
        Long id = serviceService.getMaxId();
        logger.info("本次新增生成的ID为：" + id);
        service.setId(id);
        serviceService.addService(service);
        return new JsonResult();
    }

    /**
     * 修改服务器信息方法
     * @param id
     * @param service
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = {RequestMethod.PUT})
    public JsonResult<Service> updateService(@PathVariable("id") Long id, @RequestBody Service service) {
        serviceService.updateService(id, service);
        return new JsonResult();
    }

    /**
     * 单个服务器删除方法
     * @param id: 单个服务器id
     * @return 返回操作成功或失败的json串
     */
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE})
    public JsonResult delService(@PathVariable("id") Long id) {
        JsonResult jsonResult = new JsonResult<>();
        logger.info("待删除的服务器id为：" + id);
        serviceService.delService(id);
        jsonResult.setCode("0");
        jsonResult.setMsg("删除成功！");
        return jsonResult;
    }

    /**
     * 批量删除方法
     * @param ids:待删除的服务器id列表
     * @return 返回删除成功后失败信息
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public JsonResult delMoreService(Integer... ids) {
        JsonResult jsonResult = new JsonResult<>();
        logger.info("待删除的服务器id为：" + Arrays.toString(ids));
        if (ids != null && ids.length !=0) {
            serviceService.delMoreServiceById(ids);
            jsonResult.setCode("0");
            jsonResult.setMsg("删除成功！");
        } else {
            jsonResult.setCode("1");
            jsonResult.setMsg("删除失败！");
        }

        return jsonResult;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportServiceExcel(HttpServletResponse response) {
        List<Service> serviceList = serviceService.exportAll();
        List<Service> resultList = new ArrayList<Service>();
        String[] statusList = new String[] {
                "全部", "在用", "空闲", "故障", "已预订，待安装"
        };
        for (Service value : serviceList) {
            Service service = new Service();
            service.setIp(value.getIp());
            service.setProject(value.getProject());
            service.setStatus(statusList[Integer.parseInt(value.getStatus())]);
            service.setUsername(value.getUsername());
            service.setPassword(value.getPassword());
            service.setRole(value.getRole());
            service.setCpu(value.getCpu());
            service.setMemory(value.getMemory());
            service.setDisk(value.getDisk());
            resultList.add(service);
        }
        String filename = "服务器信息.xlsx";
        long t1 = System.currentTimeMillis();
        ExcelUtils.writeExcel(response, resultList, Service.class, filename);
        long t2 = System.currentTimeMillis();
        logger.info(String.format("export over! cost:%sms", (t2 - t1)));
    }
}
