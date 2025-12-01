package ma.TeethCare.mvc.dto.agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Agenda.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaDTO {
    private Long id;
    private Long medecinId;
    private LocalDate dateAgenda;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String statut;
    private String notes;
    private LocalDateTime dateCreation;
}
