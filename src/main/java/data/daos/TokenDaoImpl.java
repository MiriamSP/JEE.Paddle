package data.daos;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import data.entities.Token;

@Repository
public class TokenDaoImpl implements TokenDaoExtended {

    @Autowired
    private TokenDao tokenDao;

    @Override
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
