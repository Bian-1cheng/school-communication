package com.commucation.demo.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.commucation.demo.entity.EduTest;
import com.commucation.demo.mapper.EduTestMapper;
import com.commucation.demo.service.EduTestService;
import org.springframework.stereotype.Service;



@Service
public class EduTestServiceImpl extends ServiceImpl<EduTestMapper, EduTest> implements EduTestService {

}
