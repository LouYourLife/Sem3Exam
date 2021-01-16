package dto;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class LoanDTO {
    @Temporal(TemporalType.DATE)
    private Date checkoutDate;
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    private String username;
    private String password;
    private int isbn;

    public LoanDTO() {
    }

    public LoanDTO(String username, String password, int isbn) {
        this.checkoutDate = new Date();
        this.dueDate = addMonth(this.checkoutDate, 1);
        this.username = username;
        this.password = password;
        this.isbn = isbn;
    }
    
    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
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

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.checkoutDate);
        hash = 37 * hash + Objects.hashCode(this.dueDate);
        hash = 37 * hash + Objects.hashCode(this.username);
        hash = 37 * hash + Objects.hashCode(this.password);
        hash = 37 * hash + this.isbn;
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
        final LoanDTO other = (LoanDTO) obj;
        if (this.isbn != other.isbn) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.checkoutDate, other.checkoutDate)) {
            return false;
        }
        if (!Objects.equals(this.dueDate, other.dueDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LoanDTO{" + "checkoutDate=" + checkoutDate + ", dueDate=" + dueDate + ", username=" + username + ", password=" + password + ", isbn=" + isbn + '}';
    }
    
    
}
