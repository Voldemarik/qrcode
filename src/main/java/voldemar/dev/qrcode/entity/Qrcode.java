package voldemar.dev.qrcode.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "qrcodes")
public class Qrcode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    @Column(name = "participant_id")
    private Long participantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qrcode qrcode = (Qrcode) o;
        return Objects.equals(id, qrcode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
