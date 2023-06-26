package com.example.hello;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HelloRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//MapのStringにはカラム名（キー）,Objectにはselect文の結果の値が入る
	public Map<String, Object> findById(String id) {

		//select文
		String query = "SELECT*"
				+ " FROM employee"
				+ " WHERE id=?";
		//検索実行
		Map<String, Object> employee = jdbcTemplate.queryForMap(query, id);

		return employee;

	}

}
