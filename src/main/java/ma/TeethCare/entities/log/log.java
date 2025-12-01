package ma.TeethCare.entities.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class log extends baseEntity {
    private Long idLog;
    private String action;
    private String utilisateur;
    private LocalDateTime dateAction;
    private String description;
    private String adresseIP;
}
