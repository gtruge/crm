package com.xxxx.crm.interceptor;

import com.xxxx.crm.dao.UserMapper;
import com.xxxx.crm.exceptions.NoLoginException;
import com.xxxx.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie中的用户id
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        //判断用户Id是否为空，且数据库中存在该Id的用户记录
        if (userId==null || userMapper.selectByPrimaryKey(userId)==null){
            //抛出登录异常
            throw new NoLoginException();
        }
        return true;
    }
}
