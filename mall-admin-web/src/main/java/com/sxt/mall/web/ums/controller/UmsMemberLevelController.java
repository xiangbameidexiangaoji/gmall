package com.sxt.mall.web.ums.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sxt.mall.to.CommonResult;
import com.sxt.mall.ums.entity.MemberLevel;
import com.sxt.mall.ums.service.MemberLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {

    @Reference
    private MemberLevelService memberLevelService;

    /**
     * 查询会员等级
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询所有会员等级")
    public CommonResult memberLevelList(@RequestParam(value = "defaultStatus") Integer defaultStatus) {
        List<MemberLevel> list = this.memberLevelService.list();
        return new CommonResult().success(list);
    }
}
