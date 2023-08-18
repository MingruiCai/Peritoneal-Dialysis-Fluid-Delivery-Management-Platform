package com.bcsd.framework.log;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * AOP配置属性
 **/
@Data
@Component
@ConfigurationProperties(prefix = "logging.aop")
public class LogAopProperties {

    /**
     * 请求路径Filter配置
     */
    @NestedConfigurationProperty
    private LogAopConfig log = new LogAopConfig();

    @Data
    public static class AopConfig {
        /**
         * 是否启用
         */
        private boolean enable;

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class LogAopConfig extends AopConfig {

        /**
         * 是否启用requestId
         */
        private boolean enableRequestId = true;

        /**
         * 日志输出类型：print-type
         */
        private LogPrintType logPrintType = LogPrintType.ORDER;

        /**
         * 请求ID生成类型
         */
        private RequestIdType requestIdType = RequestIdType.IDWORK;

        /**
         * 请求日志在控制台是否格式化输出，local环境建议开启，服务器环境设置为false
         */
        private boolean requestLogFormat = true;

        /**
         * 响应日志在控制台是否格式化输出，local环境建议开启，服务器环境设置为false
         */
        private boolean responseLogFormat = true;

        /**
         * 排除路径
         */
        private Set<String> excludePaths;
    }
    /**
     * 日志打印类型
     * @author geekidea
     * @date 2020/3/19
     **/
    public enum LogPrintType {

        /**
         * 不打印日志
         */
        NONE,
        /**
         * 请求和响应日志，按照执行顺序分开打印
         */
        ORDER,
        /**
         * 方法执行结束时，连续分开打印请求和响应日志
         */
        LINE,
        /**
         * 方法执行结束时，合并请求和响应日志，同时打印
         */
        MERGE;

    }

    /**
     * 请求ID生成类型
     *
     * @author geekidea
     * @date 2020/3/25
     **/
    public enum RequestIdType {
        /**
         * 生成UUID无中横线
         */
        UUID,
        /**
         * 生成数字
         */
        IDWORK
    }

}
