package projekt.service;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import projekt.domain.Band;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Rollback
@Transactional(transactionManager = "txManager")
public class BandManagerTest {

	@Autowired
    BandManager bandManager;

	List<Long> bandIds;

	private Band addBandHelper(String bandName, String genere, int yoe) {
		Band band = new Band();

		band.setBandName(bandName);
        band.setGenre(genere);
        band.setYoe(yoe);

		Long bandId = bandManager.addBand(band);
		bandIds.add(bandId);
	
    	assertNotNull(bandId);
		return band;
	}

	@Before
	public void setup() {
		bandIds = new LinkedList<>();
		addBandHelper("Test", "Testwes", 2);
	}

	@Test
	public void correctSetupTest() {
		assertTrue(bandIds.size() > 0);
	}

	@Test
	public void addBandTest() {
		int prevSize = bandManager.findAllBands().size();
		
		Band band = addBandHelper("Test123", "Testowy", 2012);

		assertEquals(prevSize+1,bandManager.findAllBands().size());
	}

	@Test (expected = IllegalArgumentException.class)
	public void addBandErrorTest(){
		Band band = new Band();
		band.setId(1);
		band.setBandName("Test");
		band.setGenre("ROCK");
		bandManager.addBand(band);
	}

	@Test
	public void getBandByIdTest() {
		Band band = bandManager.findBandById(bandIds.get(0));
		assertEquals("Test",band.getBandName());
	}

	@Test
	public void getAllBandsTest() {
		List <Long> ids = new LinkedList<>();

		for (Band band: bandManager.findAllBands()) {
				ids.add(band.getId());
		}
		
		assertEquals(bandIds.size(), ids.size());
	}

	@Test
	public void deleteBandTest() {
		int prevSize = bandManager.findAllBands().size();
		Band band = bandManager.findBandById(bandIds.get(0));
		assertNotNull(band);
		bandManager.deleteBand(band);

		assertNull(bandManager.findBandById(bandIds.get(0)));
		assertEquals(prevSize-1,bandManager.findAllBands().size());
	}

	@Test
	public void updateBandTest() {
		Band band = bandManager.findBandById(bandIds.get(0));
		assertNotEquals(band.getBandName(),"Test321");


		band.setBandName("Test321");
		band.setGenre("Pop");
		band.setYoe(1997);
		bandManager.updateBand(band);
		Band updatedBand = bandManager.findBandById(bandIds.get(0));
		assertEquals(updatedBand.getBandName(),"Test321");
		assertEquals(updatedBand.getGenre(), "Pop");
		assertEquals(updatedBand.getYoe().intValue(), 1997);
	}

	@Test
	public void findBandsByNameFragment() {
		List<Band> bands = bandManager.findBands("est");
		assertTrue(bands.get(0).getBandName().contains("est"));
	}
}