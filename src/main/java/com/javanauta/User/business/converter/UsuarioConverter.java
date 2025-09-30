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
                .enderecos(usuarioDTO.getEndereco() != null ?
                        paraListaEndereco(usuarioDTO.getEndereco()): null)
                .telefones(usuarioDTO.getTelefone() != null ?
                        paralistaTelefone(usuarioDTO.getTelefone()): null)
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
                .endereco(usuario.getEnderecos() != null ?
                        paraListaEnderecoDTO(usuario.getEnderecos()): null)
                .telefone(usuario.getTelefones() != null ?
                        paralistaTelefoneDTO(usuario.getTelefones()): null)
                .build();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
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
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> listaEnderecoDTO) {
        return listaEnderecoDTO.stream().map(this::paraEnderecoDTO).toList();
    }

    public List<TelefoneDTO> paralistaTelefoneDTO(List<Telefone> listaTelefoneDTO) {
        return listaTelefoneDTO.stream().map(this::paraTelefoneDTO).toList();
    }

    /*--------------------------------------------------------------------------------------------------------------------*/

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario usuarioEntity) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuarioEntity.getNome())
                .id(usuarioEntity.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuarioEntity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuarioEntity.getEmail())
                .enderecos(usuarioEntity.getEnderecos())
                .telefones(usuarioEntity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .usuarioId(entity.getUsuarioId())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .usuarioId(entity.getUsuarioId())
                .build();
    }

    /*--------------------------------------------------------------------------------------------------------------------*/

    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .usuarioId(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO dto, Long idUsuario) {
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .usuarioId(idUsuario)
                .build();
    }

}
