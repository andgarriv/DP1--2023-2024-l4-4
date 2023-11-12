package us.l4_4.dp1.end_of_line.auth.payload.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Integer id;
	private String nickname;
	private List<String> roles;

	public JwtResponse(String accessToken, Integer id, String nickname, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.nickname = nickname;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "JwtResponse [token=" + token + ", type=" + type + ", id=" + id + ", nickname=" + nickname
				+ ", roles=" + roles + "]";
	}

}
