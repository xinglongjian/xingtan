package com.xingtan.account.service;

import com.xingtan.account.bean.WeixinUser;
import com.xingtan.account.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(long id);
    List<User> getUsersByIds(List<Long> ids);
    User getUserByEmail(String email);
    User getUserByUserName(String userName);
    User getUserByPhone(String telephone);
    User getUserByIdCardNo(String idCardNo);
    User saveByWxUser(WeixinUser user);
    long insertUser(User student);
    void updateUser(User student);
    void deleteUser(long id);

}
