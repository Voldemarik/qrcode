package voldemar.dev.qrcode.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "qrcodes")
public class Qrcode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    @Column(name = "participant_id")
    private Long participantId;
}
