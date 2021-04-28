package wang.polyblog.resourcemanager.service;

import wang.polyblog.resourcemanager.entity.Pager;
import wang.polyblog.resourcemanager.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    Optional<Pager<Service>> getAll(String ip, String project, String status, Integer page, Integer size);

    List<Service> exportAll();

    void addService(Service service);

    void delService(Long id);

    void updateService(Long id, Service service);

    void delMoreServiceById(String[] ids);

    Long getMaxId(); //获取service表最大id，在添加service信息的时候需要
}
