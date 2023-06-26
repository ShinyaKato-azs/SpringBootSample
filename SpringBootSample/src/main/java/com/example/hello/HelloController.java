package com.example.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	@Autowired
	private HelloService service;

	@GetMapping("/hello")
	public String getHello() {
		//hello.htmlへ。拡張子は省略。
		return "hello";
	}

	@PostMapping("/hello")
	public String postRequest(@RequestParam("text1") String str, Model model) {
		//フォームから受け取った文字列をModelに登録
		model.addAttribute("sample", str);
		//response.htmlに画面を遷移させる
		return "hello/response";
	}

	@PostMapping("/hello/db")
	public String postDbRequest(@RequestParam("text2") String id, Model model) {

		Employee employee = service.getEmployee(id);

		model.addAttribute("employee", employee);

		return "hello/db";
	}

}
