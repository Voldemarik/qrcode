package voldemar.dev.qrcode.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "qrcodes")
public class Qrcode {
    @Id
    private UUID id;

    private Boolean status;

    @Column(name = "participant_id")
    private Long participantId;
}
