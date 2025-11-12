package ma.TeethCare.service.modules.auth.dto;

import java.util.List;

public class AuthResultDTO {

	private Long idUtilisateur;
	private String token;
	private List<String> roles;

	public Long getIdUtilisateur() { return idUtilisateur; }
	public void setIdUtilisateur(Long idUtilisateur) { this.idUtilisateur = idUtilisateur; }
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	public List<String> getRoles() { return roles; }
	public void setRoles(List<String> roles) { this.roles = roles; }
}


