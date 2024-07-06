package com.lys.web.wx_user.service;

import com.lys.web.chart.entity.orderChart;
import com.lys.web.chart.entity.userChart;
import com.lys.web.wx_user.entity.WxUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author kiza
* @description 针对表【wx_user】的数据库操作Service
* @createDate 2024-01-12 13:54:48
*/
public interface WxUserService extends IService<WxUser> {

    //用户分析图
    List<userChart> getUserChart();

}
