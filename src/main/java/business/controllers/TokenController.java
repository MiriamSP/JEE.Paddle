package business.controllers;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import data.daos.TokenDao;
import data.daos.UserDao;
import data.entities.Token;
import data.entities.User;

@Controller
@Transactional
public class TokenController {

    private TokenDao tokenDao;

    private UserDao userDao;

    @Autowired
    public void setTokenDao(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public void deleteExpiredToken(){
        List<Token> token = tokenDao.findAllTokens();
        //token.isTokenExpired(Calendar.getInstance().getTimeInMillis());
        
        
    }

    public String login(String username) {
         
        User user = userDao.findByUsernameOrEmail(username);
        assert user != null;
        Token token = new Token(user);
        tokenDao.save(token);
        return token.getValue();
    }
}
