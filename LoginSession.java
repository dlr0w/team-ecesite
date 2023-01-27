package jp.co.internous.garnet.model.session;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class LoginSession implements Serializable {
	private static final long serialVersionUID = -4505629762363906244L;
	
	private int userId;
	private int tmpUserId;
	private String userName;
	private String password;
	private boolean loginFlag;
	
	//userIdを取得
	public int getUserId() {
		return userId;
	}
	
	//userIdをセット
	public void setUserId(int userId) {
		this.userId = userId;
	}

	//仮ユーザーidを取得
	public int getTmpUserId() {
		return tmpUserId;
	}
	
	//仮ユーザーidをセット 
	public void setTmpUserId(int tmpUserId) {
		this.tmpUserId = tmpUserId;
	}

	/*ユーザー名を取得
	 * 
	 */
	public String getUserName() {
		return userName;
	}
	
	//ユーザー名をセット
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//パスワードを取得
	public String getPassword() {
		return password;
	}
	
	//パスワードをセット
	public void setPassword(String password) {
		this.password = password;
	}

	//ログイン成功か失敗か
	public boolean isLoginFlag() {
		return loginFlag;
	}

	//ログイン失敗か成功かをセット
	public void setLoginFlag(boolean loginFlag) {
		this.loginFlag = loginFlag;
	}
		
	//ユーザーid、仮ユーザーid、ユーザーネーム、パスワード、ログインフラグをセット
	public void setAll(int userId, int tmpUserId, String userName, String password, boolean loginFlag) {
		this.userId = userId;
		this.tmpUserId = tmpUserId;
		this.userName = userName;
		this.password = password;
		this.loginFlag = loginFlag;
	}
}