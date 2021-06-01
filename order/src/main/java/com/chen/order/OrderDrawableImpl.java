package com.chen.order;

import com.jessi.common.OrderDrawable;
import com.jessi.arouter_annotation_java.ARouter;

/**
 * @author Created by CHEN on 2021/5/20
 * @email 188669@163.com
 */
@ARouter(path = "/order/getDrawable", group = "order")
public class OrderDrawableImpl implements OrderDrawable {
    @Override
    public int getDrawable() {
        return R.drawable.order_logo;
    }
}
