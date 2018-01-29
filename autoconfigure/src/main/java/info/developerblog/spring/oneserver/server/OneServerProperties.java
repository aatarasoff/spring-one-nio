package info.developerblog.spring.oneserver.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author alexander.tarasov
 */
@ConfigurationProperties(prefix = "oneserver")
public class OneServerProperties {
    private String advertiseIp;
    private Integer port;

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
}
