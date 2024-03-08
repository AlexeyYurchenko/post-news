package com.example.postnews.service.impl;

import com.example.postnews.entity.Post;
import com.example.postnews.entity.User;
import com.example.postnews.repository.UserRepository;
import com.example.postnews.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        log.debug("UserServiceImpl->findAll");
        return userRepository.findAll();
    }

    @Override
    public List<User> findAll(int pageNumber, int pageSize) {
        log.debug("UserServiceImpl->findAll pageNumber= {}, pageSize= {}", pageNumber, pageSize);
        List<User> users = userRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
        for (User user : users) {
            user.setComments(null);
            for (Post post : user.getPosts()) {
                post.setComments(null);
            }
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        log.debug("UserServiceImpl->findById id= {}", id);
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public User save(User user) {
        log.debug("UserServiceImpl->save user= {}", user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        log.debug("UserServiceImpl->update user= {}", user);
        User existedUser = userRepository.findById(user.getId()).orElse(null);
        if (existedUser != null) {
            existedUser.setUsername(user.getUsername());
            existedUser.setEmail(user.getEmail());
            existedUser.setComments(user.getComments());
            existedUser.setPosts(user.getPosts());
            return userRepository.save(existedUser);
        }
        return null;
    }

    @Override
    public User deleteById(Long id) {
        log.debug("UserServiceImpl->deleteById id= {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.deleteById(id);
            return user;
        }
        return null;
    }
}