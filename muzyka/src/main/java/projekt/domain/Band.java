package projekt.domain;

public class Band{
    
    private Long id;
    private String bandName;
    private Integer yoe;

    public Band(){

    }

    public Band(String bandName, Integer yoe){
        this.id = null;
        this.bandName = bandName;
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
				((other.getId() == this.getId()) || (other.getId().longValue() == this.getId().longValue())) &&
				((other.getYoe() == this.getYoe()) || (other.getYoe().intValue() == this.getYoe().intValue()));
		return ret;
}

    @Override
	public String toString() {
		return "[" + id+ ", "
			 + bandName + ", " + yoe + "]";
}
}