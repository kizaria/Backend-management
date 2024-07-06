package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Announcement;
import generator.service.AnnouncementService;
import generator.mapper.AnnouncementMapper;
import org.springframework.stereotype.Service;

/**
* @author kiza
* @description 针对表【announcement】的数据库操作Service实现
* @createDate 2024-04-21 19:35:11
*/
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
    implements AnnouncementService{

}




