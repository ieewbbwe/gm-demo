package com.picher.aop;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by s2s8tb on 2019/8/29.
 */
@Aspect
public class TestAnnoAspect {

    //@Pointcut("execution(public * set*(..))")
    @Pointcut("execution(* com.picher.aop.MainActivity.setContentText(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint point) {
        Log.d("picher", "@Before->>");
        // System.out.println("@Before");
    }

    @Around("pointcut()")
    public void Around(ProceedingJoinPoint point) throws Throwable {
        //System.out.println("@Around");
        Log.d("picher", "@Around 1");
        point.proceed();
        Log.d("picher", "@Around 2");
    }

    @After("pointcut()")
    public void after(JoinPoint point) {
        Log.d("picher", "@After");
        // System.out.println("@After");
    }

    @AfterReturning("pointcut()")
    public void afterReturning(JoinPoint point, Object returnValue) {
        System.out.println("@AfterReturning");
    }

    @AfterThrowing(value = "pointcut()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        System.out.println("@afterThrowing");
        System.out.println("ex = " + ex.getMessage());
    }

    @Before("execution(@com.picher.aop.TestAnnotation * *(..))")
    public void annotation() {
        System.out.println("@Before annotation");
    }
}
