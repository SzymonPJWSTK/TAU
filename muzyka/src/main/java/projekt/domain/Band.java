package projekt.domain;
import javax.persistence.*;

@Entity(name = "Band")
@Table(name = "band")
@NamedQueries({ 
	@NamedQuery(name = "band.all", query = "Select b from Band b"),
	@NamedQuery(name = "band.findBands", query = "Select b from Band b WHERE b.bandName like :bandNameFragment")
})

public class Band{
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String bandName;
    private String genre;
    private Integer yoe;

    public Band(){

    }

    public Band(String bandName, String genre, Integer yoe){
        this.id = null;
        this.bandName = bandName;
        this.genre = genre;
        this.yoe = yoe;
    }

    public void setId(long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setBandName(String name){
        bandName = name;
    }

    public String getBandName(){
        return bandName;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public String getGenre(){
        return genre;
    }

    public void setYoe(Integer yoe){
        this.yoe = yoe;
    }

    public Integer getYoe(){
        return yoe;
    }

    @Override
	public boolean equals(Object o) {
		Band other = (Band) o;
        boolean ret = other.getBandName().equals(this.getBandName()) &&
                other.getGenre().equals(this.getGenre()) &&
				((other.getId() == this.getId()) || (other.getId().longValue() == this.getId().longValue())) &&
				((other.getYoe() == this.getYoe()) || (other.getYoe().intValue() == this.getYoe().intValue()));
		return ret;
}

    @Override
	public String toString() {
		return "[" + id+ ", "
			 + bandName + ", " + genre + ", " + yoe + "]";
}
}