package com.project.TravelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "password_reset_tokens")
public class PasswordReset {
    private static final int EXPIRATION = 60*24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_pwdReset_user"))
    private User user;

    private Date expirationDate;

    public PasswordReset() {
       super();
    }

    public PasswordReset(final String token) {
        super();
        this.token = token;
        this.expirationDate = calculateExpiryDate();
    }

    public PasswordReset(final String token,final User user) {
        this.token = token;
        this.user = user;
        this.expirationDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate(){
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, PasswordReset.EXPIRATION);
        return new Date(calendar.getTime().getTime());
    }

    public void updateToken(final String token){
        this.token = token;
        this.expirationDate = calculateExpiryDate();
    }
}
