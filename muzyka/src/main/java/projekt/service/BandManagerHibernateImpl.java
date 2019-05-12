package projekt.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import projekt.domain.Band;

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
	
	@Override
	public Long addBand(Band band) {
        if (band.getId() != null) 
            throw new IllegalArgumentException("Id must be null when adding to database");
		
        sessionFactory.getCurrentSession().persist(band);
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

		sessionFactory.getCurrentSession().delete(band);
	}

	@Override
	public List<Band> findAllBands() {
		return sessionFactory.getCurrentSession().getNamedQuery("band.all")
				.list();
	}

	@Override
	public List<Band> findBands(String nameFragment) {
		return (List<Band>) sessionFactory.getCurrentSession()
				.getNamedQuery("band.findBands")
				.setString("bandNameFragment", "%"+nameFragment+"%")
				.list();
	}
}