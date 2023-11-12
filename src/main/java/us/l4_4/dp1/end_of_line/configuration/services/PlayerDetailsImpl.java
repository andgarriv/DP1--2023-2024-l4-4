package us.l4_4.dp1.end_of_line.configuration.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import us.l4_4.dp1.end_of_line.player.Player;

public class PlayerDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@JsonIgnore
	private String name;

	@JsonIgnore
	private String surname;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String email;
	
	@JsonIgnore
	private Date birthDate;

	private String nickname;

	@JsonIgnore
	private String avatar;

	private Collection<? extends GrantedAuthority> authorities;

	public PlayerDetailsImpl(Integer id, String nickname, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.authorities = authorities;
	}

	public static PlayerDetailsImpl build(Player player) {
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(player.getAuthority().getAuthority()));

		return new PlayerDetailsImpl(player.getId(), player.getNickname(),
				player.getPassword(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}


	public Integer getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return nickname;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerDetailsImpl other = (PlayerDetailsImpl) obj;
		return Objects.equals(id, other.id);
	}

}
