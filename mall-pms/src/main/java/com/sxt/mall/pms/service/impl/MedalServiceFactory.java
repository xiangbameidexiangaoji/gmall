package com.sxt.mall.pms.service.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李涵林
 * @data 2021/4/12 13:11
 */
public class MedalServiceFactory {
    private static final Map<String,IMdelService> map = new HashMap<>();

    static {
        map.put("guard", new GuardMedalServiceImpl());
        map.put("vip", new VipMedalServiceImpl());
        map.put("guest", new GuardMedalServiceImpl());
    }

    public static IMdelService getMedalService(String medalType){
        return map.get(medalType);
    }
}
