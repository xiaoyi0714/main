package rjgc.mall.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderUpdate {
    private Integer id;
    private String trade_time;
    private String trade_place;
    private String name;
    private String tele;
}
