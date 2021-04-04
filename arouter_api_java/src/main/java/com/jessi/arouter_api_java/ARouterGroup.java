package com.jessi.arouter_api_java;

import java.util.Map;

public interface ARouterGroup {

    Map<String, Class<? extends ARouterPath>> getGroupMap();
}
