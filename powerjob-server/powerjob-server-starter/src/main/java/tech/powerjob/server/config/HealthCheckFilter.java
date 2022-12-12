package tech.powerjob.server.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * 健康检查
 *
 * @author Harrison
 * @date 2021/11/15 2:28 下午
 */
@Slf4j
public class HealthCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.getWriter().write("ok");
        response.getWriter().flush();
    }
}
