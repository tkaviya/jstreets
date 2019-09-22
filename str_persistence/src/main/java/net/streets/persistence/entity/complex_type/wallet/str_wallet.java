package net.streets.persistence.entity.complex_type.wallet;

import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.super_class.str_entity;

import javax.persistence.*;
import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Entity
@Table(name = "str_wallet")
@AttributeOverride(name = "id", column = @Column(name = "wallet_id"))
@Cacheable(false)
public class str_wallet extends str_entity<str_wallet> {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_admin_user_id")
    private str_user wallet_admin_user;
    @Column(scale = 4)
    private BigDecimal current_balance;

    public str_wallet() {
    }

    public str_wallet(BigDecimal current_balance, str_user wallet_admin_user) {
        this.current_balance = current_balance;
        this.wallet_admin_user = wallet_admin_user;
    }

    public BigDecimal getCurrent_balance() {
        return current_balance;
    }

    public str_wallet setCurrent_balance(BigDecimal current_balance) {
        this.current_balance = current_balance;
        return this;
    }

    public str_user getWallet_admin_user() {
        return wallet_admin_user;
    }

    public str_wallet setWallet_admin_user(str_user wallet_admin_user) {
        this.wallet_admin_user = wallet_admin_user;
        return this;
    }
}
