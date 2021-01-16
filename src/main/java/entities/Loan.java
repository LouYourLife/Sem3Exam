package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Temporal(TemporalType.DATE)
    private Date checkoutDate;
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Temporal(TemporalType.DATE)
    private Date returnedDate;
    
    @ManyToOne
    private Book book;
    
    @ManyToOne
    private Member member;

    public Loan() {
    }

    public Loan(Date checkoutDate) {
        this.checkoutDate = new Date();
        this.dueDate = addMonth(this.checkoutDate, 1);
        this.returnedDate = this.dueDate;
    }
    
    
//    public static void main(String[] args) {
//        Date x = new Date();
//        System.out.println(x);
//        Date y = addMonth(x, 1);
//        System.out.println(y);
//        
//    }
    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.checkoutDate);
        hash = 41 * hash + Objects.hashCode(this.dueDate);
        hash = 41 * hash + Objects.hashCode(this.returnedDate);
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
        final Loan other = (Loan) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.checkoutDate, other.checkoutDate)) {
            return false;
        }
        if (!Objects.equals(this.dueDate, other.dueDate)) {
            return false;
        }
        if (!Objects.equals(this.returnedDate, other.returnedDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Loan{" + "id=" + id + ", checkoutDate=" + checkoutDate + ", dueDate=" + dueDate + ", returnedDate=" + returnedDate + '}';
    }
    
}
