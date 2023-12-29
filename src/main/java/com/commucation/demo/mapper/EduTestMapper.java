package com.commucation.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.commucation.demo.entity.EduTest;

import java.util.List;

public interface EduTestMapper extends BaseMapper<EduTest> {
    EduTest findAll();
}
