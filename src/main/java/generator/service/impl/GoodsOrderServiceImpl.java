package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.GoodsOrder;
import generator.service.GoodsOrderService;
import generator.mapper.GoodsOrderMapper;
import org.springframework.stereotype.Service;

/**
* @author kiza
* @description 针对表【goods_order】的数据库操作Service实现
* @createDate 2024-05-10 23:24:08
*/
@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder>
    implements GoodsOrderService{

}




