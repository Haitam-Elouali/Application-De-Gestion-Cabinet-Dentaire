package ma.TeethCare.service.modules.notification.dto;

import java.time.LocalDateTime;

public class NotificationDTO {

	private Long idUtilisateur;
	private String channel; // EMAIL, SMS
	private String message;
	private LocalDateTime dateEnvoi;
	private String priorite; // LOW, NORMAL, HIGH

	public Long getIdUtilisateur() { return idUtilisateur; }
	public void setIdUtilisateur(Long idUtilisateur) { this.idUtilisateur = idUtilisateur; }
	public String getChannel() { return channel; }
	public void setChannel(String channel) { this.channel = channel; }
	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
	public LocalDateTime getDateEnvoi() { return dateEnvoi; }
	public void setDateEnvoi(LocalDateTime dateEnvoi) { this.dateEnvoi = dateEnvoi; }
	public String getPriorite() { return priorite; }
	public void setPriorite(String priorite) { this.priorite = priorite; }
}


