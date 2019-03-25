package projekt.dao;
import projekt.domain.Band;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


@RunWith(MockitoJUnitRunner.class)
public class BandDaoTest{
    private static final Logger LOGGER = Logger.getLogger(BandDaoTest.class.getCanonicalName());

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";
    static List<Band> expectedDbState;

    abstract class AbstractResultSet implements ResultSet {
        int i; // automatycznie bedzie 0

        @Override
        public int getInt(String s) throws SQLException {
            return expectedDbState.get(i-1).getYoe();
        }
        @Override
        public long getLong(String s) throws SQLException {
            return expectedDbState.get(i-1).getId();
        }
        @Override
        public String getString(String columnLabel) throws SQLException {
            return expectedDbState.get(i-1).getBandName();
        }
        @Override
        public boolean next() throws SQLException {
            i++;
            if (i > expectedDbState.size())
                return false;
            else
                return true;
        }
    }

    @Mock
    Connection connection;
    @Mock
    PreparedStatement selectStatementMock;
    @Mock
    PreparedStatement insertStatementMock;
    @Mock
    PreparedStatement updateStatementMock;
    @Mock
    PreparedStatement deleteStatementMock;

    @Before
    public void setup() throws SQLException {
        Random random = new Random();
        expectedDbState = new LinkedList<>();
        
        for (int i = 0; i < 10;i++) {
            Band band = new Band();
            band.setId(i);
            band.setBandName("Informatycy"+random.nextInt(1000));
            band.setYoe(random.nextInt(50)+1950);
            
            expectedDbState.add(band);
        }

        Mockito.when(connection.prepareStatement("SELECT id, name, yoe FROM Band ORDER BY id")).thenReturn(selectStatementMock);
        Mockito.when(connection.prepareStatement("INSERT INTO Band (name, yoe) VALUES (?, ?)",Statement.RETURN_GENERATED_KEYS)).thenReturn(insertStatementMock);
        Mockito.when(connection.prepareStatement("UPDATE Band SET name=?,yoe=? WHERE id = ?")).thenReturn(updateStatementMock);
        Mockito.when(connection.prepareStatement("DELETE FROM Band where id = ?")).thenReturn(deleteStatementMock);
    }

    @Test
    public void setConnectionCheck() throws SQLException {
        BandDaoJdbcImpl bandDao = new BandDaoJdbcImpl();
        bandDao.setConnection(connection);
        assertNotNull(bandDao.getConnection());
        assertEquals(bandDao.getConnection(), connection);
    }

    @Test
    public void getAllBandsCheck() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getInt("yoe")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        BandDaoJdbcImpl bandDao = new BandDaoJdbcImpl();
        bandDao.setConnection(connection);
        List<Band> retrivedBands = bandDao.getAllBands();
        assertThat(retrivedBands, equalTo(expectedDbState));

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(expectedDbState.size())).getLong("id");
        verify(mockedResultSet, times(expectedDbState.size())).getString("name");
        verify(mockedResultSet, times(expectedDbState.size())).getInt("yoe");
        verify(mockedResultSet, times(expectedDbState.size()+1)).next(); 
    }

    @Test
    public void addBandInOrderCheck() throws SQLException{
        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        BandDaoJdbcImpl bandDao = new BandDaoJdbcImpl();
        bandDao.setConnection(connection);
        Band band = new Band();
        band.setBandName("Test");
        band.setYoe(2012);

        bandDao.addBand(band);

        inorder.verify(insertStatementMock, times(1)).setString(1, "Test");
        inorder.verify(insertStatementMock, times(1)).setInt(2, 2012);
        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void updateBandInOrderCheck() throws SQLException{
        InOrder inorder = inOrder(updateStatementMock);
        when(updateStatementMock.executeUpdate()).thenReturn(1);

        BandDaoJdbcImpl bandDao = new BandDaoJdbcImpl();
        bandDao.setConnection(connection);

        Band band = new Band();
        band.setId(8);
        band.setBandName("Test");
        band.setYoe(1999);

        bandDao.updateBand(band);

        inorder.verify(updateStatementMock, times(1)).setString(1, "Test");
        inorder.verify(updateStatementMock, times(1)).setInt(2, 1999);
        inorder.verify(updateStatementMock, times(1)).setInt(3, 8);
        inorder.verify(updateStatementMock).executeUpdate();
    }

    @Test
    public void deleteBandInOrderCheck() throws SQLException{
        InOrder inorder = inOrder(deleteStatementMock);
        when(deleteStatementMock.executeUpdate()).thenReturn(1);

        BandDaoJdbcImpl bandDao = new BandDaoJdbcImpl();
        bandDao.setConnection(connection);
        Band band = new Band();
        band.setId(9);
        bandDao.deleteBand(band);
       
        inorder.verify(deleteStatementMock, times(1)).setInt(1, 9);
        inorder.verify(deleteStatementMock).executeUpdate();
    }
}