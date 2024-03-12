package com.example.postnews.service.impl;

import com.example.postnews.entity.Category;
import com.example.postnews.entity.Post;
import com.example.postnews.entity.User;
import com.example.postnews.repository.PostRepository;
import com.example.postnews.service.CategoryService;
import com.example.postnews.service.PostService;
import com.example.postnews.service.UserService;
import com.example.postnews.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public List<Post> findAll() {
        log.debug("PostServiceImpl -> findAll");
        return postRepository.findAll();
    }

    @Override
    public Page<Post> findAll(int pageNumber, int pageSize) {
        log.debug("PostServiceImpl -> findAll pageNumber= {}, pageSize= {}", pageNumber, pageSize);
        return postRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Post findById(Long id) {
        log.debug("PostServiceImpl->findById id= {}", id);
        return postRepository.findById(id).orElse(null);
    }


    @Override
    public Post save(Post post) {
        log.debug("PostServiceImpl->save post= {}", post);
        User user = userService.findById(post.getUser().getId());
        Category category = categoryService.findById(post.getCategory().getId());
        user.addPost(post);
        category.addPost(post);
//        post.setUser(user);
//        post.setCategory(category);
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        log.debug("PostServiceImpl->update news= {}", post);
        User user = userService.findById(post.getUser().getId());
        Category category = categoryService.findById(post.getCategory().getId());
        Post existedPost = findById(post.getId());
        BeanUtils.copyNonNullProperties(post, existedPost);
        existedPost.setUser(user);
        existedPost.setCategory(category);
        return postRepository.save(existedPost);
    }

    @Override
    public Post deleteById(Long id) {
        log.debug("PostServiceImpl->deleteById id= {}", id);
        Post deletedNews = postRepository.findById(id).orElse(null);
        postRepository.deleteById(id);
        return deletedNews;
    }

    @Override
    @Transactional
    public Post deleteByIdAndUserId(Long id, Long userId) {
        log.debug("NewsServiceImpl->deleteByIdAndUserId id= {}, userId= {}", id, userId);
        Post deletedNews = postRepository.findById(id).orElse(null);
        postRepository.deleteByIdAndUserId(id, userId);
        return deletedNews;
    }

}
