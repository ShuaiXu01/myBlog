package com.xu.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.common.lang.Result;
import com.xu.entity.Blog;
import com.xu.service.BlogService;
import com.xu.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class BlogController {
    
    @Autowired
    BlogService blogService;

    /**
     * 博客列表
     * @param currentPage
     * @return
     */
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,
                       @RequestParam(required = false) String userId) {
        Page page = new Page(currentPage, 5);
        if (null == userId || userId.isEmpty()) {
            IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
            return Result.success(pageData);
        }else {
            IPage pageData = blogService.page(page, new QueryWrapper<Blog>()
                                                    .eq("user_id", userId)
                                                    .orderByDesc("created"));
            return Result.success(pageData);    
        }
    }

    /**
     * 编辑博客
     * 需要登录之后才能访问
     * @param blog
     * @return
     */
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            //只能编辑自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        
        return Result.success(null);
    }

    /**
     * 删除博客
     * @param id
     * @return
     */
    @RequiresAuthentication
    @PostMapping("/blog/delete")
    public Result delete(@RequestParam Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");
        blogService.removeById(id);
        return Result.success(null);
    }

    /**
     * 博客详情
     * @param id
     * @return
     */
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");
        return Result.success(blog);
    }
    
}
