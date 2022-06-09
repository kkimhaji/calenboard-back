package jejunu.portal.calenboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode(){
        String pwd = "1111";
        String enPwd = passwordEncoder.encode(pwd);
        boolean isMatch = passwordEncoder.matches(pwd, enPwd);
    }
}
