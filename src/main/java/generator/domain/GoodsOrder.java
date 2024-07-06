package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName goods_order
 */
@TableName(value ="goods_order")
@Data
public class GoodsOrder implements Serializable {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 下单人id
     */
    private Integer orderUser;

    /**
     * 订单金额
     */
    private BigDecimal price;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 地址表id
     */
    private Integer addressid;

    /**
     * 逻辑删除 0：未删除 1：删除
     */
    private String deleteStatus;

    /**
     * 确认收货 0：未确认 1：确认
     */
    private String orderStatus;

    /**
     * 确认付款  0：未确认 1：确认
     */
    private String orderPay;

    /**
     * 确认发货 0：未确认 1：确认
     */
    private String orderReceiving;

    /**
     * 快递号
     */
    private String orderExpress;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}