package com.twotwo.interceptor;

import com.twotwo.common.R;
import com.twotwo.entity.Log;
import com.twotwo.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 异常全局捕捉
 */
@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    @Resource
    private LogService logService;

    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandle(HttpServletRequest request, Exception e) {
        log.error("异常", e);
        Log log = new Log();
        log.setMethod(request.getMethod());
        log.setName(request.getRequestURI());
        log.setStack(e.toString());
        logService.save(log);
        if (e instanceof DataIntegrityViolationException) {
            String msg = e.toString();
            Pattern pattern = Pattern.compile("Field '(\\w+)' doesn't have a default value");
            Matcher matcher = pattern.matcher(msg);
            // 查找匹配项
            if (matcher.find()) {
                return R.error("缺少必要参数: " + matcher.group(1));
            }
        }
        if (e instanceof IllegalArgumentException) {
            return R.error(e.getMessage());
        }
        return R.error("服务器出现异常");
    }

}