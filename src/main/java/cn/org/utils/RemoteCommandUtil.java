package cn.org.utils;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import cn.org.entity.Service;
import cn.org.service.ServiceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.ethz.ssh2.Connection;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

public class RemoteCommandUtil {

    @Resource
    private ServiceService serviceService;
    private static final Logger logger = LoggerFactory.getLogger(RemoteCommandUtil.class);

    /**
     * 登录主机
     * @param ip ip地址
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回true，否则返回false
     */
    public static Connection login(String ip, String username, String password) {
        boolean flag = false;
        Connection conn = null;
        try {
            conn = new Connection(ip);
            conn.connect(); // 连接
            flag = conn.authenticateWithPassword(username, password); // 认证
            if (flag) {
                logger.info("=================登录成功====================" + conn);
                return conn;
            }
        } catch (IOException | IllegalStateException e) {
            logger.error("=================登录失败====================" + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 远程执行shell脚本或者命令的
     * @param conn 链接
     * @param cmd 即将执行的命令
     * @return 命令执行完成后返回的结果值
     */
    public static String execute(Connection conn, String cmd) {
        String result = "";
        try {
            if (conn != null) {
                Session session = conn.openSession(); //打开一个会话
                session.execCommand(cmd);   //执行命令
                String DEFAULT_CHART = "UTF-8";
                result = processStdout(session.getStdout(), DEFAULT_CHART);
                //如果未得到标准输出为空，说明脚本执行出错了
                if (StringUtils.isBlank(result)) {
                    logger.info("得到标准输出为空，链接conn：" + conn + "，执行的命令：" + cmd);
                    result = processStdout(session.getStderr(), DEFAULT_CHART);
                } else {
                    logger.info("执行命令成功，链接conn：" + conn + "，执行的命令：" + cmd);
                }
//                conn.close();
                session.close();
            }
        } catch (IOException e) {
            logger.info("执行命令失败，链接conn：" + conn + "，执行的命令：" + cmd + " " + e.getMessage());
        }
        return result;
    }

    /**
     * 解析脚本执行返回的结果集
     * @param in 输入流对象
     * @param charset 编码
     * @return 以纯文本的格式返回
     */
    private static String processStdout(InputStream in, String charset) {
        InputStream stdout = new StreamGobbler(in);
        StringBuilder buffer = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (IOException e) {
            logger.error("解析脚本出错：" + e.getMessage());
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
