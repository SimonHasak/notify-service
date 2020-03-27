package sk.tuke.fei.hasak.notifyservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private long id;
    private String text;

}
