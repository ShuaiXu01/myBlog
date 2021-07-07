package com.xu.service.impl;

import com.xu.entity.Blog;
import com.xu.mapper.BlogMapper;
import com.xu.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
