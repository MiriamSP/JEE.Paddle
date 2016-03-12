package data.daos;

import java.util.Calendar;
import java.util.List;

import data.entities.Token;

public class TokenDaoImpl {
    
    private TokenDao tokenDao;

    public void deleteExpiredToken() {
        List<Token> lToken = tokenDao.findAll();
        Calendar dateActual = Calendar.getInstance();
        for (Token token : lToken) {
            if (token.isTokenExpired(dateActual)) {
                tokenDao.delete(token);
            }
        }        
    }

}
