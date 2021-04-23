package cn.org.service;

import cn.org.entity.Service;
import cn.org.utils.RemoteCommandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import static cn.org.utils.RemoteCommandUtil.execute;
import static cn.org.utils.RemoteCommandUtil.login;

@Component
@EnableScheduling
public class autoUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(autoUpdateService.class);

    static String url = "jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&serverTimezone=UTC";

    static String username = "root";

    static String password = "abc@123";

    /**
     * 连接数据库，查询服务器IP、username、password，并存入List
     * @return 返回服务器连接信息List
     * @throws SQLException 捕获SQL执行异常
     */
    public static List<Service> getServiceList() throws SQLException {
        List<Service> list = new ArrayList<Service>();
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT ip,username,password FROM service");

        while (rs.next()) {
            Service serviceList = new Service();

            serviceList.setIp(rs.getString("ip"));
            serviceList.setUsername(rs.getString("username"));
            serviceList.setPassword(rs.getString("password"));
            list.add(serviceList);
        }

        rs.close();
        st.close();
        conn.close();
        return list;
    }

    /**
     * 连接服务器并执行shell脚本，分别获取磁盘大小、CPU型号、内存大小、操作系统版本等信息，并存入HashMap中
     * @param ip 服务器IP，通过数据库服务器信息获取
     * @param username 服务器用户名，通过数据库服务器信息username字段获取
     * @param password 服务器密码，通过数据库服务器信息password字段获取
     * @return 返回服务器磁盘、CPU、内存、操作系统信息HashMap数据
     */
    public static HashMap<String, String> getServiceInfo(String ip, String username, String password) {
        ch.ethz.ssh2.Connection conn = login(ip, username, password);
        //获取磁盘大小shell命令
        String disk = execute(conn, "fdisk -l | grep 'Disk' | awk -F, '{print $1}' | sed -e '/identifier/d' | awk '{print $2 $3 $4}'");
        //获取cpu型号shell命令
        String cpu = execute(conn, "cat /proc/cpuinfo | grep name | cut -f2 -d: | awk 'NR==1'");
        //获取内存大小
        String memory = execute(conn, "free -h | tr [:blank:] \\\\\\n | grep [0-9] | sed -n '1p'");
        //获取操作系统版本
        String system_version = execute(conn, "cat /etc/issue | awk 'NR==1'");

        HashMap<String, String> serviceInfo = new HashMap<String, String>();
        serviceInfo.put("disk", disk.trim());
        serviceInfo.put("cpu", cpu.trim());
        serviceInfo.put("memory", memory.trim());
        serviceInfo.put("system_version", system_version.trim());
        conn.close();
        return serviceInfo;
    }

    @Scheduled(cron = "0 15 0 * * ?")
    public static void runUpdateService() throws SQLException {
        List<?> rs = getServiceList();
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement st = conn.createStatement();
        for (Service result : (Iterable<Service>) rs) {
            try {
                HashMap<String, String> serviceInfo = getServiceInfo(result.getIp(), result.getUsername(), result.getPassword());
                String sql = String.format("UPDATE service SET cpu='%s',disk='%s',memory='%s',system_version='%s' WHERE ip='%s'",
                        serviceInfo.get("cpu"), serviceInfo.get("disk"), serviceInfo.get("memory"),
                        serviceInfo.get("system_version"), result.getIp());
                logger.info("更新服务器配置信息的SQL语句为：" + sql);
                st.executeUpdate(sql);
            } catch (IllegalStateException e) {
                String remark = e.getMessage();
                String status = "3";
                String sql = String.format("UPDATE service SET remark='%s', status='%s' WHERE ip='%s'",
                        remark, status, result.getIp());
                st.executeUpdate(sql);
                logger.error("服务器" + result.getIp() + "登录失败，请检查服务器IP、用户名或密码！" + e.getMessage());
                e.printStackTrace();
            }
        }
        conn.close();
    }
}
