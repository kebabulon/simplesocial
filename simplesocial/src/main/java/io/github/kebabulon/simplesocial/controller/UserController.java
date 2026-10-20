package io.github.kebabulon.simplesocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.github.kebabulon.simplesocial.entity.User;
// import io.github.kebabulon.simplesocial.services.UserServices;

@Controller
public class UserController
{
	// @Autowired
    // через автовиред плохо писать private final UserRepository userreposuitory и
    // и через конструктор (с lombok @RequiredArgsConstructor)
	// private UserServices services;
}
