package data.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import data.entities.Court;

@Service
public class CourtService {

    private List<Court> courtList;

    @NotNull
    private int id;

    public CourtService() {
        courtList = new ArrayList<>();
        courtList.add(new Court(1));
        courtList.add(new Court(2));
        courtList.add(new Court(3));
        id = 3;
    }

    public List<Court> findAll() {
        return courtList;
    }

    public Court findOne(int id) {
        for (Court court : courtList) {
            if (court.getId() == id) {
                return court;
            }
        }
        return null;
    }

    public boolean save(Court court) {
        if (courtList.contains(court)) {
            return false;
        } else {
            courtList.add(court);
            return true;
        }
    }

    public void delete(int id) {
        courtList.remove(new Court(id));
    }

    public int generateId() {
        return courtList.size()+1;
    }

}
