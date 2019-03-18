package projekt.domain;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class BandTest{
    @Test
    public void createobjectTest(){
        Band b = new Band();
        assertNotNull(b);
    }

    @Test
    public void gettersAndSettersTest(){
        Band b = new Band();
        b.setId(2);
        b.setBandName("Informatycy");

        assertEquals(2,b.getId().longValue());
        assertEquals("Informatycy",b.getBandName());
    }
}