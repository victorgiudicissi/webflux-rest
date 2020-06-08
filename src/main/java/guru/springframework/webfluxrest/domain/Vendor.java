package guru.springframework.webfluxrest.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    private String id;
    private String firstName;
    private String lastName;
}
