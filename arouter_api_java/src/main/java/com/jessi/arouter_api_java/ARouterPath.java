package com.jessi.arouter_api_java;

import com.jessi.arouter_annotation_java.bean.RouterBean;

import java.util.Map;

public interface ARouterPath {
    Map<String, RouterBean> getPathMap();
}
