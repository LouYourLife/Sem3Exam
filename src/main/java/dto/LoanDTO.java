package dto;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class LoanDTO {
    @Temporal(TemporalType.DATE)
    private Date checkoutDate;
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Temporal(TemporalType.DATE)
    private Date returnedDate;

    public LoanDTO() {
    }
    
    
}
