package com.luke.springproject.dto;

/**
 * @ClassName Respstat
 * @Description TODO
 * @Author lulu
 * @Date 2020/11/23 17:15
 * @Version 1.0
 **/
public class Respstat {

    public Respstat() {
        super();
    }

    public Respstat(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    int code;
    String msg;
    String data;

    public static Respstat build(int i, String msg) {
        return new Respstat(i, msg, "meiyou");
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public static  Respstat build(int i){
        return new Respstat(i, "success", "meiyou");
    }
}
