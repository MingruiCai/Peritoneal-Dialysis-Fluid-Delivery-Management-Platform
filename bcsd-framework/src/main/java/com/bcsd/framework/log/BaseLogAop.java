package com.bcsd.framework.log;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.bcsd.common.constant.Constants;
import com.bcsd.common.constant.HttpStatus;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.common.utils.DateUtils;
import com.bcsd.common.utils.ip.IpUtils;
import com.bcsd.common.utils.uuid.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.fusesource.jansi.Ansi;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Controller Aop 抽象类
 * 获取响应结果信息
 * <p>
 * 日志输出类型：print-type
 * 1. 请求和响应分开，按照执行顺序打印
 * 2. ThreadLocal线程绑定，方法执行结束时，连续打印请求和响应日志
 * 3. ThreadLocal线程绑定，方法执行结束时，同时打印请求和响应日志
 * </p>
 */
@Slf4j
public abstract class BaseLogAop {

    /**
     * 本地线程变量，保存请求参数信息到当前线程中
     */
    protected static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    protected static ThreadLocal<RequestInfo> requestInfoThreadLocal = new ThreadLocal<>();

    /**
     * 请求ID
     */
    private static final String REQUEST_ID = "requestId";
    /**
     * 零
     */
    private static final int ZERO = 0;
    /**
     * 截取字符串的最多长度
     */
    private static final int MAX_LENGTH = 300;
    /**
     * 登录日志：登录类型
     */
    private static final int LOGIN_TYPE = 1;
    /**
     * 登录日志：登出类型
     */
    private static final int LOGOUT_TYPE = 2;

    /**
     * 项目上下文路径
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * Aop日志配置
     */
    protected LogAopProperties.LogAopConfig logAopConfig;

    /**
     * 日志打印类型
     */
    protected LogAopProperties.LogPrintType logPrintType;

    /**
     * 是否启用请求ID
     */
    protected boolean enableRequestId;

    /**
     * requestId生成类型
     */
    protected LogAopProperties.RequestIdType requestIdType;


    @Autowired
    public void setSpringBootPlusAopProperties(LogAopProperties logAopProperties) {
        logAopConfig = logAopProperties.getLog();
        logPrintType = logAopConfig.getLogPrintType();
        enableRequestId = logAopConfig.isEnableRequestId();
        requestIdType = logAopConfig.getRequestIdType();
        log.debug("logAopConfig = " + logAopConfig);
        log.debug("logPrintType = " + logPrintType);
        log.debug("enableRequestId = " + enableRequestId);
        log.debug("requestIdType = " + requestIdType);
        log.debug("contextPath = " + contextPath);
    }

    /**
     * 环绕通知
     * 方法执行前打印请求参数信息
     * 方法执行后打印响应结果信息
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public abstract Object doAround(ProceedingJoinPoint joinPoint) throws Throwable;

    /**
     * 异常通知方法
     *
     * @param joinPoint
     * @param exception
     */
    public abstract void afterThrowing(JoinPoint joinPoint, Exception exception);

    /**
     * 设置请求ID
     *
     * @param requestInfo
     */
    protected abstract void setRequestId(RequestInfo requestInfo);

    /**
     * 获取请求信息对象
     *
     * @param requestInfo
     */
    protected abstract void getRequestInfo(RequestInfo requestInfo);

    /**
     * 获取响应结果对象
     * @param result
     */
    protected abstract void getResponseResult(Object result);

    /**
     * 处理
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求相关信息
        try {
            // 获取当前的HttpServletRequest对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // HTTP请求信息对象
            RequestInfo requestInfo = new RequestInfo();

            // 请求路径 /api/foobar/add
            String path = request.getRequestURI();
            requestInfo.setPath(path);
            // 获取实际路径 /foobar/add
            String realPath = getRealPath(path);
            requestInfo.setRealPath(realPath);

            // 排除路径
            Set<String> excludePaths = logAopConfig.getExcludePaths();
            // 请求路径
            if (handleExcludePaths(excludePaths, realPath)) {
                return joinPoint.proceed();
            }

            // 获取请求类名和方法名称
            Signature signature = joinPoint.getSignature();

            // 获取真实的方法对象
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            // IP地址
            String ip = IpUtils.getIpAddr();
            requestInfo.setIp(ip);

            // 获取请求方式
            String requestMethod = request.getMethod();
            requestInfo.setRequestMethod(requestMethod);

            // 获取请求内容类型
            String contentType = request.getContentType();
            requestInfo.setContentType(contentType);

            // 判断控制器方法参数中是否有RequestBody注解
            Annotation[][] annotations = method.getParameterAnnotations();
            boolean isRequestBody = isRequestBody(annotations);
            requestInfo.setRequestBody(isRequestBody);

            AnnotatedType[] annotatedTypes = method.getAnnotatedParameterTypes();

            // 设置请求参数
            Object requestParamObject = getRequestParamObject(joinPoint, request, isRequestBody);
            requestInfo.setParam(requestParamObject);
            requestInfo.setTime(DateUtils.getTime());

            // 用户浏览器代理字符串
            requestInfo.setUserAgent(request.getHeader(Constants.USER_AGENT));

            // 记录请求ID
            setRequestId(requestInfo);

            // 调用子类重写方法，控制请求信息日志处理
            getRequestInfo(requestInfo);
        } catch (Exception e) {
            log.error("请求日志AOP处理异常", e);
        }

        // 执行目标方法,获得返回值
        // 方法异常时，会调用子类的@AfterThrowing注解的方法，不会调用下面的代码，异常单独处理
        Object result = joinPoint.proceed();
        try {
            // 调用子类重写方法，控制响应结果日志处理
            getResponseResult(result);
        } catch (Exception e) {
            log.error("处理响应结果异常", e);
        } finally {
            remove();
        }
        return result;
    }

    /**
     * 处理请求ID
     * @param requestInfo
     */
    protected void handleRequestId(RequestInfo requestInfo) {
        if (!enableRequestId) {
            return;
        }
        String requestId = null;
        if (LogAopProperties.RequestIdType.IDWORK == requestIdType) {
            requestId = IdWorker.getIdStr();
        } else if (LogAopProperties.RequestIdType.UUID == requestIdType) {
            requestId = IdUtils.fastSimpleUUID();
        }
        // 设置请求ID
        MDC.put(REQUEST_ID, requestId);
        requestInfo.setRequestId(requestId);
    }

