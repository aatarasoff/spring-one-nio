package info.developerblog.spring.oneserver;

import one.nio.http.HttpServer;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.Ordered;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author alexander.tarasov
 */
public class OneServerLifecycle implements SmartLifecycle {
    private HttpServer httpServer;

    private final ReentrantLock lifecycleLock = new ReentrantLock();
    private volatile boolean running = false;

    public OneServerLifecycle(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public int getPhase() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        this.stop();
        callback.run();
    }

    @Override
    public void start() {
        this.lifecycleLock.lock();
        try {
            if (!this.running) {
                httpServer.start();
                this.running = true;
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    @Override
    public void stop() {
        this.lifecycleLock.lock();
        try {
            if (this.running) {
                httpServer.stop();
                this.running = false;
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    @Override
    public boolean isRunning() {
        this.lifecycleLock.lock();
        try {
            return this.running;
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }
}
