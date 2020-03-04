package br.com.hevermc.authentication.api;

import br.com.hevermc.authentication.Authentication;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginPlayer {

	String name, password, ip;
	boolean logged = false;
	boolean captcha = false;
	int pin;

	public LoginPlayer(String name, String ip) {
		this.name = name.toLowerCase();
		this.ip = ip;
	}

	public void update() {
		Authentication.logins.getConfig().set(name + ".password", this.password);
		Authentication.logins.getConfig().set(name + ".pin", this.pin);
		Authentication.logins.getConfig().set(name + ".ip", this.ip);
		Authentication.logins.save();
	}

	public boolean isRegistred() {
		if (!(Authentication.logins.getConfig().get(name + ".password") == null || Authentication.logins.getConfig().get(name  +".password").equals("null")))
			return true;
		return false;
	}

	public LoginPlayer load() {
		if (Authentication.logins.getConfig().contains(name)) {
			password = Authentication.logins.getConfig().getString(name + ".password");
			pin = Authentication.logins.getConfig().getInt(name + ".pin");
		}
		return this;
	}

	public void unload() {

		name = null;
		password = null;
		ip = null;
		captcha = false;
		logged = false;
		pin = 0;

	}

}
