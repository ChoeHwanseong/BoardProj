package model.member;

import java.sql.Date;

public class MemberDto {
	private String username;
	private String password;
	private String name;
	private Date regiDate;
	private String address;
	private String telNumber;
	private String gender;
	private String interest;
	private String edu;
	private String introduce;
	private String profile;

	public static class builder{
		private String username;
		private String password;
		private String name;
		private Date regiDate;
		private String address;
		private String telNumber;
		private String gender;
		private String interest;
		private String edu;
		private String introduce;
		private String profile;
		public builder username(String username) {
			this.username = username;
			return this;
		}
		public builder password(String password) {
			this.password = password;
			return this;
		}
		public builder name(String name) {
			this.name = name;
			return this;
		}
		public builder regiDate(Date regiDate) {
			this.regiDate = regiDate;
			return this;
		}
		public builder address(String address) {
			this.address = address;
			return this;
		}
		public builder telNumber(String telNumber) {
			this.telNumber = telNumber;
			return this;
		}
		public builder gender(String gender) {
			this.gender = gender;
			return this;
		}
		public builder interest(String interest) {
			this.interest = interest;
			return this;
		}
		public builder edu(String edu) {
			this.edu = edu;
			return this;
		}
		public builder introduce(String introduce) {
			this.introduce = introduce;
			return this;
		}
		public builder profile(String profile) {
			this.profile = profile;
			return this;
		}
		
		public MemberDto build() {
			return new MemberDto(this);
		}
	}//builder

	public MemberDto(builder builder) {
		this.username = builder.username;
		this.password = builder.password;
		this.name = builder.name;
		this.regiDate = builder.regiDate;
		this.address = builder.address;
		this.telNumber = builder.telNumber;
		this.gender = builder.gender;
		this.interest = builder.interest;
		this.edu = builder.edu;
		this.introduce = builder.introduce;
		this.profile = builder.profile;
	}
	public static MemberDto.builder builder() {
		return new MemberDto.builder();
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegiDate() {
		return regiDate;
	}

	public void setRegiDate(Date regiDate) {
		this.regiDate = regiDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
}
