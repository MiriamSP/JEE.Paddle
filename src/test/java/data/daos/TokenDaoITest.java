package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Token;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TokenDaoITest {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DaosService daosService;

    @Test
    public void testFindByUser() {
        Token token = (Token) daosService.getMap().get("tu1");
        Token token2 = tokenDao.findByUser(token.getUser()).get(0);
        assertEquals(token, token2);
    }

    @Test
    public void testDeleteExpiredTokens() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        userDao.saveAndFlush(user);
        assertEquals(4, tokenDao.count());
        Token token = new Token(user);
        tokenDao.saveAndFlush(token);
        Calendar dateAnt = Calendar.getInstance();
        assertTrue(!token.isTokenExpired(dateAnt));
        dateAnt.add(Calendar.MINUTE, -69);
        token.setDateCreated(dateAnt);
        tokenDao.saveAndFlush(token);
        assertEquals(5, tokenDao.count());
        tokenDao.deleteExpiredTokens();
        assertEquals(4, tokenDao.count());
    }

    @Test
    public void testFindByTokenValue1() {
        Token t2 = (Token) daosService.getMap().get("tu2");
        assertNotNull(tokenDao.findByTokenValue(t2.getValue()));
    }

    @Test
    public void testFindByTokenValue2() {
        User u1 = userDao.findByUsernameOrEmail("u1");
        List<Token> ltoken = tokenDao.findByUser(u1);
        assertEquals(1, ltoken.size());
        String tokenValue = ltoken.get(0).getValue();
        Token t = tokenDao.findByTokenValue(tokenValue);
        assertNotNull(t);
        assertEquals(ltoken.get(0), t);
    }

}
