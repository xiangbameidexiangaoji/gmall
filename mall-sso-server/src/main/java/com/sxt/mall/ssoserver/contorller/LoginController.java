package com.sxt.mall.ssoserver.contorller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.sxt.mall.constant.SysCacheConstant;
import com.sxt.mall.to.CommonResult;
import com.sxt.mall.ums.entity.Member;
import com.sxt.mall.ums.service.MemberService;
import com.sxt.mall.vo.ums.LoginResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Reference
    private MemberService memberService;

    /**
     * 登录
     *
     * @return
     */
    @GetMapping("/applogin")
    @ResponseBody
    public CommonResult loginForMall(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        Member member = this.memberService.login(username, password);
        if (member == null) {
            //未登录
            CommonResult failed = new CommonResult().failed();
            failed.setMessage("账号密码不匹配,请重新登录");
            return failed;
        } else {
            String token1 = request.getHeader("token");
            if (token1.isEmpty()) {
                return null;
            }

            String s = this.stringRedisTemplate.opsForValue().get(token1);

            String token = UUID.randomUUID().toString().replace("-", "");

            this.stringRedisTemplate.opsForValue().set(
                    SysCacheConstant.LOGIN_MEMBER + token,  //token   login:member:uuid
                    JSON.toJSONString(member)                //对象    login:member:uuid:{obj}
            );

            LoginResponseVo responseVo = new LoginResponseVo();
            BeanUtils.copyProperties(member, responseVo);
            responseVo.setAccessToken(token);// 设置访问令牌
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new CommonResult().success(responseVo);
        }
    }
}
