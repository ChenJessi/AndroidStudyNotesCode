package com.chen.order;

import com.jessi.arouter_annotation_java.ARouter;
import com.jessi.common.user.BaseUser;
import com.jessi.common.user.IUser;

/**
 * @author Created by CHEN on 2021/5/31
 * @email 188669@163.com
 */
@ARouter(path = "/order/getUserInfo", group = "order")
public class OrderUserInfoImpl implements IUser {
    @Override
    public BaseUser getUserInfo() {
        return new BaseUser("order", "order", 18);
    }
}
