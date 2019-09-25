package net.streets.persistence.entity.complex_type;

import net.streets.persistence.entity.super_class.str_entity;

import javax.persistence.*;
import java.math.BigDecimal;

/***************************************************************************
 *                                                                         *
 * Created:     25 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * System:      IntelliJ 2019 / Windows 10                                 *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Entity
@AttributeOverride(name = "id", column = @Column(name = "user_attributes_id"))
@Cacheable(false)
public class str_user_attributes extends str_entity<str_user_attributes> {
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private str_user user;
	@Column(scale = 4)
	private BigDecimal pocket_balance;
	@Column(scale = 4)
	private BigDecimal account_balance;
	@Basic
	private Integer current_energy;
	@Basic
	private Integer max_energy;

	public str_user_attributes() {}

	public str_user_attributes(str_user user, BigDecimal pocket_balance, BigDecimal account_balance, Integer current_energy, Integer max_energy) {
		this.user = user;
		this.pocket_balance = pocket_balance;
		this.account_balance = account_balance;
		this.current_energy = current_energy;
		this.max_energy = max_energy;
	}

	public str_user getUser() { return user; }

	public void setUser(str_user user) { this.user = user; }

	public BigDecimal getPocket_balance() { return pocket_balance; }

	public void setPocket_balance(BigDecimal pocket_balance) { this.pocket_balance = pocket_balance; }

	public BigDecimal getAccount_balance() { return account_balance; }

	public void setAccount_balance(BigDecimal account_balance) { this.account_balance = account_balance; }

	public Integer getCurrent_energy() { return current_energy; }

	public void setCurrent_energy(Integer current_energy) { this.current_energy = current_energy; }

	public Integer getMax_energy() { return max_energy; }

	public void setMax_energy(Integer max_energy) { this.max_energy = max_energy; }
}
