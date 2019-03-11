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
    public void save(Band o) throws IllegalArgumentException {
        if(bands.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does exist");
    
        bands.put(o.getId(),o);
    }

    @Override
    public void update(Band o) throws IllegalArgumentException {
        if(!bands.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does not exist");

        bands.put(1L,o);
    }

    @Override
    public void delete(Band o) throws IllegalArgumentException {
        if(!bands.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does not exist");
        
        bands.remove(o.getId());
    }
}