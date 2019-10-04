package com.wlkg.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/28 0028 14:01
 */
@Table(name="tb_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 4,max = 30,message = "用户名只能在 4~30 位之间")
    private String username; //用户名

    @JsonIgnore  //返回json字符串的时候会忽略这个字段
    @Length(min = 4, max = 30, message = "用户名只能在 4~30 位之间")
    private String password; //密码

    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String phone; //手机号

    private Date created; //创建时间

    @JsonIgnore  //返回json字符串的时候会忽略这个字段
    private String salt; //密码中的盐值

    @Pattern(regexp = "^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$", message = "手机号格式不正确")
    private String email; //用户的邮箱
}
