package data.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import data.entities.Token;

@Repository
public class TokenDaoImpl implements TokenDaoExtended {

    @Autowired
    private TokenDao tokenDao;

    @Override
    public void deleteExpiredTokens() {
        List<Token> lToken = tokenDao.findAll();
        for (Token token : lToken) {
            if (token.isTokenExpired()) {
                tokenDao.delete(token);
            }
        }  
    }   
    
}
