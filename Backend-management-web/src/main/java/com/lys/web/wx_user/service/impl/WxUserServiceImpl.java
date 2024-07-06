package com.lys.web.wx_user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lys.web.chart.entity.userChart;
import com.lys.web.wx_user.entity.WxUser;
import com.lys.web.wx_user.mapper.WxUserMapper;
import com.lys.web.wx_user.service.WxUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author kiza
* @description 针对表【wx_user】的数据库操作Service实现
* @createDate 2024-01-12 13:54:48
*/
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

    @Override
    public List<userChart> getUserChart() {
        return this.baseMapper.getUserChart();
    }
}




