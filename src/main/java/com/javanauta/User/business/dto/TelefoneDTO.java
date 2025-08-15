package com.javanauta.User.business.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TelefoneDTO {

    private long id;
    private String numero;
    private String ddd;
}
