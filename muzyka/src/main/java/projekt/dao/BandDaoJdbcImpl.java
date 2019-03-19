package projekt.dao;
import projekt.domain.Band;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BandDaoJdbcImpl implements BandDao {

    private Connection connection;

    private PreparedStatement addBandStmt;
    private PreparedStatement getAllBandsStmt;
    private PreparedStatement deleteBandStmt;
    private PreparedStatement getBandStmt;
    private PreparedStatement updateBandStmt;

    public BandDaoJdbcImpl (Connection connection) throws SQLException{
        this.connection = connection;
        setConnection(connection);
    }

    @Override
    public Connection getConnection(){
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException{
        addBandStmt = connection.prepareStatement(
            "INSERT INTO Band (name, yoe) VALUES (?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        deleteBandStmt = connection.prepareStatement("DELETE FROM Band where id = ?");
        getAllBandsStmt = connection.prepareStatement("SELECT id, name, yoe FROM Band ORDER BY id");
        getBandStmt = connection.prepareStatement("SELECT id, name, yoe FROM Band WHERE id = ?");
        updateBandStmt = connection.prepareStatement("UPDATE Band SET name=?,yoe=? WHERE id = ?");
    }
	
    @Override
    public List<Band> getAllBands(){
        List<Band> bands = new LinkedList<Band>();
        try {
            ResultSet rs = getAllBandsStmt.executeQuery();

            while (rs.next()) {
                Band b = new Band();
                b.setId(rs.getInt("id"));
                b.setBandName(rs.getString("name"));
                b.setYoe(rs.getInt("yoe"));
                bands.add(b);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        
        return bands;
    }
	
    @Override
    public int addBand(Band band){
        int count = 0;
        try {
            addBandStmt.setString(1, band.getBandName());
            addBandStmt.setInt(2, band.getYoe());
            count = addBandStmt.executeUpdate();
            ResultSet generatedKeys = addBandStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                band.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }

        return count;
    }
	
    @Override
    public int deleteBand(Band band) throws IllegalArgumentException{
        int count = 0;

        try{
            deleteBandStmt.setInt(1, (int) band.getId().longValue());
            count = deleteBandStmt.executeUpdate();
        }catch(SQLException e){
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }

        if(count == 0)
            throw new IllegalArgumentException("Band does not exist");

        return count;
    }
	
    @Override
    public int updateBand(Band band) throws IllegalArgumentException{
        int count = 0;

        try{
            updateBandStmt.setString(1,band.getBandName());
            updateBandStmt.setInt(2, band.getYoe());
            updateBandStmt.setInt(3, (int) band.getId().longValue());
            count = updateBandStmt.executeUpdate();
        }catch(SQLException e){
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        
        if(count == 0)
            throw new IllegalArgumentException("Band does not exist");

        return count;
    }
    
    @Override
    public Band getBand(long id) throws IllegalArgumentException{

        Band band = null;
        try {
            getBandStmt.setInt(1,(int)id);
            ResultSet rs = getBandStmt.executeQuery();

            while (rs.next()) {
                band = new Band();
                band.setId(rs.getInt("id"));
                band.setBandName(rs.getString("name"));
                band.setYoe(rs.getInt("yoe"));
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        
        if(band == null)
            throw new IllegalArgumentException("Id does not exist");

        return band;
    }
}