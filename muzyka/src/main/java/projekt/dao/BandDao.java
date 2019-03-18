package projekt.dao;
import projekt.domain.Band;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BandDao{

    public Connection getConnection();
    public void setConnection(Connection connection) throws SQLException;
	public List<Band> getAllBands();
	public int addBand(Band band);
	public int deleteBand(Band band);
	public int updateBand(Band band) throws SQLException;
    public Band getBand(long id) throws SQLException;

}