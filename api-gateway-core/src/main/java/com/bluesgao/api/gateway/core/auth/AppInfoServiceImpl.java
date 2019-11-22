package com.bluesgao.api.gateway.core.auth;

import com.alibaba.fastjson.JSON;
import com.bluesgao.api.gateway.core.common.CacheUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppInfoServiceImpl implements AppInfoService {
    @Override
    public AppInfo getAppInfoByAppId(String appId) {
        log.debug("getAppInfoByAppId appId:{}", appId);

        String appInfoStr = CacheUtils.getInstance().getJedis().get("auth:appinfo:" + appId);
        AppInfo appInfo = null;
        try {
            appInfo = JSON.parseObject(appInfoStr, AppInfo.class);
        } catch (Exception e) {
            log.error("getAppInfoByAppId parseObject error appId:{},appStr:{},e:{}", appId, appInfoStr, e);
        }
        log.debug("getAppInfoByAppId appInfo:{}", JSON.toJSONString(appInfo));
        return appInfo;
    }

    @Override
    public Boolean validateAppStatus(String appId) {
        AppInfo appInfo = this.getAppInfoByAppId(appId);
        if (appInfo == null || appInfo.getAppStatus() == null || appInfo.getAppStatus() != 1) {
            log.error("validateAppStatus appInfo error.{}", JSON.toJSONString(appInfo));
            return false;
        }
        return true;
    }
}
