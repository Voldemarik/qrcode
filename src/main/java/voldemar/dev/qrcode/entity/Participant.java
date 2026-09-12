package voldemar.dev.qrcode.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String patronymic;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private List<Qrcode> qrcodeList;
}
