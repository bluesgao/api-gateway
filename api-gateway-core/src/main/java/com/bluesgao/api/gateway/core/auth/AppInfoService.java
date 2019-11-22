package com.bluesgao.api.gateway.core.auth;

public interface AppInfoService {
    //获取应用信息
    AppInfo getAppInfoByAppId(String appId);

    //验证app状态
    Boolean validateAppStatus(String appId);
}
