package rjgc.mall.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VegetablesTypeMapper {
    List<String> getAllType();
}
