package rjgc.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rjgc.mall.mapper.VegetablesTypeMapper;
import rjgc.mall.service.VegetablesTypeService;

import java.util.List;

@Service
public class VegetablesTypeServiceImpl implements VegetablesTypeService {
    @Autowired
    private VegetablesTypeMapper vegetablesTypeMapper;
    @Override
    public List<String> getAllType() {
        return vegetablesTypeMapper.getAllType();
    }
}
