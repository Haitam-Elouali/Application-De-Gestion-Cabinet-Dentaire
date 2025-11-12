package ma.TeethCare.service.modules.dashboard.dto;

public class DashboardOverviewDTO {

	private long nbPatients;
	private long nbRdvToday;
	private long nbConsultationsToday;
	private double chiffreAffairesJour;

	public long getNbPatients() { return nbPatients; }
	public void setNbPatients(long nbPatients) { this.nbPatients = nbPatients; }
	public long getNbRdvToday() { return nbRdvToday; }
	public void setNbRdvToday(long nbRdvToday) { this.nbRdvToday = nbRdvToday; }
	public long getNbConsultationsToday() { return nbConsultationsToday; }
	public void setNbConsultationsToday(long nbConsultationsToday) { this.nbConsultationsToday = nbConsultationsToday; }
	public double getChiffreAffairesJour() { return chiffreAffairesJour; }
	public void setChiffreAffairesJour(double chiffreAffairesJour) { this.chiffreAffairesJour = chiffreAffairesJour; }
}


