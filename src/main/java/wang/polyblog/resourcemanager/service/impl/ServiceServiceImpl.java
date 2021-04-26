package wang.polyblog.resourcemanager.service.impl;

import wang.polyblog.resourcemanager.dao.ServiceMapper;
import wang.polyblog.resourcemanager.entity.Pager;
import wang.polyblog.resourcemanager.entity.Service;
import wang.polyblog.resourcemanager.service.ServiceService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Resource
    private ServiceMapper serviceMapper;

    @Override
    public List<Service> exportAll() {
        return serviceMapper.exportAll();
    }

    @Override
    public Optional<Pager<Service>> getAll(String ip, String project, String status, Integer page, Integer size) {
        Long total = serviceMapper.getTotal(ip, project, status);   // 查询总记录数

        Optional<Pager<Service>> optionalPager = Optional.empty();  // 根据total判断查询结果

        if (total > 0) {
            // 封装分页数据
            Pager<Service> pager = new Pager<>();
            int total_page = 0;

            // 默认总页数为10
            if (size == null){
                size = 10;
            }

            List<Service> services = serviceMapper.getAll(ip, project, status, (page - 1) * size, size);

            pager.setPage(page);
            pager.setSize(size);
            pager.setTotal(total);
            pager.setList(services);

            optionalPager = Optional.of(pager);
        }

        return optionalPager;
    }

    @Override
    public Long getMaxId() {
        return serviceMapper.getMaxId();
    }

    @Override
    public void addService(Service service) {
        serviceMapper.addService(service);
    }

    @Override
    public void delService(Long id) {
        serviceMapper.delService(id);
    }

    @Override
    public void updateService(Long id, Service service) {
        service.setId(id);
        serviceMapper.updateService(service);
    };

    @Override
    public void delMoreServiceById(Integer... ids) {
        //判断参数是否合法
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException("请至少选择一条数据");
        }

        //执行删除操作
        serviceMapper.delMoreServiceById(ids);
    }
}
