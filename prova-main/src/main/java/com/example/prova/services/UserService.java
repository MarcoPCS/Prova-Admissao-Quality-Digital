package com.example.prova.services;

import com.example.prova.dto.UserDTO;
import com.example.prova.entity.User;
import com.example.prova.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDTO> findUsers(String name, String cpf, String phone, Pageable pageable) {
        Specification<User> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(name)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(cpf)) {
                predicates.add(criteriaBuilder.like(root.get("cpf"), "%" + cpf + "%"));
            }

            if (StringUtils.hasText(phone)) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<User> userPage = userRepository.findAll(spec, pageable);

        return userPage.map(UserDTO::new);
    }
}
