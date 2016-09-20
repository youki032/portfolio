package net.spring.board.security;

import java.security.SecureRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;

public class MyKeyBasedPersistenceTokenService extends KeyBasedPersistenceTokenService {
  protected Logger log = LogManager.getLogger(MyKeyBasedPersistenceTokenService.class);
  private static final int PSEUDO_RANDOM_NUMBER_BYTES = 16;
  private static final Integer SERVER_INTEGER = 1235;
  private static final String SERVER_SECRET = "Verify#@";
  private static final int TOKEN_VALIDITY_SECONDS = 1209600;
  
  
  public MyKeyBasedPersistenceTokenService() throws Exception {
    setPseudoRandomNumberBytes(PSEUDO_RANDOM_NUMBER_BYTES);
    setSecureRandom(getSecureRandom());
    setServerInteger(SERVER_INTEGER);
    setServerSecret(SERVER_SECRET);
    afterPropertiesSet();
    
  }
  
  protected SecureRandom getSecureRandom() throws Exception {
    return (SecureRandom) new SecureRandomFactoryBean().getObject();
  }
  
  public boolean isTokenExpired(String key) {
    Token token = verifyToken(key);
    long tokenExpiryTime = token.getKeyCreationTime();
    tokenExpiryTime += 1000L * TOKEN_VALIDITY_SECONDS;
    
    return tokenExpiryTime > System.currentTimeMillis();
  }
  
  public boolean isSameKey(String inputKey, String savedKey) {
    return inputKey.equals(savedKey);
  }
}
