package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.UserService;
import com.example.domain.user.model.MUser;
import com.example.form.UserDetailForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserDetailController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	//ユーザー詳細画面の表示。動的URLの処理
	@GetMapping("/detail/{userId:.+}")
	public String getUser(UserDetailForm form, Model model, @PathVariable("userId") String userId) {

		//ユーザーを一件取得
		MUser user = userService.getUserOne(userId);
		user.setPassword(null);

		//MUserをformに変換
		form = modelMapper.map(user, UserDetailForm.class);

		//モデルに登録
		model.addAttribute("userDetailForm", form);

		return "user/detail";
	}

	//ユーザー更新処理 paramsでbuttonタグのname属性と同じ値を指定すると、同じformでも別の場所で受け取れる
	@PostMapping(value = "/detail", params = "update")
	public String updateUser(UserDetailForm form, Model model) {
		try {

			//ユーザーを更新
			userService.updateUserOne(form.getUserId(), form.getPassword(), form.getUserName());

		} catch (Exception e) {
			log.error("ユーザー更新でエラー");
		}
		return "redirect:/user/list";
	}

	//ユーザー削除処理
	@PostMapping(value = "/detail", params = "delete")
	public String deleteUser(UserDetailForm form, Model model) {

		//ユーザーを削除
		userService.deleteUserOne(form.getUserId());

		return "redirect:/user/list";
	}

}
