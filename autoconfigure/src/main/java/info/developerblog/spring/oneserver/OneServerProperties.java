package info.developerblog.spring.oneserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author alexander.tarasov
 */
@ConfigurationProperties(prefix = "oneserver")
public class OneServerProperties {
    private String advertiseIp;
    private String port;

    public String getAdvertiseIp() {
        return advertiseIp;
    }

    public void setAdvertiseIp(String advertiseIp) {
        this.advertiseIp = advertiseIp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
