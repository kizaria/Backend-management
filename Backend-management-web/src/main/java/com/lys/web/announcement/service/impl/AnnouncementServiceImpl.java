package com.lys.web.announcement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.announcement.entity.Announcement;
import com.lys.web.announcement.mapper.AnnouncementMapper;
import com.lys.web.announcement.service.AnnouncementService;
import org.springframework.stereotype.Service;

/**
* @author kiza
* @description 针对表【announcement】的数据库操作Service实现
* @createDate 2024-04-21 19:35:11
*/
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
    implements AnnouncementService {

}




