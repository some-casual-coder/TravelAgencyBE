package com.project.TravelAgency.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "verification_codes")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class VerificationCode {
    private static final int EXPIRATION_MINUTES = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;
    private Date expirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_codes_user"))
    private User user;

    public VerificationCode(String token) {
        super();
        this.token = token;
        this.expirationDate = calculateExpiryDate();
    }

    public VerificationCode(User user, String token) {
        super();
        this.token = token;
        this.user = user;
        this.expirationDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate(){
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, VerificationCode.EXPIRATION_MINUTES);
        return new Date(calendar.getTime().getTime());
    }

    public void updateToken(String verificationCode){
        this.token = verificationCode;
        this.expirationDate = calculateExpiryDate();
    }

}
