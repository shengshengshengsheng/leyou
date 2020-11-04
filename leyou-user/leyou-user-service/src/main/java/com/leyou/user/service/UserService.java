package com.leyou.user.service;

import com.leyou.common.utils.CodecUtils;
import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.opjo.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xuqiangsheng
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    static final String KEY_PREFIX = "user:code:phone:";

    static final Logger logger = LoggerFactory.getLogger(UserService.class);

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

    /**
     * 发送短信验证码
     * @author XuQiangsheng
     * @date 2020/11/4 14:46
     * @param phone
     * @return java.lang.Boolean
    */
    public Boolean sendVerifyCode(String phone) {
        // 生成验证码
        String code = NumberUtils.generateCode(6);
        try {
            // 发送短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            this.amqpTemplate.convertAndSend("leyou.sms.exchange", "sms.verify.code", msg);
            // 将code存入redis
            this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }

    /**
     *
     * @author XuQiangsheng
     * @date 2020/11/4 14:58
     * @param code 验证码
     * @return java.lang.Boolean true注册成功 false注册失败
    */
    public Boolean register(User user, String code) {
        String cacheCode = redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        if(!StringUtils.equals(code,cacheCode)){
            return false;
        }
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        // 强制设置不能指定的参数为null
        user.setId(null);
        user.setCreated(new Date());
        // 添加到数据库
        boolean b = this.userMapper.insertSelective(user) == 1;
        if(b){
            // 注册成功，删除redis中的记录
            this.redisTemplate.delete(KEY_PREFIX + user.getPhone());
        }
        return b;
    }

    public User queryUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        User userTemp = this.userMapper.selectOne(user);
        //校验用户名
        if(userTemp == null){
            return null;
        }
        //校验密码
        if(!StringUtils.equals(userTemp.getPassword(),CodecUtils.md5Hex(password,userTemp.getSalt()))){
            return null;
        }
        return userTemp;
    }
}