package com.pro.www.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R<T>{
    private Integer code; // 1 为成功
    private String msg;
    private T data;
    private Map map = new HashMap(); // 动态数据

    public R() {

    }


    public static <T> R<T> success(T object){
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }
    public static <T> R<T> error(String msg){
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }
    public R<T> add(String key,Object value){
        this.map.put(key,value);
        return this;
    }


}