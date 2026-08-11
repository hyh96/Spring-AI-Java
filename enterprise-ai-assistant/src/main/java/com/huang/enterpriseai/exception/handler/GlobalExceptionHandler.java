package com.huang.enterpriseai.exception.handler;

import com.huang.enterpriseai.enums.ResponseCodeEnum;
import com.huang.enterpriseai.vo.ResultVo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/10 16:39
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 数据重复
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResultVo<Void> handleDuplicateKey(DuplicateKeyException e) {
        return ResultVo.failed(ResponseCodeEnum.RESP_409.code, "数据已存在，请勿重复添加");
    }

    /**
     * @Valid 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVo handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse(ResponseCodeEnum.RESP_400.message);

        return ResultVo.failed(ResponseCodeEnum.RESP_400.code, message) ;
    }

    /**
     * 未知系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVo<Void> handleException(Exception e) {
        return ResultVo.failed(ResponseCodeEnum.RESP_500.code, ResponseCodeEnum.RESP_500.message);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultVo<Void> handleNoSuchElement(
            NoSuchElementException e) {

        return ResultVo.failed(
                ResponseCodeEnum.RESP_404.code,
                e.getMessage()
        );
    }

}
