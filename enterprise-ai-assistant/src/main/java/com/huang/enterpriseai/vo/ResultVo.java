package com.huang.enterpriseai.vo;


import com.huang.enterpriseai.enums.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public static <T> ResultVo<T> success() {
        return new ResultVo<>(
                ResponseCodeEnum.RESP_200.code,
                ResponseCodeEnum.RESP_200.message,
                null
        );
    }

    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(
                ResponseCodeEnum.RESP_200.code,
                ResponseCodeEnum.RESP_200.message,
                data
        );
    }

    public static <T> ResultVo<T> success(String msg, T data) {
        return new ResultVo<>(
                ResponseCodeEnum.RESP_200.code,
                msg,
                data
        );
    }

    public static <T> ResultVo<T> failed() {
        return new ResultVo<>(
                ResponseCodeEnum.RESP_500.code,
                ResponseCodeEnum.RESP_500.message,
                null
        );
    }

    public static <T> ResultVo<T> failed(String msg) {
        return new ResultVo<>(
                ResponseCodeEnum.RESP_500.code,
                msg,
                null
        );
    }

    public static <T> ResultVo<T> failed(int code, String msg) {
        return new ResultVo<>(
                code,
                msg,
                null
        );
    }

    public static <T> ResultVo<T> failed(ResponseCodeEnum responseCode) {
        return new ResultVo<>(
                responseCode.code,
                responseCode.message,
                null
        );
    }

    public static <T> ResultVo<T> failed(
            ResponseCodeEnum responseCode,
            String msg) {

        return new ResultVo<>(
                responseCode.code,
                msg,
                null
        );
    }
}