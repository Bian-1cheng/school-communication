package com.commucation.demo.common.config.exception;

import com.commucation.demo.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("运行时异常---------->[{}]", e);
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handler(ShiroException e) {
        log.error("运行时异常---------->[{}]", e);
        return Result.fail(401, e.getMessage(), null);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Result handle401() {
        return Result.fail(401, "Unauthorized", null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnavailableSecurityManagerException.class)
    public Result handleUnLogin() {
        return Result.fail(500, "无用户信息", null);
    }

    // 实体校验异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        log.error("实体校验异常---------- {}", e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        log.error("Assert异常---------- ", e);
        return Result.fail(e.getMessage());
    }

//     捕捉其他所有异常
//     @ExceptionHandler(Exception.class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     public Result globalException(HttpServletRequest request, Throwable ex) {
//         return Result.fail(getStatus(request).value(), ex.getMessage(), null);
//     }
//
//     private HttpStatus getStatus(HttpServletRequest request) {
//         Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//         if (statusCode == null) {
//             return HttpStatus.INTERNAL_SERVER_ERROR;
//         }
//         return HttpStatus.valueOf(statusCode);
//     }
}