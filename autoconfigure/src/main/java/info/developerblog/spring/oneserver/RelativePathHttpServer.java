package info.developerblog.spring.oneserver;

import one.nio.http.HttpServer;
import one.nio.http.Path;
import one.nio.http.RequestHandler;
import one.nio.http.gen.RequestHandlerGenerator;
import one.nio.net.ConnectionString;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author alexander.tarasov
 */
public class RelativePathHttpServer extends HttpServer {
    public RelativePathHttpServer(ConnectionString conn, Object... routers) throws IOException {
        super(conn, routers);
    }

    @Override
    public void addRequestHandlers(final Object router) {
        final RequestHandlerGenerator generator = new RequestHandlerGenerator();
        final Class<?> routerClass = router.getClass();
        final HttpController controllerAnnotation = routerClass.getAnnotation(HttpController.class);

        for (Method m : routerClass.getMethods()) {
            Path pathAnnotation = m.getAnnotation(Path.class);
            if (pathAnnotation != null) {
                RequestHandler requestHandler = generator.generateFor(m, router);

                if (controllerAnnotation.value().length == 0) {
                    for (String path : pathAnnotation.value()) {
                        requestHandlers.put(path, requestHandler);
                    }
                } else {
                    for (String rootPath : controllerAnnotation.value()) {
                        for (String path : pathAnnotation.value()) {
                            requestHandlers.put(rootPath + path, requestHandler);
                        }
                    }
                }
            }
        }
    }
}
