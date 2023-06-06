package rjgc.mall.entity.param;

import lombok.Data;

@Data
public class OrderInfoParam {
    private Integer userId;
    private Integer orderId;
    private String name;
    private String tele;
    private String tradeTime;
    private String tradePlace;
}
