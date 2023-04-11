//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.internetitem.logback.elasticsearch.config;

import java.net.URL;

public class Settings {
    private String index;
    private String type;
    private URL url;
    private String loggerName;
    private String errorLoggerName;
    private int sleepTime = 250;
    private int maxRetries = 3;
    private int connectTimeout = 30000;
    private int readTimeout = 30000;
    private boolean logsToStderr;
    private boolean errorsToStderr;
    private boolean includeCallerData;
    private boolean includeMdc;
    private boolean rawJsonMessage;
    private int maxQueueSize = 104857600;
    private Authentication authentication;
    private int maxMessageSize = -1;
    
    public Settings() {
    }
    
    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSleepTime() {
        return this.sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        if (sleepTime < 100) {
            sleepTime = 100;
        }

        this.sleepTime = sleepTime;
    }

    public int getMaxRetries() {
        return this.maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public boolean isLogsToStderr() {
        return this.logsToStderr;
    }

    public void setLogsToStderr(boolean logsToStderr) {
        this.logsToStderr = logsToStderr;
    }

    public boolean isErrorsToStderr() {
        return this.errorsToStderr;
    }

    public void setErrorsToStderr(boolean errorsToStderr) {
        this.errorsToStderr = errorsToStderr;
    }

    public boolean isIncludeCallerData() {
        return this.includeCallerData;
    }

    public void setIncludeCallerData(boolean includeCallerData) {
        this.includeCallerData = includeCallerData;
    }

    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public String getLoggerName() {
        return this.loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getErrorLoggerName() {
        return this.errorLoggerName;
    }

    public void setErrorLoggerName(String errorLoggerName) {
        this.errorLoggerName = errorLoggerName;
    }

    public boolean isRawJsonMessage() {
        return this.rawJsonMessage;
    }

    public void setRawJsonMessage(boolean rawJsonMessage) {
        this.rawJsonMessage = rawJsonMessage;
    }

    public Authentication getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public boolean isIncludeMdc() {
        return this.includeMdc;
    }

    public void setIncludeMdc(boolean includeMdc) {
        this.includeMdc = includeMdc;
    }

    public int getMaxMessageSize() {
        return this.maxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }
}
