package com.rezatechie.filter;

import com.rezatechie.context.StageContextHolder;
import com.amazonaws.serverless.proxy.RequestReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Component
public class ApiGatewayStageVariableFilter implements Filter {

    @Autowired
    private StageContextHolder stageContextHolder;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            if (request instanceof HttpServletRequest) {
                @SuppressWarnings("unchecked")
                Map<String, String> stageVariables = (Map<String, String>)
                        ((HttpServletRequest) request).getAttribute(RequestReader.API_GATEWAY_STAGE_VARS_PROPERTY);

                if (stageVariables != null) {
                    stageContextHolder.setStageVariables(stageVariables);
                }
            }

            chain.doFilter(request, response);
        } finally {
            stageContextHolder.clear(); // Clean up after request ends
        }
    }
}
