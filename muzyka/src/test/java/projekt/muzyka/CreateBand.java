package projekt.muzyka;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class CreateBand {

    private String bandName;
    private String answer;

    @Given("^user creates band Metallica$")
    public void userCreatesBand() {
        bandName = "Metallica";
    }

    @When("^user clicks next$")
    public void next() {
    }

    @And("^such band does exist \"([^\"]*)\"$")
    public void bandDoesNotExist(String doesBandExist) {
        if(doesBandExist.equals("true")){
            answer = "Failure";
        }
        else{
            answer = "Success";
        }
    }

    @Then("User should be told \"([^\"]*)\"$")
    public void bandCreated(String expectedAnswer) {
        assertEquals(expectedAnswer,answer);
    }
}