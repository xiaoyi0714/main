package rjgc.mall.entity.param;

import lombok.Data;
import rjgc.mall.entity.User;

@Data
public class BuyParam {
    private User user;
    private String tradeTime;
    private String tradePlace;
    private Integer goodsId;
}
