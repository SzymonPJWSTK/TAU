package projekt.service;
import java.util.List;

import projekt.domain.Band;

public interface BandManager {

	Long addBand(Band band);
	void updateBand(Band band);
	Band findBandById(Long id);
	void deleteBand(Band band);
	List<Band> findAllBands();
	List<Band> findBands(String nameFragment);
}