package com.javanauta.User.business.converter;

import com.javanauta.User.business.dto.EnderecoDTO;
import com.javanauta.User.business.dto.TelefoneDTO;
import com.javanauta.User.business.dto.UsuarioDTO;
import com.javanauta.User.infrastructure.entity.Endereco;
import com.javanauta.User.infrastructure.entity.Telefone;
import com.javanauta.User.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

/*------------------------------------------------------DTO PARA ENTITY----------------------------------------------------------------------*/
@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paralistaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> listaEnderecoDTO) {
        return listaEnderecoDTO.stream().map(this::paraEndereco).toList();
    }

    public List<Telefone> paralistaTelefone(List<TelefoneDTO> listaTelefoneDTO) {
        return listaTelefoneDTO.stream().map(this::paraTelefone).toList();
    }

    /*------------------------------------------------------ENTITY PARA DTO----------------------------------------------------------------------*/

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paralistaTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> listaEndereco) {
        return listaEndereco.stream().map(this::paraEnderecoDTO).toList();
    }

    public List<TelefoneDTO> paralistaTelefoneDTO(List<Telefone> listaTelefoneDTO) {
        return listaTelefoneDTO.stream().map(this::paraTelefoneDTO).toList();
    }

}
