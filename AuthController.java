package jp.co.internous.garnet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jp.co.internous.garnet.model.domain.MstUser;
import jp.co.internous.garnet.model.form.UserForm;
import jp.co.internous.garnet.model.mapper.MstUserMapper;
import jp.co.internous.garnet.model.mapper.TblCartMapper;
import jp.co.internous.garnet.model.session.LoginSession;

@RestController
@RequestMapping("/garnet/auth")
public class AuthController {
	
	@Autowired
	private LoginSession loginSession;
	
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private TblCartMapper tblCartMapper;
	
	Gson gson = new Gson();
	
	//ログイン処理
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String loginApi(@RequestBody UserForm form) {
		MstUser user = userMapper.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		/*ログイン認証*/
		if (user != null) {
			//ログインセッションの更新
			tblCartMapper.updateUserId(loginSession.getTmpUserId(), user.getId());
			//カートにユーザーidを紐づけ
			loginSession.setAll(user.getId(), 0, user.getUserName(), user.getPassword(), true);
		}else {
			loginSession.setAll(0, 0, null, null, false);
		}
		return gson.toJson(user);
	}
	
	//ログアウト処理
	@RequestMapping(value="/logout")
	@ResponseBody
	public String logoutApi() {
		loginSession.setAll(0, 0, null, null, false);
		return "";
	}
	
	//パスワード再設定処理
	@RequestMapping(value="/resetPassword",method = RequestMethod.POST)
	@ResponseBody
	public String resetPassword(@RequestBody UserForm form) {
		MstUser user = userMapper.findByUserNameAndPassword(loginSession.getUserName(), form.getPassword());	
		//DBの会員情報マスターテーブルに一致しているユーザーが存在しているか確認
		if(user == null) {
			return "現在のパスワードが正しくありiません。";
		}
		//現在のパスワードと新しいパスワードが一致していないか確認
		if(loginSession.getPassword().equals(form.getNewPassword())) {
			return "現在のパスワードと同一文字列が入力されました。";
		}
		//新しいパスワードと新しいパスワード確認が一致しているか確認
		if(!(form.getNewPassword().equals(form.getNewPasswordConfirm()))) {
			return "新パスワードと確認用パスワードが一致しません。";
		}
		userMapper.updatePassword(user.getUserName(), form.getNewPassword());
		loginSession.setPassword(form.getNewPassword());
		return "パスワードが再設定されました。";
	}
}