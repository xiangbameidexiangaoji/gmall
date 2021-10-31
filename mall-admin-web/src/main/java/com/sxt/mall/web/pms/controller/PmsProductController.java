package com.sxt.mall.web.pms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sxt.mall.pms.service.ProductService;
import com.sxt.mall.to.CommonResult;
import com.sxt.mall.vo.PageInfoVo;
import com.sxt.mall.vo.product.PmsProductParam;
import com.sxt.mall.vo.product.PmsProductQueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品管理Controller
 */
@CrossOrigin
@RestController
@Api(tags = "PmsProductController", description = "商品管理")
@RequestMapping("/product")
public class PmsProductController {
    @Reference
    private ProductService productService;

    @ApiOperation("创建商品")
    @PostMapping(value = "/create")
    public Object create(@RequestBody PmsProductParam productParam,
                         BindingResult bindingResult) {
        //TODO 查询所有一级分类及子分类
        this.productService.saveProduct(productParam);
        return new CommonResult().success(null);
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping(value = "/updateInfo/{id}")
    public Object getUpdateInfo(@PathVariable Long id) {
        //TODO 根据商品id获取商品编辑信息
        return new CommonResult().success(null);
    }

    @ApiOperation("更新商品")
    @PostMapping(value = "/update/{id}")
    public Object update(@PathVariable Long id, @RequestBody PmsProductParam productParam, BindingResult bindingResult) {
        //TODO 更新商品
        return new CommonResult().success(null);
    }

    @ApiOperation("查询商品")
    @GetMapping(value = "/list")
    public Object getList(PmsProductQueryParam pmsProductQueryParam) {
        //TODO 查询商品
        PageInfoVo pageInfoVo = this.productService.productPageInfo(pmsProductQueryParam);
        return new CommonResult().success(pageInfoVo);
    }

    @ApiOperation("根据商品名称或货号模糊查询")
    @GetMapping(value = "/simpleList")
    public Object getList(String keyword) {
        //TODO 根据商品名称或货号模糊查询
        return new CommonResult().success(null);
    }

    @ApiOperation("批量修改审核状态")
    @PostMapping(value = "/update/verifyStatus")
    public Object updateVerifyStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("verifyStatus") Integer verifyStatus,
                                     @RequestParam("detail") String detail) {
        //TODO 批量修改审核状态
        return new CommonResult().success(null);
    }

    @ApiOperation("批量上下架")
    @PostMapping(value = "/update/publishStatus")
    public Object updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                      @RequestParam("publishStatus") Integer publishStatus) {
        //TODO 批量上下架
        this.productService.updatePublishStatus(ids, publishStatus);
        return new CommonResult().success(null);
    }

    @ApiOperation("批量推荐商品")
    @PostMapping(value = "/update/recommendStatus")
    public Object updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("recommendStatus") Integer recommendStatus) {
        //TODO 批量推荐商品
        return new CommonResult().success(null);
    }

    @ApiOperation("批量设为新品")
    @PostMapping(value = "/update/newStatus")
    public Object updateNewStatus(@RequestParam("ids") List<Long> ids,
                                  @RequestParam("newStatus") Integer newStatus) {
        //TODO 批量设为新品
        return new CommonResult().success(null);
    }

    @ApiOperation("批量修改删除状态")
    @PostMapping(value = "/update/deleteStatus")
    public Object updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("deleteStatus") Integer deleteStatus) {
        //TODO 根据商品id获取商品编辑信息
        return new CommonResult().success(null);
    }
}
