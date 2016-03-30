package api;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Test;

import business.api.Uris;

public class TokenResourceFunctionalTesting {

    @Test
    public void testLoginPlayer() {
        String token = new RestService().registerAndLoginPlayer();
        assertTrue(token.length() > 20);
        LogManager.getLogger(this.getClass()).info("testLoginPlayer (token:" + token + ")");
    }
    
    @Test
    public void testDeleteExpiredToken() {
        String token = new RestService().registerAndLoginPlayer();
        new RestBuilder<Object>(RestService.URL).path(Uris.TOKENS).basicAuth(token, "").body(token).delete().build();
        LogManager.getLogger(this.getClass()).info("testExpiredToken (token:" + token + ")");
    }
    
    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
