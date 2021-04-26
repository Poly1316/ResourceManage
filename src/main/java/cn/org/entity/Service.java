package cn.org.entity;

import java.io.Serializable;

public class Service implements Serializable {
    private Long id;

    @ExcelColumn(value="IP地址", col = 1)
    private String ip;

    @ExcelColumn(value = "所属项目", col = 2)
    private String project;

    @ExcelColumn(value = "状态", col = 3)
    private String status;

    @ExcelColumn(value = "用户名", col = 4)
    private String username;

    @ExcelColumn(value = "密码", col = 5)
    private String password;

    @ExcelColumn(value = "角色", col = 6)
    private String role;

    @ExcelColumn(value = "系统版本", col = 7)
    private String system_version;

    @ExcelColumn(value = "CPU", col = 8)
    private String cpu;

    @ExcelColumn(value = "内存大小", col = 9)
    private String memory;

    @ExcelColumn(value = "磁盘情况", col = 10)
    private String disk;

    private String remark;

    public Service(Long id, String ip, String project, String status, String username, String password, String role,
                   String system_version, String cpu, String memory, String disk, String remark) {
        this.id = id;
        this.ip = ip;
        this.project = project;
        this.status = status;
        this.username = username;
        this.password = password;
        this.role = role;
        this.system_version = system_version;
        this.cpu = cpu;
        this.memory = memory;
        this.disk = disk;
        this.remark = remark;
    }

    public Service(String project) {
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Service() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSystem_version() {
        return system_version;
    }

    public void setSystem_version(String system_version) {
        this.system_version = system_version;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString(){
        return "id:" + id + ";ip:" + ip + ";project:" + project + ";status:" + status + ";username:" + username +
                ";password:" + password + ";role:" + role + ";system_version:" + system_version + ";cpu:" + cpu +
                ";memory:" + memory + ";disk:" + disk + ";remark:" + remark;
    }
}
