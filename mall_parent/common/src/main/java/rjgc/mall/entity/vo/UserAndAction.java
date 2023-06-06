package rjgc.mall.entity.vo;

import lombok.Data;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;

@Data
public class UserAndAction {
    private Integer orderId;
    private String tradePlace;
    private String tradeTime;
    private Integer goodsId;
    private String name;
    private String tele;
}
