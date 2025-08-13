package com.javanauta.User.business.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {

    private String nome;
    private String email;
    private String senha;
    private List<TelefoneDTO> telefones;
    private List<EnderecoDTO> enderecos;
}
