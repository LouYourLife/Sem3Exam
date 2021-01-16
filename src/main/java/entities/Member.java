package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String username;
    private String password;
    
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Loan> memLoans;

    public Member() {
    }

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
        this.memLoans = new ArrayList<>();
    }

    
    
    public List<Loan> getMemLoans() {
        return memLoans;
    }
    
    public void AddMemLoans(Loan loan) {
        this.memLoans.add(loan);
        if(loan != null) {
            loan.setMember(this);
        }
    }
    
    public void removeMemLoans(Loan loan) {
        if(loan != null) {
            memLoans.remove(loan);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.username);
        hash = 53 * hash + Objects.hashCode(this.password);
        hash = 53 * hash + Objects.hashCode(this.memLoans);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Member other = (Member) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.memLoans, other.memLoans)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Member{" + "username=" + username + ", password=" + password + ", memLoans=" + memLoans + '}';
    }

    
    
}
