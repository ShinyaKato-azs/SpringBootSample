package com.example.domain.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.UserService;
import com.example.domain.user.model.MUser;
import com.example.repository.UserRepository;

@Service
@Primary
public class UserServiceImpl2 implements UserService {

	@Autowired
	private UserRepository repository;

	//	@Autowired
	//	private PasswordEncoder encoder;

	//ユーザー登録
	@Transactional
	@Override
	public void signup(MUser user) {
		//存在チェック
		boolean exists = repository.existsById(user.getUserId());
		if (exists) {
			throw new DataAccessException("データが既に存在します") {
			};
		}
		//部署と権限は自動的に決まる
		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");

		//パスワードを暗号化。セキュリティ入れるとログイン画面が変になるからひとまずオフ
		//		String rawPassword = user.getPassword();
		//		user.setPassword(encoder.encode(rawPassword));
		//
		//		//insert
		//		repository.save(user);
		//パスワードを格納。暗号化しないならここの処理いらないかも
		String rawPassword = user.getPassword();
		user.setPassword(rawPassword);
		repository.save(user);

	}

	//ユーザー取得（全て）
	@Override
	public List<MUser> getUsers(MUser user) {
		return repository.findAll();
	}

	//ユーザー取得（１件）
	@Override
	public MUser getUserOne(String userId) {
		Optional<MUser> option = repository.findById(userId);
		MUser user = option.orElse(null);
		return user;
	}

	//ユーザー更新（１件）
	@Transactional
	@Override
	public void updateUserOne(String userId, String password, String userName) {

		repository.updateUser(userId, password, userName);

	}

	//ユーザー削除（１件）
	@Transactional
	@Override
	public void deleteUserOne(String userId) {
		repository.deleteById(userId);
	}

	//ログインユーザー取得
	@Override
	public MUser getLoginUser(String userId) {
		Optional<MUser> option = repository.findById(userId);
		MUser user = option.orElse(null);
		return user;
	}

}
