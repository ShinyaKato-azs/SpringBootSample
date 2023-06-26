package com.example.hello;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

	@Autowired
	private HelloRepository repository;

	/**従業員を1人取得する*/
	public Employee getEmployee(String id) {
		//レポジトリで検索した結果をそのまま変数mapに格納
		Map<String, Object> map = repository.findById(id);

		//map（データ構造）から値（データ）を取得。テーブルのカラム名がキーになってる。
		//mapクラスのgetメソッドは、引数と一致するカラム名のデータを返す。
		String employeeId = (String) map.get("id");
		String name = (String) map.get("name");
		int age = (Integer) map.get("age");

		//取得した値をEmployeeクラスのフィールドにセット
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeName(name);
		employee.setEmployeeAge(age);

		return employee;
	}

}
