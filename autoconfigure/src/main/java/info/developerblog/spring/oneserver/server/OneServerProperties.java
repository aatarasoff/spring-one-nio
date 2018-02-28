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
}
