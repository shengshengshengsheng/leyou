package com.leyou.user.service;

import com.leyou.user.mapper.UserMapper;
import com.leyou.user.opjo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户数据的校验，主要包括对：手机号、用户名的唯一性校验。
     * @author XuQiangsheng
     * @date 2020/11/4 9:35
     * @param data 要校验的数据
     * @param type 要校验的数据类型：1，用户名；2，手机；
     * @return True 该用户名或手机号可用  False 不可用
     */
    public Boolean checkUserData(String data, Integer type) {
        User user = new User();
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                return null;
        }
        return userMapper.selectCount(user) == 0;
    }
}