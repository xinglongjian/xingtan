package com.xingtan.account.web;

import com.xingtan.account.bean.Jscode2sessionResult;
import com.xingtan.account.bean.LoginError;
import com.xingtan.account.bean.LoginType;
import com.xingtan.account.entity.User;
import com.xingtan.account.entity.UserBaseData;
import com.xingtan.account.service.UserBaseDataService;
import com.xingtan.account.service.UserService;
import com.xingtan.account.service.WechatServerService;
import com.xingtan.common.entity.OperationStatus;
import com.xingtan.common.utils.MD5Utils;
import com.xingtan.common.web.BaseResponse;
import com.xingtan.common.web.HttpStatus;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 登录
 */
@RestController
@Slf4j
@RequestMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private WechatServerService wechatServerService;
    @Autowired
    private UserBaseDataService userBaseDataService;


    @GetMapping("/loginBySys")
    @ApiOperation(value = "通过用户名登陆", notes = "通过用户名登陆", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "登录类型", required = true, dataType = "LoginType", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "参数不全"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部错误"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "操作成功")
    })
    public BaseResponse<User> by(String userName, String password, LoginType type) {
        if (StringUtils.isEmpty(userName)) {
            return new BaseResponse<User>(HttpStatus.BAD_REQUEST, "userName is not empty", null);
        }
        try {
            User user = queryUser(userName, type);
            if (null == user) {
                return new BaseResponse<User>(HttpStatus.OK, LoginError.USERNAME_ERROR.name(), null);
            }
            if (MD5Utils.md5(password).equals(user.getPassword())) {
                return new BaseResponse<User>(HttpStatus.OK, OperationStatus.SUCCESS.name(), user);
            } else {
                return new BaseResponse<User>(HttpStatus.OK, LoginError.PASSWORD_ERROR.name(), null);
            }
        } catch (Exception e) {
            return new BaseResponse<User>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
    }

    @GetMapping("/loginByToken")
    @ApiOperation(value = "通过Token登陆", notes = "通过Token登陆", httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "参数不全"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部错误"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "操作成功")
    })
    public BaseResponse<User> loginByToken(@RequestParam("token") String token) {
        if (StringUtils.isEmpty(token)) {
            return new BaseResponse<User>(HttpStatus.BAD_REQUEST, "token is not empty", null);
        }
        try {
            User user = queryUser(token, LoginType.TOKEN);
            log.info("loginByToken success,token:{},user:{}", token, user);
            if (null == user) {
                return new BaseResponse<User>(HttpStatus.OK, LoginError.USERNAME_ERROR.name(), null);
            } else {
                return new BaseResponse<User>(HttpStatus.OK, LoginError.USERNAME_ERROR.name(), user);
            }

        } catch (Exception e) {
            log.error("loginByToken error,token:{},caused by:{}", token, e.getMessage());
            return new BaseResponse<User>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
    }

    @GetMapping("/loginByWx")
    @ApiOperation(value = "通过微信登陆", notes = "通过微信登陆", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "登录类型", required = true, dataType = "LoginType", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "参数不全"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部错误"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "操作成功")
    })
    public BaseResponse<Jscode2sessionResult> loginByWx(String code) {
        if (StringUtils.isEmpty(code)) {
            return new BaseResponse<Jscode2sessionResult>(HttpStatus.BAD_REQUEST, "code is not empty", null);
        }
        try {
            Jscode2sessionResult jscode2sessionResult = wechatServerService.getJscode2session(code);
            if (StringUtils.isEmpty(jscode2sessionResult.getErrcode())) {
                UserBaseData userBaseData =
                        userBaseDataService.getDataByOpenId(jscode2sessionResult.getOpenid());
                if (userBaseData != null) {
                    jscode2sessionResult.setUserId(userBaseData.getUserId());
                }
                return new BaseResponse<>(HttpStatus.OK, jscode2sessionResult);
            } else {
                return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, jscode2sessionResult);
            }
        } catch (Exception e) {
            log.error("loginByWx Error:{}", e.getMessage());
            return new BaseResponse<Jscode2sessionResult>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
    }

    private User queryUser(String value, LoginType type) {
        switch (type) {
            case USERNAME:
                return userService.getUserByUserName(value);
            case EMAIL:
                return userService.getUserByEmail(value);
            case PHONE:
                return userService.getUserByPhone(value);
            case TOKEN:
                return userService.getUserByToken(value);
            default:
                return null;

        }
    }
}
