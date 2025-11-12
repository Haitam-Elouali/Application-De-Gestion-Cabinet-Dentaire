package ma.TeethCare.service.modules.statistiques.dto;

public class StatsView {

	private long nbConsultations;
	private long nbRdv;
	private double chiffreAffaires;
	private double charges;

	public long getNbConsultations() { return nbConsultations; }
	public void setNbConsultations(long nbConsultations) { this.nbConsultations = nbConsultations; }
	public long getNbRdv() { return nbRdv; }
	public void setNbRdv(long nbRdv) { this.nbRdv = nbRdv; }
	public double getChiffreAffaires() { return chiffreAffaires; }
	public void setChiffreAffaires(double chiffreAffaires) { this.chiffreAffaires = chiffreAffaires; }
	public double getCharges() { return charges; }
	public void setCharges(double charges) { this.charges = charges; }
}


