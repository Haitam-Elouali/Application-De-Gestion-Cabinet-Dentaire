package ma.TeethCare.entities.baseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class baseEntity {
    private Long idEntite;
    private LocalDate dateCreation;
    private LocalDateTime dateDerniereModification;
    private String modifierPar;
    private String creePar;
}
