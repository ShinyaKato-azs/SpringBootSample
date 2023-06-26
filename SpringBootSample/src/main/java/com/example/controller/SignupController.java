package com.example.controller;

import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.application.service.UserApplicationService;
import com.example.domain.user.UserService;
import com.example.domain.user.model.MUser;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {

	@Autowired
	private UserApplicationService userApplicationService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;

	//Getされたらユーザー登録画面を返す
	//コントローラーのメソッドでは、引数にModel型を使用すると、自動的にModel型のオブジェクトが生成され、その中にキーと値を格納できる
	//applicationServiceから性別のデータ構造を呼び出す
	//モデルに性別のデータ構造を格納＝タイムリーフを通してテンプレに送る準備
	@GetMapping("/signup")
	public String getSignup(Model model, Locale locale, @ModelAttribute SignupForm form) {
		Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);
		return "user/signup";
	}

	//ユーザー登録処理
	@PostMapping("/signup")
	public String postSignup(Model model, Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult) {

		//入力チェック結果で分岐
		if (bindingResult.hasErrors()) {

			//ダメだったらユーザー登録画面に戻る
			return getSignup(model, locale, form);

		}

		log.info(form.toString());
		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		//ユーザー登録
		userService.signup(user);
		return "redirect:/login";
	}

	//データベース関連の例外処理
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {

		//空の文字をセット
		model.addAttribute("error", "");

		//メッセージをModelに登録
		model.addAttribute("message", "SignUpControllerで例外が発生しました");

		//HTTPのエラーコード（500）をモデルに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";

	}

	//その他の例外処理
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(DataAccessException e, Model model) {

		//空の文字をセット
		model.addAttribute("error", "");

		//メッセージをModelに登録
		model.addAttribute("message", "SignUpControllerで例外が発生しました");

		//HTTPのエラーコード（500）をモデルに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";

	}

}
