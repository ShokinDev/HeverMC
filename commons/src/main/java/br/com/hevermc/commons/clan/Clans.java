package br.com.hevermc.commons.clan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Clans {

	String name;
	int xp;
	String member1;
	String member2;
	String member3;
	String member4;
	String member5;
	String owner;
	String admin1;
	String admin2;

	public Clans(String name, String owner, String member1, String member2, String member3, String member4, String member5,
			String admin1, String admin2, int xp) {
		this.name = name;
		this.owner = owner;
		this.member1 = member1;
		this.member2 = member2;
		this.member3 = member3;
		this.member4 = member4;
		this.member5 = member5;
		this.admin1 = admin1;
		this.admin2 = admin2;
		this.xp = xp;
	}

}
