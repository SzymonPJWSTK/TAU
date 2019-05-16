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
import projekt.domain.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Rollback
@Transactional(transactionManager = "txManager")
public class BandManagerTest {

	@Autowired
    BandManager bandManager;

	List<Long> bandIds;
	List<Long> memberIds;

	private Member addMemberHelper(String name, String position){
		Member member = new Member();
		member.setName(name);
		member.setPosition(position);
		member.setId(null);
		Long memberId = bandManager.addMember(member);
		assertNotNull(memberId);
		memberIds.add(memberId);
		return member;
	}

	private Band addBandHelper(String bandName, String genere, int yoe, List<Member> members) {
		Band band = new Band();

		band.setBandName(bandName);
        band.setGenre(genere);
        band.setYoe(yoe);
		band.setMembers(members);

		Long bandId = bandManager.addBand(band);
		bandIds.add(bandId);
	
    	assertNotNull(bandId);
		return band;
	}

	@Before
	public void setup() {
		bandIds = new LinkedList<>();
		memberIds = new LinkedList<>();

		addMemberHelper("Janusz", "Perkusja");
		addMemberHelper("Mirek", "Gitara elektryczna");
		Member member = addMemberHelper("Staszek", "Bass");

		addBandHelper("Test","Rock",2012, new LinkedList<Member>());
		ArrayList<Member> members = new ArrayList<Member>();
		members.add(member);
		addBandHelper("Metallica", "Pop", 1999, members);
	}

	@Test
	public void addBandTest() {
		assertTrue(bandIds.size() > 0);
	}

	@Test
	public void addmemberTest(){
		assertTrue(memberIds.size() > 0);
	}

	@Test (expected = IllegalArgumentException.class)
	public void addBandErrorTest(){
		Band band = new Band();
		band.setId(1);
		band.setBandName("Test");
		band.setGenre("ROCK");
		band.setMembers(new LinkedList<Member>());
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
	public void findMemberByFragmentTest(){
		addMemberHelper("Jan_Test", "Wokal");
		addMemberHelper("Staszek_Test","Bass");
		addMemberHelper("Miroslaw", "Perkusja");

		List<Member> members = bandManager.findMembersByName("Test");
		assertEquals(2,members.size());

		members = bandManager.findMembersByName("Miroslaw");
		assertEquals(1,members.size());
	}

	@Test
	public void getAllBandMembersTest(){
		Member member1 = addMemberHelper("Staszek", "Bass");
		Member member2 = addMemberHelper("Jan", "Perkusja");
		Member member3 = addMemberHelper("Zdzisław", "Wokal");

		ArrayList<Member> members = new ArrayList<Member>();
		members.add(member1);
		members.add(member2);
		members.add(member3);

		addBandHelper("Testowy", "Test", 1989, members);

		Band band = bandManager.findBandById(bandIds.get(bandIds.size() - 1));
		assertNotNull(band);

		assertEquals(3,band.getMembers().size());
		assertEquals(members,band.getMembers());

		for(Member member : band.getMembers())
			assertEquals(member.getBand(), band);
	}

	@Test
	public void swapMemberTest(){
		Member member1 = addMemberHelper("Staszek", "Bass");
		Member member2 = addMemberHelper("Zdzisław", "Wokal");
		ArrayList<Member> members = new ArrayList<Member>();
		members.add(member1);
		members.add(member2);

		addBandHelper("Java", "Rock", 2012, members);
		addBandHelper("Cpp", "Klasyka", 2013, new LinkedList<Member>());

		Band band1 = bandManager.findBandById(bandIds.get(bandIds.size() - 2));
		Band band2 = bandManager.findBandById(bandIds.get(bandIds.size() - 1));
		
		assertEquals("Java",band1.getBandName());
		assertEquals(2,band1.getMembers().size());
		assertEquals("Cpp",band2.getBandName());
		assertEquals(0, band2.getMembers().size());

		bandManager.swapMember(band2,band1, band1.getMembers().get(0));

		band1 = bandManager.findBandById(bandIds.get(bandIds.size() - 2));
		band2 = bandManager.findBandById(bandIds.get(bandIds.size() - 1));

		assertEquals("Java",band1.getBandName());
		assertEquals(1,band1.getMembers().size());
		assertEquals("Cpp",band2.getBandName());
		assertEquals(1, band2.getMembers().size());
		assertNotEquals(band2.getMembers().get(0),band1.getMembers().get(0));
		assertEquals("Staszek", band2.getMembers().get(0).getName());
		assertEquals(band2.getMembers().get(0).getBand(), band2);
	}
}