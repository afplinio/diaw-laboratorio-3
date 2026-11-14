package br.pucminas.matriculas.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import br.pucminas.matriculas.model.Usuario;

/** Emite o token JWT entregue ao usuário após um login bem-sucedido. */
@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final long expiracaoMinutos;

    public TokenService(JwtEncoder jwtEncoder, @Value("${app.jwt.expiracao-minutos}") long expiracaoMinutos) {
        this.jwtEncoder = jwtEncoder;
        this.expiracaoMinutos = expiracaoMinutos;
    }

    public String gerarToken(Usuario usuario) {
        Instant agora = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("sistema-matriculas")
                .issuedAt(agora)
                .expiresAt(agora.plus(expiracaoMinutos, ChronoUnit.MINUTES))
                .subject(usuario.getId())
                .claim("login", usuario.getLogin())
                .claim("nome", usuario.getNome())
                .claim(SecurityConfig.CLAIM_PERFIL, usuario.getPerfil().name())
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public long getExpiracaoMinutos() {
        return expiracaoMinutos;
    }
}
