package info.developerblog.spring.oneserver.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author alexander.tarasov
 */
@ConfigurationProperties(prefix = "oneserver")
public class OneServerProperties {
    private String advertiseIp;
    private Integer port;

    private int selectorThreadsCount = 0;
    private int maxWorkersCount = 0;
    private int minWorkersCount = 0;
    private int queueTime = 0;
    private int keepAlive = 0;
    private int threadPriority = Thread.NORM_PRIORITY;
    private boolean affinity = false;

    public String getAdvertiseIp() {
        return advertiseIp;
    }

    public void setAdvertiseIp(String advertiseIp) {
        this.advertiseIp = advertiseIp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public int getSelectorThreadsCount() {
        return selectorThreadsCount;
    }

    public void setSelectorThreadsCount(int selectorThreadsCount) {
        this.selectorThreadsCount = selectorThreadsCount;
    }

    public int getMaxWorkersCount() {
        return maxWorkersCount;
    }

    public void setMaxWorkersCount(int maxWorkersCount) {
        this.maxWorkersCount = maxWorkersCount;
    }

    public int getMinWorkersCount() {
        return minWorkersCount;
    }

    public void setMinWorkersCount(int minWorkersCount) {
        this.minWorkersCount = minWorkersCount;
    }

    public int getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(int queueTime) {
        this.queueTime = queueTime;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isAffinity() {
        return affinity;
    }

    public void setAffinity(boolean affinity) {
        this.affinity = affinity;
    }
}
