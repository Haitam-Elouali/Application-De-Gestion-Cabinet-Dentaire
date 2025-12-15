package ma.TeethCare.service.common.dto;

import java.time.LocalDateTime;

/**
 * Closed-open interval [start, end) representing a time slot.
 */
public class DateRange {

	private LocalDateTime start;
	private LocalDateTime end;

	public DateRange() {
	}

	public DateRange(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public boolean isValid() {
		return start != null && end != null && start.isBefore(end);
	}
}


