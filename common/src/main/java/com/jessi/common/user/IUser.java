package com.jessi.common.user;

import com.jessi.arouter_api_java.Call;

/**
 * @author Created by CHEN on 2021/5/31
 * @email 188669@163.com
 */
public interface IUser extends Call {
    BaseUser getUserInfo();
}
