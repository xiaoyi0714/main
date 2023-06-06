package rjgc.mall.mapper;

import org.apache.ibatis.annotations.Mapper;
import rjgc.mall.entity.Seller;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;

import java.util.Date;
import java.util.List;

@Mapper
public interface SellerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Seller record);

    int insertSelective(Seller record);

    Seller selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Seller record);

    int updateByPrimaryKey(Seller record);
    int publishGoods(String goodsName,
                     String goodsImgUrl,
                     String goodsDescription,
                     Float goodsPrice,
                     String goodsType,
                     Integer status,
                     Integer supply_id,
                     Date date);

    Seller Sellerlogin(String account);

    int Passwordupdate(String account,
                       String password);

    User checkUser(Integer user_id);

    int checkPassword(String account, String password);

    void Register(String account, String password,String name);


    User getintendingUser(Integer id);

    void chooseUser(Integer id);

    int registerComfirm();

    List<String> Buyerinfo(Integer id);
}