    /**
     * 处理请求参数
     *
     * @param requestInfo
     */
    protected void handleRequestInfo(RequestInfo requestInfo) {
        requestInfoThreadLocal.set(requestInfo);
        if (LogAopProperties.LogPrintType.NONE == logPrintType) {
            return;
        }
        // 获取请求信息
        String requestInfoString = formatRequestInfo(requestInfo);
        // 如果打印方式为顺序打印，则直接打印，否则，保存的threadLocal中
        if (LogAopProperties.LogPrintType.ORDER == logPrintType) {
            printRequestInfoString(requestInfoString);
        } else {
            threadLocal.set(requestInfoString);
        }
    }

    /**
     * 处理响应结果
     *
     * @param result
     */
    protected void handleResponseResult(Object result) {
        if (LogAopProperties.LogPrintType.NONE == logPrintType) {
            return;
        }
        if (result != null){
            int code = 0;
            String responseResultString = null;
            if (result instanceof AjaxResult) {
                AjaxResult apiResult = (AjaxResult) result;
                code = Integer.parseInt(apiResult.get(AjaxResult.CODE_TAG).toString());
                // 获取格式化后的响应结果
                responseResultString = formatResponseResult(apiResult);
            }else if (result instanceof TableDataInfo){
                TableDataInfo apiResult = (TableDataInfo) result;
                code = apiResult.getCode();
                // 获取格式化后的响应结果
                responseResultString = formatResponseResult(apiResult);
            }else{
                return;
            }
            if (LogAopProperties.LogPrintType.ORDER == logPrintType) {
                printResponseResult(code, responseResultString);
            } else {
                // 从threadLocal中获取线程请求信息
                String requestInfoString = threadLocal.get();
                // 如果是连续打印，则先打印请求参数，再打印响应结果
                if (LogAopProperties.LogPrintType.LINE == logPrintType) {
                    printRequestInfoString(requestInfoString);
                    printResponseResult(code, responseResultString);
                } else if (LogAopProperties.LogPrintType.MERGE == logPrintType) {
                    printRequestResponseString(code, requestInfoString, responseResultString);
                }
            }
        }

    }

    /**
     * 同时打印请求和响应信息
     *
     * @param code
     * @param requestInfoString
     * @param responseResultString
     */
    protected void printRequestResponseString(int code, String requestInfoString, String responseResultString) {
        if (code == HttpStatus.SUCCESS) {
            log.info(requestInfoString + "\n" + responseResultString);
        } else {
            log.error(requestInfoString + "\n" + responseResultString);
        }
    }


    /**
     * 格式化请求信息
     *
     * @param requestInfo
     * @return
     */
    protected String formatRequestInfo(RequestInfo requestInfo) {
        String requestInfoString = "requestInfo:";
        try {
            if (logAopConfig.isRequestLogFormat()) {
                requestInfoString += "\n" + Jackson.toJsonStringNonNull(requestInfo, true);
                requestInfoString = AnsiUtil.getAnsi(Ansi.Color.GREEN,  requestInfoString);
            } else {
                requestInfoString = Jackson.toJsonStringNonNull(requestInfo);
            }
        } catch (Exception e) {
            log.error("格式化请求信息日志异常", e);
        }
        return requestInfoString;
    }

    /**
     * 打印请求信息
     *
     * @param requestInfoString
     */
    protected void printRequestInfoString(String requestInfoString) {
        log.info(requestInfoString);
    }

