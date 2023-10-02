package com.yt.service;

import com.yt.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yt.utils.Result;

/**
* @author Administrator
* @description 针对表【news_user】的数据库操作Service
* @createDate 2023-10-01 16:46:44
*/
public interface UserService extends IService<User> {

    /**
     * 登录业务
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 检查用户名是否可用
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 注册业务
     * @param user
     * @return
     */
    Result regist(User user);
}
