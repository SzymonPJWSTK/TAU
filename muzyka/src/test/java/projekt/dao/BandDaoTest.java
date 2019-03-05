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

}