    /**
     * 格式化响应信息
     * @param apiResult
     * @return
     */
    protected String formatResponseResult(AjaxResult apiResult) {
        String responseResultString = "responseResult:";
        try {

            if (logAopConfig.isResponseLogFormat()) {
                responseResultString += "\n" + Jackson.toJsonString(apiResult, true);
                if (apiResult.isSuccess()) {
                    return AnsiUtil.getAnsi(Ansi.Color.BLUE, responseResultString);
                } else {
                    return AnsiUtil.getAnsi(Ansi.Color.RED, responseResultString);
                }
            } else {
                responseResultString += Jackson.toJsonString(apiResult);
            }
        } catch (Exception e) {
            log.error("格式化响应日志异常", e);
        }
        return responseResultString;
    }

    /**
     * 格式化响应信息
     * @param apiResult
     * @return
     */
    protected String formatResponseResult(TableDataInfo apiResult) {
        String responseResultString = "responseResult:";
        try {
            if (logAopConfig.isResponseLogFormat()) {
                responseResultString += "\n" + Jackson.toJsonString(apiResult, true);
                if (apiResult.getCode()==HttpStatus.SUCCESS) {
                    return AnsiUtil.getAnsi(Ansi.Color.BLUE, responseResultString);
                } else {
                    return AnsiUtil.getAnsi(Ansi.Color.RED, responseResultString);
                }
            } else {
                responseResultString += Jackson.toJsonString(apiResult);
            }
        } catch (Exception e) {
            log.error("格式化响应日志异常", e);
        }
        return responseResultString;
    }

    /**
     * 打印响应信息
     *
     * @param code
     * @param responseResultString
     */
    protected void printResponseResult(int code, String responseResultString) {
        if (code == HttpStatus.SUCCESS) {
            log.info(responseResultString);
        } else {
            log.error(responseResultString);
        }
    }

    /**
     * 获取请求参数JSON字符串
     *
     * @param joinPoint
     * @param request
     * @param isRequestBody
     */
    protected Object getRequestParamObject(ProceedingJoinPoint joinPoint, HttpServletRequest request, boolean isRequestBody) {
        Object paramObject = null;
        if (isRequestBody) {
            // POST,application/json,RequestBody的类型,简单判断,然后序列化成JSON字符串
            Object[] args = joinPoint.getArgs();
            paramObject = getArgsObject(args);
        } else {
            // 获取getParameterMap中所有的值,处理后序列化成JSON字符串
            Map<String, String[]> paramsMap = request.getParameterMap();
            paramObject = getParamJSONObject(paramsMap);
        }
        return paramObject;
    }

    /**
     * 判断控制器方法参数中是否有RequestBody注解
     *
     * @param annotations
     * @return
     */
    protected boolean isRequestBody(Annotation[][] annotations) {
        boolean isRequestBody = false;
        for (Annotation[] annotationArray : annotations) {
            for (Annotation annotation : annotationArray) {
                if (annotation instanceof RequestBody) {
                    isRequestBody = true;
                }
            }
        }
        return isRequestBody;
    }

    /**
     * 请求参数拼装
     * @param args
     * @return
     */
    protected Object getArgsObject(Object[] args) {
        if (args == null) {
            return null;
        }
        // 去掉HttpServletRequest和HttpServletResponse
        List<Object> realArgs = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                continue;
            }
            if (arg instanceof HttpServletResponse) {
                continue;
            }
            if (arg instanceof MultipartFile) {
                continue;
            }
            if (arg instanceof ModelAndView) {
                continue;
            }
            realArgs.add(arg);
        }
        if (realArgs.size() == 1) {
            return realArgs.get(0);
        } else {
            return realArgs;
        }
    }


    /**
     * 获取参数Map的JSON字符串
     *
     * @param paramsMap
     * @return
     */
    protected JSONObject getParamJSONObject(Map<String, String[]> paramsMap) {
        if (MapUtils.isEmpty(paramsMap)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String[]> kv : paramsMap.entrySet()) {
            String key = kv.getKey();
            String[] values = kv.getValue();
            // 没有值
            if (values == null) {
                jsonObject.put(key, null);
            } else if (values.length == 1) {
                // 一个值
                jsonObject.put(key, values[0]);
            } else {
                // 多个值
                jsonObject.put(key, values);
            }
        }
        return jsonObject;
    }

    /**
     * 处理排除路径，匹配返回true，否则返回false
     *
     * @param excludePaths 排除路径
     * @param realPath     请求实际路径
     * @return
     */
    protected boolean handleExcludePaths(Set<String> excludePaths, String realPath) {
        if (CollectionUtils.isEmpty(excludePaths) || StringUtils.isBlank(realPath)) {
            return false;
        }
        // 如果是排除路径，则跳过
        if (excludePaths.contains(realPath)) {
            return true;
        }
        return false;
    }

    /**
     * 获取实际路径
     *
     * @param requestPath
     * @return
     */
    private String getRealPath(String requestPath) {
        // 如果项目路径不为空，则去掉项目路径，获取实际访问路径
        if (StringUtils.isNotBlank(contextPath)) {
            return requestPath.substring(contextPath.length());
        }
        return requestPath;
    }

    /**
     * 释放资源
     */
    protected void remove() {
        threadLocal.remove();
        requestInfoThreadLocal.remove();
        MDC.clear();
    }

}
