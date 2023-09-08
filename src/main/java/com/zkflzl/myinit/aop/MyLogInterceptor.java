package com.zkflzl.myinit.aop;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志打印 AOP
 *
 * @author <a href="https://gitee.com/zkflzl">zkflzl</a>
 */
@Aspect
@Component
@Slf4j
public class MyLogInterceptor {
    @Pointcut("@annotation(com.zkflzl.myinit.annotation.MyLog)")
    public void myLogInterceptor() {
    }

    /**
     * 日志打印
     *
     * @param joinPoint 连接点
     */
    @Before("myLogInterceptor()")
    public void beforeMyLogInterceptor(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String methodName = joinPoint.getSignature().getName();

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        // 打印方法名称
        out.println("========================== " + methodName + " ==========================");

        // 打印调用时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        out.println("接口请求时间       ：" + time);

        // 打印请求的 url
        out.println("请求URL           ：" + request.getRequestURL());

        // 打印请求方法
        out.println("HTTP Method      ：" + request.getMethod());

        // 打印请求 controller 的全路径以及请求方法
        out.println("Class Method     ：" + joinPoint.getSignature().getDeclaringTypeName() + "." + methodName);

        // 打印请求的 ip
        out.println("IP               ：" + request.getRemoteHost());

        // 打印请求入参
        out.println("Request Args     ：" + JSONUtil.toJsonStr(joinPoint.getArgs()));

        out.println("===============================================================");

        // 关闭输出流
        out.flush();
    }
}

