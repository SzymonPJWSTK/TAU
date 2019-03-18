
package projekt.dao;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import projekt.domain.Band;
import java.sql.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@RunWith(JUnit4.class)
public class BandDaoTest{
    private static final Logger LOGGER = Logger.getLogger(BandDaoTest.class.getCanonicalName());

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";
    BandDao bandManager;
    List<Band> expectedDbState;

    @Before
    public void setup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.createStatement()
                    .executeUpdate("CREATE TABLE " +
                            "Band(id bigint GENERATED BY DEFAULT AS IDENTITY, "
                            + "name varchar(20) NOT NULL, " + "yoe integer)");

        } catch (SQLException e) {}

        Random rand = new Random();
        PreparedStatement addBandStmt = connection.prepareStatement(
                "INSERT INTO Band (name, yoe) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        expectedDbState = new LinkedList<Band>();
        for (int i = 0; i < 10; i++) {
            Band band = new Band("Informatycy" + rand.nextInt(1000), 1000 + rand.nextInt(1000));
            try {
                addBandStmt.setString(1, band.getBandName());
                addBandStmt.setInt(2, band.getYoe());
                addBandStmt.executeUpdate();

                ResultSet generatedKeys = addBandStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    band.setId(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
            }

            expectedDbState.add(band);
        }

        bandManager = new BandDaoJdbcImpl(connection);
    }

    @After
    public void cleanup() throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.prepareStatement("DELETE FROM Band").executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.FINEST,"Probably the database was not yet initialized");
        }
    }

    @Test
    public void addbandTest() throws Exception{
        Band band = new Band();
        band.setBandName("Informatica");
        band.setYoe(2012);

        assertEquals(1, bandManager.addBand(band));

        expectedDbState.add(band);
        assertThat(bandManager.getAllBands(), equalTo(expectedDbState));
    }

    @Test
    public void deleteBandTest() throws Exception{
        addbandTest();
        List<Band> bands = bandManager.getAllBands();
        Band bandToDelete = bands.get(bands.size()-1);
        
        assertEquals(1,bandManager.deleteBand(bandToDelete));
        
        expectedDbState.remove(bandToDelete);
        
        assertThat(bandManager.getAllBands(), equalTo(expectedDbState));
    }

    @Test
    public void updateBandTest() throws Exception{
        addbandTest();

        List<Band> bands = bandManager.getAllBands();
        Band bandToUpdate = bands.get(bands.size()-1);
        bandToUpdate.setBandName("Nowi informatycy");
        assertEquals(1,bandManager.updateBand(bandToUpdate));

        expectedDbState.set(expectedDbState.size()-1,bandToUpdate);
        assertThat(bandManager.getAllBands(), equalTo(expectedDbState));
    }

    @Test
    public void getBandTest() throws Exception{
        addbandTest();
        List<Band> bands = bandManager.getAllBands();
        assertThat(bandManager.getBand(bands.get(bands.size()-1).getId().longValue()), 
                    equalTo(expectedDbState.get(expectedDbState.size()-1)));
    }
}