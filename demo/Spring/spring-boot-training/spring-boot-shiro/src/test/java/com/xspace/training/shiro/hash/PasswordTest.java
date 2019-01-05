package com.xspace.training.shiro.hash;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.xspace.training.shiro.BaseTest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;

@Log4j2
public class PasswordTest extends BaseTest {
  @Test
  public void testPasswordServiceWithMyRealm() {
    login("classpath:shiro-passwordservice.ini", "zeng", "123##");
  }

  @Test
  public void testPasswordServiceWithJdbcRealm() {
    login("classpath:shiro-jdbc-passwordservice.ini", "wu", "123");
  }

  @Test
  public void testGeneratePassword() {
    String algorithmName = "md5";
    String username = "liu";
    String password = "123";
    String salt1 = username;
    String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
    int hashIterations = 2;

    SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
    String encodedPassword = hash.toHex();
    log.info(salt2);
    log.info(encodedPassword);
  }

  @Test
  public void testHashedCredentialsMatcherWithMyRealm2() {
    // 使用testGeneratePassword生成的散列密码
    login("classpath:shiro-hashedCredentialsMatcher.ini", "liu", "123");
  }

  @Test
  public void testHashedCredentialsMatcherWithJdbcRealm() {
    // 使用testGeneratePassword生成的散列密码
    login("classpath:shiro-jdbc-hashedCredentialsMatcher.ini", "liu", "123");
  }

  private class EnumConverter extends AbstractConverter {
    @Override
    protected String convertToString(final Object value) throws Throwable {
      return ((Enum) value).name();
    }

    @Override
    protected Object convertToType(final Class type, final Object value) throws Throwable {
      return Enum.valueOf(type, value.toString());
    }

    @Override
    protected Class getDefaultType() {
      return Enum.class;
    }
  }

  @Test
  public void testRetryLimitHashedCredentialsMatcherWithMyRealm() {
    for(int i = 1; i <= 5; i++) {
      try {
        login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini", "liu", "234");
      } catch (Exception e) {/*ignore*/}
    }
    assertThrows(ExcessiveAttemptsException.class, () -> {
      login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini", "liu", "234");
    });
  }
}
