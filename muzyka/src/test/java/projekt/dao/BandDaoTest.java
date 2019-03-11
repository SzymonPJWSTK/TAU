package projekt.dao;

import projekt.domain.Band;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RunWith(BlockJUnit4ClassRunner.class)
public class BandDaoTest{

    BandDao bandDao;

    @Before
    public void init(){
        bandDao = new BandDao();
        Band b = new Band();
        b.setId(1L);
        b.setBandName("Informatycy");
        bandDao.bands = new HashMap<Long,Band>();
        bandDao.bands.put(1L,b);

    }

    @Test
    public void bandDaoExistsTest(){
        assertNotNull(bandDao);
    }

    @Test
    public void getBandThatExists(){
        Optional <Band> b = bandDao.get(1L);
        assertThat(b.get().getBandName(), is("Informatycy"));
    }

    @Test
    public void saveNewBandTest(){
        Band band = new Band();
        band.setId(2L);
        band.setBandName("Testowallnica");
        bandDao.save(band);

        Optional <Band> b = bandDao.get(band.getId());
        assertThat(b.get().getBandName(), is("Testowallnica"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNewBandOnExistingIndex(){
        Band band = new Band();
        band.setId(1L);
        band.setBandName("Informatycy");
        bandDao.save(band);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNotExistingBand(){
        Band updatedBand = new Band();
        updatedBand.setId(2L);
        updatedBand.setBandName("Nowi Informatycy");
        bandDao.update(updatedBand);
    }

    @Test
    public void updateExistingBand(){
        Band updatedBand = new Band();
        updatedBand.setId(1L);
        updatedBand.setBandName("Nowi Informatycy");
        bandDao.update(updatedBand);
        
        Optional <Band> b = bandDao.get(1L);
        assertThat(b.get().getBandName(), is("Nowi Informatycy"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNotExistingBand(){
        Band bandToDelet = new Band();
        bandToDelet.setId(2L);
        bandDao.delete(bandToDelet);
    }

    @Test
    public void deleteExistingBand(){
        Band bandToDelete = new Band();
        bandToDelete.setId(1L);
        bandDao.delete(bandToDelete);

        Optional <Band> b = bandDao.get(bandToDelete.getId());
        assertThat(b, is(Optional.empty()));
    }
}