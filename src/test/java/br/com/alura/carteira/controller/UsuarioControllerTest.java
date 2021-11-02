package br.com.alura.carteira.controller;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.carteira.dto.UsuarioDto;
import br.com.alura.carteira.infra.security.TokenService;
import br.com.alura.carteira.mocks.UsuarioFactory;
import br.com.alura.carteira.modelo.Perfil;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.PerfilRepository;
import br.com.alura.carteira.repository.UsuarioRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UsuarioControllerTest {
	
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String token;
    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario(null, "Henrique Lustosa", "henriqlustosa", "123123");;
        Perfil admin = perfilRepository.getById(1L);
        usuario.adicionarPerfil(admin);
        usuarioRepository.save(usuario);
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, usuario.getLogin());

        token = "Bearer " + tokenService.gerarToken(authentication);
    }
    @Test
    void naoDeveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
        String json = "{}";

        mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(json).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaCadastrarUmUsuarioComDadosCompletos() throws Exception {
        String json = objectMapper.writeValueAsString(UsuarioFactory.criarUsuarioFormDto());
        UsuarioDto usuarioResponseDto = UsuarioFactory.criarUsuarioResponseDto();

        mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(json).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isCreated()).andExpect(header().exists("Location")).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value(usuarioResponseDto.getNome()));
    }

}
