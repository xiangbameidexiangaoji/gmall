package com.sxt.mall.vo.ums;

import lombok.Data;

@Data
public class LoginResponseVo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户会员等级
     */
    private Long memberLevelId;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户 访问令牌
     */
    private String accessToken;
}
