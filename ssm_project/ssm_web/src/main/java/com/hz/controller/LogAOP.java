package com.hz.controller;

import com.hz.domain.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
@SuppressWarnings("all")
public class LogAOP {

    @Autowired
    private HttpServletRequest request;

    private Date visitTime;
    private Class cls;
    private Method method;


    // 前置通知:获取开始时间 执行的类是哪一个 执行的是哪一个方法
    @Before("execution(* com.hz.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        visitTime = new Date();
        cls = joinPoint.getTarget().getClass();
        String name = joinPoint.getSignature().getName();
        // 获取要访问的方法名
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            method = cls.getMethod(name); // 获取无参数方法
        }else{
            // 有参数，就将args中所有元素遍历，获取对应的Class, 装入到一个Class[]
            Class[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classes[i] = args[i].getClass();
            }
            method = cls.getMethod(name, classes); // 获取有参数的方法
        }
    }

    // 后置通知
    @After("execution(* com.hz.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint){
        // 获取访问时长
        long executionTime = new Date().getTime() - visitTime.getTime();
        // 获取访问的URL
        String url = "";
        if(cls != null && method != null && cls != LogAOP.class ){
            RequestMapping clsAnnotation = (RequestMapping) cls.getAnnotation(RequestMapping.class);
            if(clsAnnotation != null){
                String clsURL = clsAnnotation.value()[0];
                // 获取方法上的value值
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if(methodAnnotation != null){
                    String methodURL = methodAnnotation.value()[0];
                    url = clsURL + methodURL;
                }
            }
        }
        // 获取访问的IP地址
        String ip = request.getRemoteAddr();
        // 获取当前操作的用户: 可以通过securityContext获取，也可以从request.getSession中获取
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication();
        String username = user.getUsername();
        // 日志信息封装到SysLog对象中
        SysLog log = new SysLog();
        log.setVisitTime(visitTime);
        log.setUsername(username);
        log.setIp(ip);
        log.setUrl(url);
        log.setExecutionTime(executionTime);
        log.setMethod("[类名]" + cls.getName() + "[方法名]" + method.getName());

    }
}
