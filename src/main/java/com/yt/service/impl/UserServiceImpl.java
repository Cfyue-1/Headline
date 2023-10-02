package com.yt.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.pojo.User;
import com.yt.service.UserService;
import com.yt.mapper.UserMapper;
import com.yt.utils.JwtHelper;
import com.yt.utils.MD5Util;
import com.yt.utils.Result;
import com.yt.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2023-10-01 16:46:44
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 1.登录 查询用户对象 loginUser
     * 2.如果用户对象为null，查询失败，账号错误，501
     * 3.对比密码，失败，503
     * 4.根据id生成一个token，token -》 result返回
     */
    @Override
    public Result login(User user) {
//        //1.登录 查询用户对象 loginUser
//        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        userLambdaQueryWrapper.eq(User::getUsername, user.getUsername());
//        User loginUser = userMapper.selectOne(userLambdaQueryWrapper);
//
//        //2.如果用户对象为null，查询失败，账号错误，501
//        if (loginUser == null) {
//            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
//        }
//
//        //3.对比密码，失败，503
//        if (StringUtils.isEmpty(user.getUserPwd())
//                || !(MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd()))) {
//            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
//        }
//
//        //4.根据id生成一个token，token -》 result返回
//        String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
//        Map data = new HashMap();
//        data.put("token",token);
//
//        return Result.ok(data);


        //根据账号查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(queryWrapper);

        //账号判断
        if (loginUser == null) {
            //账号错误
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //判断密码
        if (!StringUtils.isEmpty(user.getUserPwd())
                && loginUser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd())))
        {
            //账号密码正确
            //根据用户唯一标识生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));

            Map data = new HashMap();
            data.put("token",token);

            return Result.ok(data);
        }

        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }


    /**
     * 1.验证token是否在有效期
     * 2.根据token解析userId
     * 3.根据userId查询数据
     * 4.去掉密码，封装result返回
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
//        1.验证token是否在有效期
        if (jwtHelper.isExpiration(token)){
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

//        2.根据token解析userId
        int userId = jwtHelper.getUserId(token).intValue();

//        3.根据userId查询数据
        User user = userMapper.selectById(userId);

//        4.去掉密码，封装result返回
        user.setUserPwd("");

        Map data = new HashMap();
        data.put("login",user);

        return Result.ok(data);

    }

    /**
     *
     * @param username
     * @return
     */

    @Override
    public Result checkUserName(String username) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        Long l = userMapper.selectCount(userLambdaQueryWrapper);

        if (l != 0) {
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        return Result.ok(null);
    }

    /**
     * 1.检查用户名是否可以用
     * 2.给密码加密
     * 3.把用户数据保存
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        Long l = userMapper.selectCount(userLambdaQueryWrapper);

        if (l != 0) {
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));

        userMapper.insert(user);

        return Result.ok(null);
    }
}




