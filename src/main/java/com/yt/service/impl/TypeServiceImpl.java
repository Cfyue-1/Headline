package com.yt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.pojo.Type;
import com.yt.service.TypeService;
import com.yt.mapper.TypeMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2023-10-01 16:46:44
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

}




