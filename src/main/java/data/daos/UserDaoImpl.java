package data.daos;

import java.util.Calendar;
import java.util.List;

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
        // TODO Auto-generated method stub
        // busco el token según el valor
        System.out.println("@@@@@@@@@@@@@@@@ aqui findByValidTokenValue: " + tokenValue);

        Token token = tokenDao.findByTokenValue(tokenValue);
        // compruebo si no esta espirado, busco el usuario
        Calendar dateActual = Calendar.getInstance();
        if (token != null) {
            if (!token.isTokenExpired(dateActual)) {
                // busco el usuario
                User user = userDao.findByTokenValue(tokenValue);
                System.out.println("@@@@@@@@@@@@@@@@ aqui findByValidTokenValue - user encontrado: " + user.toString());

                return user;
            } else {
                return null;
            }
        } else {
            System.out.println("@@@@@@@@@@@@@@@@ aqui findByValidTokenValue - no encontrado token");

            return null;
        }

    }

}
