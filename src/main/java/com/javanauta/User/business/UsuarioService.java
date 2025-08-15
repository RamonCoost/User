package com.javanauta.User.business;

import com.javanauta.User.business.converter.UsuarioConverter;
import com.javanauta.User.business.dto.UsuarioDTO;
import com.javanauta.User.infrastructure.entity.Usuario;
import com.javanauta.User.infrastructure.exception.ConflictException;
import com.javanauta.User.infrastructure.exception.ResourceNotFoundException;
import com.javanauta.User.infrastructure.repository.UsuarioRepository;
import com.javanauta.User.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
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

    public Usuario buscaUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email)
        );
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
       Usuario usuario = usuarioConverter.upadateUsuario(dto, usuarioEntity);

       // Salvou os dados do usuário convertido e depois pegou o retorno e converteu pra UsuarioDTO
       return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
