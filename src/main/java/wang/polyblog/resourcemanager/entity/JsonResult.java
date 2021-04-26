package wang.polyblog.resourcemanager.entity;

public class JsonResult<T> {

    public static final int SUCCESS=200;
    public static final int ERROR=201;

    private T data;
    private int code;
    private String msg;

    /**
     * 若没有数据返回，默认状态码为0，提示信心为：操作成功！
     */
    public JsonResult() {
        this.code = SUCCESS;
        this.msg = "操作成功!";
    }

    /**
     * 若没有数据返回，可以人为制定状态码和提示信息
     * @param code
     * @param msg
     */
    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为0，默认提示信息为：操作成功！
     * @param data
     */
    public JsonResult(T data) {
        this.data = data;
        this.code = SUCCESS;
        this.msg = "操作成功！";
    }

    /**
     * 有数据返回，状态码为0，人为指定提示信息
     * @param data
     * @param msg
     */
    public JsonResult(T data, String msg) {
        this.data = data;
        this.code = SUCCESS;
        this.msg = msg;
    }

    public JsonResult(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /*
    重写实体类toString()方法，方便输出到日志
     */
    @Override
    public String toString() {
        return "JsonResult [code=" + this.code + ", msg=" + this.msg + ", data=" + this.data.toString() + "]";
    }
}
