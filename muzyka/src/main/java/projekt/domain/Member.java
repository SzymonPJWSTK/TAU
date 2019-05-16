package projekt.domain;

import javax.persistence.*;

@Entity(name = "Member")
@Table(name = "member")
@NamedQueries({
        @NamedQuery(name = "member.all", query = "Select m from Member m"),
        @NamedQuery(name = "member.findMembersByName", query = "Select m from Member m where m.name like :memberNameFragment")
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id")
    private Band band;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Member))
            return false;

        return
                id != null &&
                        id.equals(((Member) o).getId());
    }

    @Override
    public int hashCode() {
        return 1337;
    }

    @Override
    public String toString(){
        return "[" + id+ ", "
			 + name + ", " + position + "]";
    }
}