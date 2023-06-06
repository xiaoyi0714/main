package rjgc.mall.mapper;

import org.apache.ibatis.annotations.Mapper;
import rjgc.mall.entity.OrderUpdate;
import rjgc.mall.entity.param.OrderInfoParam;

@Mapper
public interface OrderUpdateMapper {
    Integer insert (OrderInfoParam orderInfoParam);
    Integer deleteByid(Integer id);
    OrderUpdate selectById(Integer id);
}
