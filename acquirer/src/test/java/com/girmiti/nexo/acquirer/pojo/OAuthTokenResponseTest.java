package com.girmiti.nexo.acquirer.pojo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class OAuthTokenResponseTest {
	@InjectMocks
	OAuthTokenResponse oAuthTokenResponse;

	@Mock
	NexoAcquirerDataResponse nexoAcquirerDataResponse;
	
	private static final String ACCESS_TOKEN="acces_token";
	private static final String REFRESH_TOKEN="refresh token";
	private static final String TOKEN_TYPE="token type";
	private static final Integer EXPIRES_IN=12345;
	private static final String VALUE="value";
	private static final Integer expiresIn=12345;
	private static final Long EXPIRATION=54321l;
	private static final String tokenType="tokenType";
	private static final List<String> SCOPE=new ArrayList<String>();
	private static final Boolean EXPIRED=true;
	 Map additionalInformation;
	 OAuthTokenResponse refreshToken;


	@Before
	public void setUp() {
	oAuthTokenResponse = new OAuthTokenResponse();
	nexoAcquirerDataResponse=new NexoAcquirerDataResponse();
	nexoAcquirerDataResponse.setResponse("responce");
	oAuthTokenResponse.setAccess_token(ACCESS_TOKEN);
	oAuthTokenResponse.setRefresh_token(REFRESH_TOKEN);
	oAuthTokenResponse.setToken_type(TOKEN_TYPE);
	oAuthTokenResponse.setExpires_in(EXPIRES_IN);
	oAuthTokenResponse.setValue(VALUE);
	oAuthTokenResponse.setExpiresIn(EXPIRES_IN);
	oAuthTokenResponse.setExpiration(EXPIRATION);
	oAuthTokenResponse.setTokenType(TOKEN_TYPE);
	oAuthTokenResponse.setScope(SCOPE);
	oAuthTokenResponse.setAdditionalInformation(additionalInformation);
	oAuthTokenResponse.setExpired(EXPIRED);
	oAuthTokenResponse.setRefreshToken(refreshToken);
	}

	@Test
	public void testOAuthTokenResponse(){
	Assert.assertEquals(ACCESS_TOKEN,oAuthTokenResponse.getAccess_token());
	Assert.assertEquals(REFRESH_TOKEN,oAuthTokenResponse.getRefresh_token());
	Assert.assertEquals(TOKEN_TYPE,oAuthTokenResponse.getToken_type());
	Assert.assertEquals(EXPIRES_IN,oAuthTokenResponse.getExpires_in());
	Assert.assertEquals(VALUE,oAuthTokenResponse.getValue());
	Assert.assertEquals(EXPIRES_IN,oAuthTokenResponse.getExpiresIn());
	Assert.assertEquals(EXPIRATION,oAuthTokenResponse.getExpiration());
	Assert.assertEquals(TOKEN_TYPE,oAuthTokenResponse.getTokenType());
	Assert.assertEquals(SCOPE,oAuthTokenResponse.getScope());
	Assert.assertEquals(additionalInformation,oAuthTokenResponse.getAdditionalInformation());
	Assert.assertEquals(EXPIRED,oAuthTokenResponse.getExpired());
	Assert.assertEquals(refreshToken,oAuthTokenResponse.getRefreshToken());
	Assert.assertNotNull(nexoAcquirerDataResponse.getResponse());
	}


}
