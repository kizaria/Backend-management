package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Notice;
import generator.service.NoticeService;
import generator.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

/**
* @author kiza
* @description 针对表【notice】的数据库操作Service实现
* @createDate 2024-04-22 20:14:06
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService{

}




