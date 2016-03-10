package data.entities;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

import data.entities.Token;
import data.entities.User;

public class TokenTest {

    @Test
    public void testTokenUser() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user);
        assertTrue(token.getValue().length() > 20);
        // Test Date Expired        
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MINUTE,59);
        assertTrue(!token.isTokenExpired(date));
    }
    
    

}
