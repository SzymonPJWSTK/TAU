package projekt.muzyka;
import projekt.domain.Band;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.Iterator; 
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class FilterBands {

    private String genre;
    private int year;
    private List<Band> bands = new ArrayList<>();
    private List<Band> expectedBands = new ArrayList<>();

    @Given("^User is on serch page$")
    public void userCreatesBand() {
        bands.add(new Band("Metallica", "Disco", 8));
        bands.add(new Band("Metallica2", "Pop", 1));
        bands.add(new Band("Test", "Rock", 4));
        bands.add(new Band("Test2", "Metal", 5));
        bands.add(new Band("Metallica3", "Rock", 6));
        bands.add(new Band("Test3", "Rock", 2));
        bands.add(new Band("Test4", "Country", 7));
    }

    @When("^User sets genre to \"([^\"]*)\"$")
    public void userSetsGenre(String genre) {
        this.genre = genre;
        for(Band b : bands){
            if(b.getGenre().equals(genre))
                expectedBands.add(b);
        }
    }

    @And("^User sets year to hihger than (\\d+)$")
    public void userSetsBandYear(int year) {
        this.year = year;
        Iterator itr = expectedBands.iterator(); 
        while (itr.hasNext()) 
        { 
            Band x = (Band)itr.next(); 
            if (x.getYoe() <= year) 
                itr.remove(); 
        } 
    }

    @Then("User should see bands that match his criteria$")
    public void bandCreated() {
        for(Band b : expectedBands){
            assertEquals(genre, b.getGenre());
            assertTrue(b.getYoe() > year);
        }
    }
}