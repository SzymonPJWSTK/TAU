package projekt.domain;

public class Band{
    
    private long id;
    private String bandName;

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void setBandName(String name){
        bandName = name;
    }

    public String getBandName(){
        return bandName;
    }
}