package com.capg.expensetracker.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.capg.expensetracker.model.UserInformation;

import lombok.Data;



@Data
public class UserPrincipal implements UserDetails{
	
	private String username; 
	
	private String name;

	private String password;

	private long mobileNumber;
	
	private String email;

	private long balance;
	
	 private Collection<? extends GrantedAuthority> authorities;
	
	
	
	public UserPrincipal(String name, String password, long mobileNumber, String email, long balance,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.name = name;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.username = email;
		this.email = email;
		this.balance = balance;
		this.authorities= authorities;
	}
	
	
	
	 public static UserPrincipal create(UserInformation user) {

		 
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		 
		 authorities.add(new SimpleGrantedAuthority("USER"));
		 
	        return new UserPrincipal(
	                user.getName(),
	                user.getPassword(),
	                user.getMobileNumber(),
	                user.getEmail(),
	                user.getBalance(),
	               authorities
	        );
	    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;

	}

	
	
	
	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		return email;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email);
    }
	

}
