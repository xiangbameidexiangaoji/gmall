package com.sxt.mall.pms;

import com.sxt.mall.pms.entity.Brand;
import com.sxt.mall.pms.service.BrandService;
import com.sxt.mall.pms.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MallPmsApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BrandService brandService;
    @Test
    public void contextLoads() {
//        Product byId = this.productService.getById(1);
//        System.out.println(byId.getName());
//        Brand brand = new Brand();
//        brand.setName("123");
//        this.brandService.save(brand);
//        System.out.println("保存成功");
        Brand byId = this.brandService.getById(53);
        System.out.println(byId.getName());
    }

    @Test
    public void redisTemp(){
        this.redisTemplate.opsForValue().set("key", "hello");
        System.out.println("============");
        Object key = this.redisTemplate.opsForValue().get("key");
        System.out.println("key====="+key);
        ArrayList arrayList = new ArrayList();
    }

}
