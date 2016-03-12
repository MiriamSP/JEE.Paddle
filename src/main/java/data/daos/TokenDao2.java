package data.daos;

import java.util.Calendar;
import java.util.List;

import data.entities.Token;

public interface TokenDao2 {

    public void deleteExpiredToken();
    
    
}
