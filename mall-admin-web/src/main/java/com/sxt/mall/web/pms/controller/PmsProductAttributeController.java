package com.sxt.mall.web.pms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sxt.mall.pms.service.ProductAttributeService;
import com.sxt.mall.to.CommonResult;
import com.sxt.mall.vo.PageInfoVo;
import com.sxt.mall.vo.product.PmsProductAttributeParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性管理Controller
 */
@RestController
@CrossOrigin
@Api(tags = "PmsProductAttributeController", description = "商品属性管理")
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Reference
    private ProductAttributeService productAttributeService;

    @ApiOperation("根据分类查询属性列表或参数列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "0表示属性，1表示参数", required = true, paramType = "query", dataType = "integer")})
    @GetMapping(value = "/list/{cid}")
    public Object getList(@PathVariable Long cid,
                          @RequestParam(value = "type") Integer type,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        //TODO 根据分类查询属性列表或参数列表
        PageInfoVo pageInfoVo = this.productAttributeService.getCategoryAttributes(cid, type, pageSize, pageNum);
        return new CommonResult().success(pageInfoVo);
    }

    @ApiOperation("添加商品属性信息")
    @PostMapping(value = "/create")
    public Object create(@RequestBody PmsProductAttributeParam productAttributeParam, BindingResult bindingResult) {
        //TODO 添加商品属性信息
        return new CommonResult().success(null);
    }

    @ApiOperation("修改商品属性信息")
    @PostMapping(value = "/update/{id}")
    public Object update(@PathVariable Long id, @RequestBody PmsProductAttributeParam productAttributeParam, BindingResult bindingResult) {
        //TODO 修改商品属性信息
        return new CommonResult().success(null);
    }

    @ApiOperation("查询单个商品属性")
    @GetMapping(value = "/{id}")
    public Object getItem(@PathVariable Long id) {
        //TODO 查询单个商品属性
        return new CommonResult().success(null);
    }

    @ApiOperation("批量删除商品属性")
    @PostMapping(value = "/delete")
    public Object delete(@RequestParam("ids") List<Long> ids) {
        //TODO 批量删除商品属性
        return new CommonResult().success(null);
    }

    @ApiOperation("根据商品分类的id获取商品属性及属性分类")
    @GetMapping(value = "/attrInfo/{productCategoryId}")
    public Object getAttrInfo(@PathVariable Long productCategoryId) {
        //TODO 根据分类查询属性列表或参数列表
        return new CommonResult().success(null);
    }
}
