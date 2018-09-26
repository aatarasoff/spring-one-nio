package info.developerblog.spring.oneserver.client;

import com.google.common.collect.Maps;
import com.netflix.client.ClientException;
import com.netflix.client.IResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpStatus;

import one.nio.http.Header;
import one.nio.http.Response;
import one.nio.serial.Serializer;

/**
 * @author alexander.tarasov
 */
public class OneHttpResponse implements IResponse {
    private final byte[] payload;
    private final int status;
    private final Map<String, String> headers = Maps.newHashMap();
    private final URI originalURI;

    public OneHttpResponse(Response response,
                           URI originalURI) {
        payload = response.getBody();
        status = response.getStatus();
        for (String header : response.getHeaders()) {
            if (header != null) {
                String[] headerParts = header.split(": ");
                if (headerParts.length > 1) {
                    headers.put(headerParts[0], headerParts[1]);
                }
            }
        }

        this.originalURI = originalURI;
    }

    @Override
    public Object getPayload() throws ClientException {
        return payload;
    }

    public <T> T get() throws ClientException {
        try {
            return (T) Serializer.deserialize(payload);
        } catch (IOException | ClassNotFoundException e) {
            throw new ClientException(e);
        }
    }

    @Override
    public boolean hasPayload() {
        return payload != null;
    }

    @Override
    public boolean isSuccess() {
        return HttpStatus.valueOf(status).is2xxSuccessful();
    }

    @Override
    public URI getRequestedURI() {
        return originalURI;
    }

    @Override
    public Map<String, ?> getHeaders() {
        return headers;
    }

    @Override
    public void close() throws IOException {
        //do nothing
    }
}
