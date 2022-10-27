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

    private String verificationCode;
    private Date expirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_codes_user"))
    private User user;

    public VerificationCode(String verificationCode) {
        super();
        this.verificationCode = verificationCode;
        this.expirationDate = calculateExpiryDate(EXPIRATION_MINUTES);
    }

    public VerificationCode(User user, String verificationCode) {
        super();
        this.verificationCode = verificationCode;
        this.user = user;
        this.expirationDate = calculateExpiryDate(EXPIRATION_MINUTES);
    }

    private Date calculateExpiryDate(int expiryInMinutes){
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expiryInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public void updateToken(String verificationCode){
        this.verificationCode = verificationCode;
        this.expirationDate = calculateExpiryDate(EXPIRATION_MINUTES);
    }

}
