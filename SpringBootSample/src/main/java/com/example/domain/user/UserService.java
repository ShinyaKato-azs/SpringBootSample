package com.example.domain.user;

import java.util.List;

import com.example.domain.user.model.MUser;

public interface UserService {

	//ユーザー登録
	public void signup(MUser user);

	//ユーザー取得（全件）
	public List<MUser> getUsers(MUser user);

	//ユーザー取得（1件）
	public MUser getUserOne(String userId);

	//ユーザー更新（１件）
	public void updateUserOne(String userId, String password, String userName);

	//ユーザー削除（１件） 
	public void deleteUserOne(String userId);

	//ログインユーザー取得
	public MUser getLoginUser(String userId);
}
