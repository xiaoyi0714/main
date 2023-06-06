package rjgc.mall.entity.vo;

import lombok.Data;
import rjgc.mall.entity.Goods;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;

import java.util.Date;

@Data
public class GoodsAndUserAndAction {
    private Goods goods;
    private User user;
    private TradeAction tradeAction;
}
