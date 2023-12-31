package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	// /loginにアクセスが来たらログイン画面の表示
	@GetMapping("/login")
	public String getLogin() {
		return "login/login";
	}

	//loginがPostされたらユーザー画面一覧にリダイレクト
	@PostMapping("/login")
	public String postLogin() {
		return "redirect:user/list";
	}

}
