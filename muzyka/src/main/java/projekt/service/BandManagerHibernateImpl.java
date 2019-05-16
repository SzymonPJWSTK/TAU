package projekt.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import projekt.domain.Band;
import projekt.domain.Member;

@Component
@Transactional
public class BandManagerHibernateImpl implements BandManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	//#region BAND
	@Override
	public Long addBand(Band band) {
        if (band.getId() != null) 
            throw new IllegalArgumentException("Id must be null when adding to database");
		
        sessionFactory.getCurrentSession().persist(band);

		for (Member member : band.getMembers()) {
			member.setBand(band);
			sessionFactory.getCurrentSession().update(member);
		}

		sessionFactory.getCurrentSession().flush();
		return band.getId();
	}

	@Override
    public void updateBand(Band band) {
        sessionFactory.getCurrentSession().update(band);
    }

	@Override
	public Band findBandById(Long id) {
		return (Band) sessionFactory.getCurrentSession().get(Band.class, id);
	}

	@Override
	public void deleteBand(Band band) {
		band = (Band) sessionFactory.getCurrentSession().get(Band.class,
				band.getId());
		
		for (Member member : band.getMembers()) {
			member.setBand(null);
			sessionFactory.getCurrentSession().update(member);
		}

		sessionFactory.getCurrentSession().delete(band);
	}

	@Override
	public List<Band> findAllBands() {
		return sessionFactory.getCurrentSession().getNamedQuery("band.all")
				.list();
	}

/*	@Override
	public List<Band> findBands(String nameFragment) {
		return (List<Band>) sessionFactory.getCurrentSession()
				.getNamedQuery("band.findBands")
				.setString("bandNameFragment", "%"+nameFragment+"%")
				.list();
	}
	*/
	//#endregion

	//#region MEMBER
	@Override
	public Long addMember(Member member){
		return (Long) sessionFactory.getCurrentSession().save(member);
	}

	@Override
	public void updateMember(Member member){

	}

	@Override
	public Member findMemberById(Long id){
		return (Member) sessionFactory.getCurrentSession().get(Member.class, id);
	}

	@Override
	public void deleteMember(Member member){
		if(member.getBand() != null){
			member.getBand().getMembers().remove(member);
            sessionFactory.getCurrentSession().update(member.getBand());
		}

		sessionFactory.getCurrentSession().delete(member);
	}


	@Override
	public List<Member> findAllMembers(){
		return sessionFactory.getCurrentSession().getNamedQuery("member.all").list();
	}

	@Override
	public List<Member> findAvailableMembers(){
		return null;
	}

	@Override
	public List<Member> findMembersByName(String memberNameFragment){
		return (List<Member>) sessionFactory.getCurrentSession()
				.getNamedQuery("member.findMembersByName")
				.setString("memberNameFragment", "%"+memberNameFragment+"%")
				.list();
	}

	@Override
	public void swapMember(Band to, Band from, Member member){
		
		from.getMembers().remove(member);
		to.getMembers().add(member);
		member.setBand(to);

		updateBand(from);
		updateBand(to);

		sessionFactory.getCurrentSession().update(member);
	}
	//#endregion
}