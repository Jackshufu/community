package com.community.advice;

import com.alibaba.fastjson.JSON;
import com.community.dto.ResultDTO;
import com.community.exception.CustomErrorCodeEnumImp;
import com.community.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 舒先亮 on 2019/9/6.   异常处理器
 *
 * @ControllerAdvice ,
 */
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Throwable ex, Model model) {
        HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
//            返回application/json格式数据
            if (ex instanceof CustomException) {
                resultDTO =  ResultDTO.errorOf((CustomException) ex);
            } else {
                resultDTO =  ResultDTO.errorOf(CustomErrorCodeEnumImp.SYSTEM_ERROR);
            }
            try {
//                处理字符集
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
//                关闭流
                writer.close();
            } catch (IOException ioe) {
            }

        } else {
//            返回text/html格式数据
            if (ex instanceof CustomException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomErrorCodeEnumImp.SYSTEM_ERROR.getMessage());
            }
        }

        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
//        去request拿到返回错误的状态码
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
