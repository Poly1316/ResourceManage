package cn.org.dao;

import cn.org.entity.Service;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ServiceMapper {

    List<Service> exportAll();

    List<Service> getAll(@Param("ip") String ip,
                         @Param("project") String project,
                         @Param("status") String status,
                         @Param("page") Integer page,
                         @Param("size") Integer size);

    /**
     * 查询符合条件的总记录数
     * @param ip: ip地址
     * @param project: 项目名称
     * @param status: 服务器状态
     * @return 总记录数
     */
    Long getTotal(@Param("ip") String ip,
                  @Param("project") String project,
                  @Param("status") String status);

    Long getMaxId();

    void addService(Service service);

    void delService(Long id);

    void delMoreServiceById(@Param("ids")Integer... ids);   //...表示是可变个数参数

    void updateService(Service service);
}
