package com.mark.vo;

import lombok.Data;

@Data
public class ResultVo<T> {
    private Integer code;
    private String desc;
    private T data;

    public static<T> ResultVo<T> setSuccess(String desc, T data) {
        ResultVo<T> resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setData(data);
        resultVo.setDesc(desc);
        return resultVo;
    }

    public static<T> ResultVo<T> setSuccess(T data) {
        ResultVo<T> resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setData(data);
        return resultVo;
    }

    public static<T> ResultVo<T> setError(String desc) {
        ResultVo<T> resultVo = new ResultVo<>();
        resultVo.setCode(-1);
        resultVo.setDesc(desc);
        return resultVo;
    }
}
