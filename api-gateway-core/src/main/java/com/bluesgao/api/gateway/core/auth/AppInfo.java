package com.bluesgao.api.gateway.core.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 应用信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppInfo {
    private String appId;//应用id
    private String appName;//应用名称
    private String appDesc;//应用描述
    private String appOwner;//应用负责人
    private String appOwnerEmail;//应用负责人邮箱
    private String appMOwnerPhone;//应用负责人电话
    private String appSecret;//应用密码
    //private String noticekUrl;//通知回调url（post）
    private Integer appStatus;//应用状态 0:待审核 1:审核通过 2:审核驳回
}
