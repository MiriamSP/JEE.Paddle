package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    private DaosService daosService;

    @Test
    public void testFindByUser() {
        Token token = (Token) daosService.getMap().get("tu1");
        User user = (User) daosService.getMap().get("u4");
        assertEquals(token, tokenDao.findByUser(token.getUser()));
        assertNull(tokenDao.findByUser(user));
    }
    
    @Test
    public void testDeleteExpiredTokens() {
        Token token = (Token) daosService.getMap().get("tu1");
        User user = (User) daosService.getMap().get("u4");
        System.out.println("=========================================================================");
        System.out.println("Num total tokens: " + tokenDao.count());
        Calendar dateAnt  = Calendar.getInstance();
        dateAnt.add(Calendar.MINUTE,-69);
        token.setDateCreated(dateAnt);
        tokenDao.save(token);
        System.out.println("Estado token: " + token.detailsTokenStatus());
        tokenDao.deleteExpiredTokens();
        //assertEquals(token, tokenDao.findByUser(token.getUser()));
        System.out.println("Num total tokens: " + tokenDao.count());
        assertNull(tokenDao.findByUser(user));
    }

}
