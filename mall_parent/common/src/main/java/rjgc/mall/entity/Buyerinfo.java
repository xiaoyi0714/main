package rjgc.mall.entity;

public class Buyerinfo {
    private String name;
    private Integer id;
    private Integer tradeid;
    private String tele;
    private String trade_time;

    public Integer getTradeid() {
        return tradeid;
    }

    public void setTradeid(Integer tradeid) {
        this.tradeid = tradeid;
    }

    private String trade_place;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(String trade_time) {
        this.trade_time = trade_time;
    }

    public String getTrade_place() {
        return trade_place;
    }

    public void setTrade_place(String trade_place) {
        this.trade_place = trade_place;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Buyerinfo(String name,Integer id,Integer tradeid, String tele, String trade_time, String trade_place) {
        this.name = name;
        this.tele = tele;
        this.trade_time = trade_time;
        this.trade_place = trade_place;
        this.id=id;
        this.tradeid=tradeid;
    }
}
