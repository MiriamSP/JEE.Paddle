package data.daos;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import data.entities.Token;
import data.entities.User;

@Repository
public class UserDaoImpl implements UserDaoExtended {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    @Override
    public User findByValidTokenValue(String tokenValue) {
        Token token = tokenDao.findByTokenValue(tokenValue);
        Calendar dateActual = Calendar.getInstance();
        if (token != null) {
            if (!token.isTokenExpired(dateActual)) {
                User user = userDao.findByTokenValue(tokenValue);
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
