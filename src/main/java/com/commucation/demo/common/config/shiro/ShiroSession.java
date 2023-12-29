package com.commucation.demo.common.config.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.util.StringUtils;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class ShiroSession extends DefaultWebSessionManager {

    private static final String TOKEN = "token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public ShiroSession() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {

        String id = WebUtils.toHttp(request).getHeader(TOKEN);
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            return super.getSessionId(request, response);
        }
    }


}
