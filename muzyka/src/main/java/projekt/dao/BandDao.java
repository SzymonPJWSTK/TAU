package projekt.dao;
import projekt.domain.Band;

import java.util.Optional;
import java.util.Map;
import java.util.List;

public class BandDao implements Dao<Band>{

    protected Map<Long,Band> bands;

    @Override
    public Optional<Band> get (Long id){
        return Optional.ofNullable(bands.get(id));
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