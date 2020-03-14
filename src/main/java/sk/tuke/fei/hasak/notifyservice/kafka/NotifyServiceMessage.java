package sk.tuke.fei.hasak.notifyservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotifyServiceMessage {

    private String email;

    private String message;

}
