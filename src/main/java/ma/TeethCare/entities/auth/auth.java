package ma.TeethCare.entities.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class auth extends baseEntity {
    private String email;
    private String motDePasse;
}
