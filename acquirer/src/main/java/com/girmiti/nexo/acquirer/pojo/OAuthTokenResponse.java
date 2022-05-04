package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OAuthTokenResponse implements Serializable {

	private static final long serialVersionUID = 1309224350868321781L;

	private String access_token;

	private String refresh_token;

	private String token_type;

	private Integer expires_in;

	private String value;

	private Integer expiresIn;

	private Long expiration;

	private String tokenType;

	private List<String> scope;

	private Map additionalInformation;

	private Boolean expired;

	private OAuthTokenResponse refreshToken;

}
