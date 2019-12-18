package com.sxt.mall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.mall.pms.entity.Brand;
import com.sxt.mall.pms.mapper.BrandMapper;
import com.sxt.mall.pms.service.BrandService;
import com.sxt.mall.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author
 * @since 2019-12-06
 */
@Component
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfoVo brandPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        QueryWrapper<Brand> name = null;
        if (!StringUtils.isEmpty(keyword)) {
            name = new QueryWrapper<Brand>().like("name", keyword);
        }
        //分页查询数据
        IPage<Brand> brandIPage = this.brandMapper.selectPage(new Page<Brand>(pageNum.longValue(), pageSize.longValue()), name);
        //封装分页
        PageInfoVo pageInfoVo = new PageInfoVo(
                brandIPage.getTotal(),  //总条数
                brandIPage.getPages(),  //总页数
                pageSize.longValue(),   //当前页的大小
                brandIPage.getRecords(),//查询的数据
                brandIPage.getCurrent());//当前页
        return pageInfoVo;
    }
}
