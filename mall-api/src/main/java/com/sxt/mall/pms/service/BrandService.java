package com.sxt.mall.pms.service;

import com.sxt.mall.pms.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.mall.vo.PageInfoVo;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface BrandService extends IService<Brand> {

    PageInfoVo brandPageInfo(String keyword, Integer pageNum, Integer pageSize);
}
