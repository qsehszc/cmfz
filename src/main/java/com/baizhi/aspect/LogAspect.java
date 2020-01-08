package com.baizhi.aspect;

import com.baizhi.annotation.LogAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
@Aspect
public class LogAspect {
    @Autowired
    HttpSession session;
    @Around(value = "@annotation(com.baizhi.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        // 时间
        Date date = new Date();
        System.out.println(date);
        // 人物
        String admin = (String) session.getAttribute("admin");
        System.out.println(admin);
        // 事件
        // 获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        System.out.println(name);
        // 获取自定义注解中的值
        // Signature 中保存了该类该方法所有的信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        System.out.println(annotation.value());
        try {
            // proceed 表示该方法执行结果
            Object proceed = proceedingJoinPoint.proceed();
            System.out.println("success");
            return proceed;
        } catch (Throwable throwable) {
            System.out.println("error");
            throwable.printStackTrace();
        }
        return null;
    }
}
