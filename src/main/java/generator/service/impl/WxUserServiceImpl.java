package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.WxUser;
import generator.service.WxUserService;
import generator.mapper.WxUserMapper;
import org.springframework.stereotype.Service;

/**
* @author kiza
* @description 针对表【wx_user】的数据库操作Service实现
* @createDate 2024-04-22 16:23:09
*/
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser>
    implements WxUserService{

}




