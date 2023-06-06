package rjgc.mall.entity.vo;

import lombok.Data;
import rjgc.mall.entity.Goods;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;

@Data
public class Order {

    private User user;

    private TradeAction tradeAction;

    private Goods goods;
}
