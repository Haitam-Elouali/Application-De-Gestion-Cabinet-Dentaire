package ma.TeethCare.service.common.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Minimal money value object for service layer contracts.
 */
public class Money {

	private BigDecimal amount;
	private String currency; // e.g. "MAD"

	public Money() {
	}

	public Money(BigDecimal amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Money)) return false;
		Money money = (Money) o;
		return Objects.equals(amount, money.amount) && Objects.equals(currency, money.currency);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, currency);
	}
}


