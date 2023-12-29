package com.commucation.demo.common.config.filter;

import cn.hutool.json.JSONObject;
import com.google.common.collect.Lists;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class CORSAuthenticationFilter extends FormAuthenticationFilter {
    /**
     * 直接过滤可以访问的请求类型
     */
    private static final String REQUEST_TYPE = "OPTIONS";


    public CORSAuthenticationFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals(REQUEST_TYPE)) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest) request;
//        res.setHeader("Access-Control-Allow-Origin",  req.getHeader("origin"));
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Headers", "Access-Token,Authentication,Origin, X-Requested-With, Content-Type, Accept");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        res.setHeader("Access-Control-Max-Age", "3600");
        //这里要加上content-type
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, content-type");
        res.setHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equals(req.getMethod())){
            res.setStatus(HttpServletResponse.SC_OK);
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();

        JSONObject jsonObject = new JSONObject();
        jsonObject.set("msg","请先登录");
        writer.write(String.valueOf(jsonObject));
        writer.close();
        return false;
    }

    @Bean(name = "corsFilter")
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new CORSAuthenticationFilter());
        registrationBean.setUrlPatterns(Lists.newArrayList("/*"));
        registrationBean.setOrder(1);
        return registrationBean;
    }
}