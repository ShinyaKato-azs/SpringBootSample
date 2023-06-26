package com.example.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.UserService;
import com.example.domain.user.model.MUser;
import com.example.form.UserListForm;

@Controller
@RequestMapping("/user")
public class UserListController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	//ユーザー一覧を取得、ユーザー画面一覧を表示
	@GetMapping("/list")
	public String getUserList(@ModelAttribute UserListForm form, Model model) {

		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);

		//ユーザー一覧取得
		List<MUser> userList = userService.getUsers(user);

		//Modelに登録
		model.addAttribute("userList", userList);

		return "user/list";
	}

}
