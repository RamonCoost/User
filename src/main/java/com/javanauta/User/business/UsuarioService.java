package com.javanauta.User.business;

import com.javanauta.User.business.converter.UsuarioConverter;
import com.javanauta.User.business.dto.EnderecoDTO;
import com.javanauta.User.business.dto.TelefoneDTO;
import com.javanauta.User.business.dto.UsuarioDTO;
import com.javanauta.User.infrastructure.entity.Endereco;
import com.javanauta.User.infrastructure.entity.Telefone;
import com.javanauta.User.infrastructure.entity.Usuario;
import com.javanauta.User.infrastructure.exception.ConflictException;
import com.javanauta.User.infrastructure.exception.ResourceNotFoundException;
import com.javanauta.User.infrastructure.repository.EnderecoRepository;
import com.javanauta.User.infrastructure.repository.TelefoneRepository;
import com.javanauta.User.infrastructure.repository.UsuarioRepository;
import com.javanauta.User.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email ja cadastrado");
            }

        }catch (ConflictException e){
            throw new ConflictException("Email já Cadastrado " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscaUsuarioPorEmail(String email){
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                            .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email)
                            )
            );
        }catch (ResourceNotFoundException e ){
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deleteUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        //Aqui buscamos o email do usuario através do token (tirar a obrigatoriedade de passar o email)
       String email = jwtUtil.extrairEmailToken(token.substring(7));

       //criptografia de senha
       dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

       //Busca os dados do usuário no banco de dados
       Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
               ()-> new ResourceNotFoundException("Email não encontrado"));

       //Mesclou os dados que recebemos na requisição DTO com os dados do banco de dados
       Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

       // Salvou os dados do usuário convertido e depois pegou o retorno e converteu pra UsuarioDTO
       return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(
                ()-> new ResourceNotFoundException("Id não encontrado " + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO,entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto,entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("email não localizado " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("email não localizado " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        Telefone telefoneEntity = telefoneRepository.save(telefone);
        return usuarioConverter.paraTelefoneDTO(telefoneEntity);

    }
}
