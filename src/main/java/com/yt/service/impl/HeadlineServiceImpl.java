package com.yt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.pojo.Headline;
import com.yt.service.HeadlineService;
import com.yt.mapper.HeadlineMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2023-10-01 16:46:44
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

}




