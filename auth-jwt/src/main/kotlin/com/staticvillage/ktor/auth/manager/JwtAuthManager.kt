package com.staticvillage.ktor.auth.manager

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.staticvillage.ktor.auth.model.Token
import io.ktor.auth.jwt.JWTAuthenticationProvider
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.getDigestFunction
import java.util.*

class JwtAuthManager(
    private val audience: String,
    private val realm: String,
    private val secret: String,
    private val issuer: String,
    private val salt: String,
    private val validityInMs: Long = 36_000_00L * 24 * 365
) : AuthManager {
    companion object {
        const val DIGEST_ALGORITHM = "SHA-256"
        const val TOKEN_SUBJECT = "Authentication"
    }

    @KtorExperimentalAPI
    private val digester = getDigestFunction(DIGEST_ALGORITHM, salt)
    private val algorithm: Algorithm = Algorithm.HMAC512(secret)

    override fun configure(provider: JWTAuthenticationProvider) {
        val verifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()

        provider.verifier(verifier)
        provider.realm = realm
        provider.validate { credential ->
            if (credential.payload.audience.contains(audience)) {
                JWTPrincipal(credential.payload)
            } else {
                null
            }
        }
    }

    @KtorExperimentalAPI
    override fun passwordHash(password: String): String {
        val hash = digester(password)
        return Base64.getEncoder().encodeToString(hash)
    }

    override fun makeToken(id: UUID): Token {
        val jwtToken = JWT.create()
            .withSubject(TOKEN_SUBJECT)
            .withIssuer(issuer)
            .withAudience(audience)
            .withJWTId(id.toString())
            .withExpiresAt(getExpiration())
            .sign(algorithm)
        return Token(jwtToken)
    }

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}