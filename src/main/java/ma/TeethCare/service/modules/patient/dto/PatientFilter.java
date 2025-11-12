package ma.TeethCare.service.modules.patient.dto;

import java.time.LocalDate;

public class PatientFilter {

	private String namePart;
	private LocalDate dateNaissanceFrom;
	private LocalDate dateNaissanceTo;
	private String assurance; // enum name as string to keep DTO simple

	public String getNamePart() { return namePart; }
	public void setNamePart(String namePart) { this.namePart = namePart; }
	public LocalDate getDateNaissanceFrom() { return dateNaissanceFrom; }
	public void setDateNaissanceFrom(LocalDate dateNaissanceFrom) { this.dateNaissanceFrom = dateNaissanceFrom; }
	public LocalDate getDateNaissanceTo() { return dateNaissanceTo; }
	public void setDateNaissanceTo(LocalDate dateNaissanceTo) { this.dateNaissanceTo = dateNaissanceTo; }
	public String getAssurance() { return assurance; }
	public void setAssurance(String assurance) { this.assurance = assurance; }
}


