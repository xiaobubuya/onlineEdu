package com.xiaobubuya.edu.service.impl;

import com.xiaobubuya.edu.entity.Comment;
import com.xiaobubuya.edu.mapper.CommentMapper;
import com.xiaobubuya.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-25
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
