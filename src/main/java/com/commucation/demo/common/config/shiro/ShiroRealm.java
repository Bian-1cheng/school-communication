package com.commucation.demo.common.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.commucation.demo.mapper.UserMapper;
import com.commucation.demo.service.UserService;
import com.commucation.demo.util.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.commucation.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.util.ByteSource;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j

public class ShiroRealm extends AuthorizingRealm {

    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("shiroRealm.doGetAuthenticationInfo() start ...");

        String identityNo = (String)authenticationToken.getPrincipal();
        if (StringUtils.isBlank(identityNo)) {
            throw new UnknownAccountException("用户错误，请重新登录");
        }
        System.out.println(identityNo);

        User user = userMapper.findByUsername(identityNo);

        if (user == null) {
            log.error("用户不存在");
            throw new UnknownAccountException("用户错误，请重新登录");
        }

        String credentials = user.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(identityNo);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                identityNo,
                credentials,
                credentialsSalt,
                "EduSysRealm"
        );
        SecurityUtils.getSubject().getSession().setAttribute(Constants.USER_SESSION, user);

        return authenticationInfo;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.USER_SESSION);
        String username = user.getIdentity_no();

        if (StringUtils.isNotBlank(username)){
            Set<String> roles = new HashSet<>();
            Integer roleId = user.getRole_id();
            Set<String> permissions = Collections.singleton(roleId.toString());
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.setStringPermissions(permissions);
            authorizationInfo.setRoles(roles);
            return authorizationInfo;
        }

        return null;

    }
}
