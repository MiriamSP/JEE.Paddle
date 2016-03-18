package data.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import data.entities.Token;
import data.entities.User;

public interface TokenDao extends JpaRepository<Token, Integer>, TokenDaoExtended {

    List<Token> findByUser(User user);
    
    @Query("select token from Token token where token.value = ?1")
     public Token findByTokenValue(String tokenValue);
    
    @Query("select token from Token token where token.id = ?1")
    public Token findByTokenValue2(String id);
    
    

}
