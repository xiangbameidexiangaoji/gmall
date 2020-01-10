package com.sxt.mall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.constant.SysCacheConstant;
import com.sxt.mall.pms.entity.ProductCategory;
import com.sxt.mall.pms.mapper.ProductCategoryMapper;
import com.sxt.mall.pms.service.ProductCategoryService;
import com.sxt.mall.vo.product.PmsProductCategoryWithChildrenItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author
 * @since 2019-12-06
 */
@Slf4j
@Service
@Component
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<PmsProductCategoryWithChildrenItem> listCatelogWithChilder(Integer i) {

        List<PmsProductCategoryWithChildrenItem> items = null;
        //查询缓存是否有数据
        Object cacheMenu = this.redisTemplate.opsForValue().get(SysCacheConstant.CATEGORY_MENU_CACHE_KEY);
        if (cacheMenu != null) {
            log.debug("=======菜单数据命中缓存======");
            return (List<PmsProductCategoryWithChildrenItem>) cacheMenu;
        }
        //查询所有商品分类（递归查询）
        items = this.productCategoryMapper.listCatelogWithChilder(i);
        if (items != null) {
            this.redisTemplate.opsForValue().set(SysCacheConstant.CATEGORY_MENU_CACHE_KEY, items);
        } else {
            log.debug("======菜单数据缓存未命中,已添加空值");
            //如果本地也没有，缓存一个空值
            this.redisTemplate.opsForValue().set(SysCacheConstant.CATEGORY_MENU_CACHE_KEY, null, 20, TimeUnit.SECONDS);
        }
        return items;
    }
}
