package projekt.dao;
import projekt.domain.Band;

import java.util.Optional;
import java.util.List;

public class BandDao implements Dao<Band>{

    @Override
    public Optional<Band> get (int id){
        return Optional.empty();
    }

    @Override
    public List<Band> getAll() {
        return null;
    }

    @Override
    public void save(Band o) {

    }

    @Override
    public void update(Band o){
    }

    @Override
    public void delete(Band o) {
    }
}