package com.sxt.mall.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoVo implements Serializable {
    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("总页码")
    private Long totalPage;

    @ApiModelProperty("每页显示记录数")
    private Long pageSize;

    @ApiModelProperty("分页查询的数据")
    private List<?> list;

    @ApiModelProperty("当前页")
    private Long pageNum;

    public static PageInfoVo getPage(IPage iPage,Long size){
        return new PageInfoVo(iPage.getTotal(), iPage.getPages(), size, iPage.getRecords(), iPage.getCurrent());
    }
}
