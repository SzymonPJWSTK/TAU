package projekt.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

@Entity(name = "Band")
@Table(name = "band")
@NamedQueries({ 
	@NamedQuery(name = "band.all", query = "Select b from Band b"),
})

public class Band{
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String bandName;
    private String genre;
    private Integer yoe;

    @OneToMany(cascade = CascadeType.PERSIST,
			fetch = FetchType.EAGER,
			orphanRemoval=false,
			mappedBy = "band"
	)
    private List<Member> members = new LinkedList<>();

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

    public List<Member> getMembers(){
        return members;
    }

    public void setMembers(List<Member> members) {
		this.members = members;
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