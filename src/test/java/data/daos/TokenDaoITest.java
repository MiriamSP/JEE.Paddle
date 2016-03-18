package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        User user = (User) daosService.getMap().get("u4");
        Token token2 = tokenDao.findByUser(token.getUser()).get(0);
        assertEquals(token, token2);
        //assertNull(tokenDao.findByUser(user));
    }
    
    @Test
    public void testDeleteExpiredTokens() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        userDao.saveAndFlush(user);
        assertEquals(4, tokenDao.count());
        Token token = new Token(user);
        tokenDao.saveAndFlush(token);
        Calendar dateAnt  = Calendar.getInstance();
        assertTrue(!token.isTokenExpired(dateAnt) );
        dateAnt.add(Calendar.MINUTE,-69);
        token.setDateCreated(dateAnt);
        tokenDao.saveAndFlush(token);
        assertEquals(5, tokenDao.count());
        tokenDao.deleteExpiredTokens();
        assertEquals(4, tokenDao.count());
    }
    
    

}
