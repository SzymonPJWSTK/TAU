package projekt.service;
import java.util.List;

import projekt.domain.Member;
import projekt.domain.Band;

public interface BandManager {

	Long addBand(Band band);
	List<Band> findAllBands();
	Band findBandById(Long id);
	void deleteBand(Band band);
	void updateBand(Band band);
	

	Long addMember(Member member);
	void updateMember(Member member);
	Member findMemberById(Long id);
	void deleteMember(Member member);
	void swapMember(Band to, Band from, Member member);


	List<Member> findAllMembers();
	List<Member> findAvailableMembers();
	List<Member> findMembersByName(String memberNameFragment);
}