package data.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Calendar;
import org.junit.Test;
import data.entities.Token;
import data.entities.User;

public class TokenTest {

    @Test
    public void testTokenUser() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user);
        assertFalse(token.isTokenExpired());
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MINUTE, -61);
        token.setDateCreated(date);
        assertTrue(token.isTokenExpired());
    }

}
