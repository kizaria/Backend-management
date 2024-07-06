package com.lys.web.wx_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lys.web.chart.entity.orderChart;
import com.lys.web.chart.entity.userChart;
import com.lys.web.wx_user.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author kiza
* @description 针对表【wx_user】的数据库操作Mapper
* @createDate 2024-01-12 13:54:48
* @Entity generator.domain.WxUser
*/
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {
    //用户分析图
    List<userChart> getUserChart();
}




