package sk.tuke.fei.hasak.notifyservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "notification_tb")
public class Notification extends RepresentationModel<Notification> {

    @Id
    @Column(name = "contact_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contactId;

    @Column(name = "message_id", nullable = false)
    private long messageId;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    public Notification(long messageId, String email) {
        this.messageId = messageId;
        this.email = email;
    }
}
