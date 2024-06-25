package com.finforz.logger.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

   // private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper mapper;
    @Before("execution(* com.finforz.ems_backend..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Method execution started: {}", joinPoint.getSignature());
    }

    @After("execution(* com.finforz.ems_backend..*.*(..)) ")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Method execution completed: {}", joinPoint.getSignature());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping mapping = signature.getMethod().getAnnotation(RequestMapping.class);
//
        Map<String, Object> parameters = getParameters(joinPoint);
        log.info("parameters"+parameters);
//        try {
//            logger.info("==> path(s): {}, method(s): {}, arguments: {} ",
//                    mapping.path(), mapping.method(), mapper.writeValueAsString(parameters));
//        } catch (JsonProcessingException e) {
//            logger.error("Error while converting", e);
//        }
    }
    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        HashMap<String, Object> map = new HashMap<>();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }

        return map;
    }
}
