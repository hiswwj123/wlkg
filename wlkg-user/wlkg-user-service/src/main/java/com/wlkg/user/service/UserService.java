package com.wlkg.user.service;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.common.utils.CodecUtils;
import com.wlkg.common.utils.NumberUtils;
import com.wlkg.user.mapper.UserMapper;
import com.wlkg.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/28 0028 14:17
 */
@Service
public class UserService {
    static final String KEY_PREFIX = "user:code:phone:";
    static final Logger logger =
            LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 检查用户是否存在
     *
     * @param data
     * @param type
     * @return
     */
    public Boolean checkUserExist(String data, Integer type) {
        //默认是查询用户名
        if (type == null) {
            type = 1;
        }
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            case 3:
                user.setEmail(data);
                break;
            default:
                return false;
        }

        int i = userMapper.selectCount(user);
        //查询对应的用户个数
        return i == 0;
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     */
    public Boolean sendVerifyCode(String phone) {
        //生成验证码
        String code = NumberUtils.generateCode(6);
        try {
            // 生产者发送消息给交换机
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            //生成者生产消息、
            amqpTemplate.convertAndSend("wlkg.sms.exchange", "sms.verify.code", msg);

            // 将code存入redis
            this.stringRedisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (AmqpException e) {
            logger.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }

    /**
     * 注册用户
     * 验证码确认
     * 生成盐
     * 对密码加密
     * 写入数据库
     * 删除redis中的验证码
     *
     * @param user
     * @param code
     * @return
     */
    public Boolean register(User user, String code) {
        //根据手机号生成键
        String key = KEY_PREFIX + user.getPhone();
        // 根据键去除验证码
        String codeCache = stringRedisTemplate.opsForValue().get(key);
        if (!code.equals(codeCache)) {
            //不正确，返回
            //throw new WlkgException(ExceptionEnums.REGISTER_FAIL);
            return false;
        }
        //设置一下注册日期
        user.setCreated(new Date());
        //生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        //对密码进行加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        user.setEmail("599756790@qq.com");
        Boolean boo = userMapper.insertSelective(user) == 1;
        if (boo) {
            try {
                stringRedisTemplate.delete(key);
            } catch (Exception e) {
                logger.error("删除缓存验证码失败，codde", code, e);
                return false;
            }
        }
        return true;
    }

    /**
     * 查询用户信息
     *
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
        //先根据用户名查询出对应的用户对象
        User user = userMapper.selectOne(record);
        //校验用户名
        if (user == null) {
            throw new WlkgException(ExceptionEnums.INVALID_USERNAME_PASSWORD);
        }

        //校验密码
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            throw new WlkgException(ExceptionEnums.INVALID_USERNAME_PASSWORD);
        }
        //用户名密码都正确
        return user;
    }

    /**
     * 用户登录
     * @param loginValue
     * @param passWord
     * @param type
     * @return
     */
    public User login(String loginValue,String passWord, Integer type) {
        if(type == null){
            type = 1;
        }
        User recod = new User();
        User user = null;
        switch(type){
            case 1:
                recod.setUsername(loginValue);
                user = userMapper.selectOne(recod);
                break;
            case 2:
                recod.setPhone(loginValue);
                user = userMapper.selectOne(recod);
                break;
            case 3:
                recod.setEmail(loginValue);
                user = userMapper.selectOne(recod);
                break;
            default:
                return user;
        }

        //校验密码
        if (!user.getPassword().equals(CodecUtils.md5Hex(passWord, user.getSalt()))) {
            throw new WlkgException(ExceptionEnums.INVALID_USERNAME_PASSWORD);
        }
        return user;
    }
}
