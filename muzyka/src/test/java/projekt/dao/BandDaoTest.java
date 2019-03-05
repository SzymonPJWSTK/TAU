package projekt.dao;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class BandDaoTest{

    BandDao bandDao;

    @Before
    public void init(){
        bandDao = new BandDao();
    }

    @Test
    public void bandDaoExistsTest(){
        assertNotNull(bandDao);
    }
}