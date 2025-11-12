package ma.TeethCare.service.modules.rdv.dto;

import ma.TeethCare.service.common.dto.DateRange;

public class BookRdvCommand {

	private Long idPatient;
	private Long idMedecin;
	private DateRange slot;
	private String motif;

	public BookRdvCommand() {
	}

	public BookRdvCommand(Long idPatient, Long idMedecin, DateRange slot, String motif) {
		this.idPatient = idPatient;
		this.idMedecin = idMedecin;
		this.slot = slot;
+		this.motif = motif;
	}

	public Long getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(Long idPatient) {
		this.idPatient = idPatient;
	}

	public Long getIdMedecin() {
		return idMedecin;
	}

	public void setIdMedecin(Long idMedecin) {
		this.idMedecin = idMedecin;
	}

	public DateRange getSlot() {
		return slot;
	}

	public void setSlot(DateRange slot) {
		this.slot = slot;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}
}


