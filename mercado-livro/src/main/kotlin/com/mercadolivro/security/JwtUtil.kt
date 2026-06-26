package com.mercadolivro.security

import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {
    @Value($$"${jwt.secret}")
    private val secret: String? = null
    @Value($$"${jwt.expiration}")
    private val expiration: Long? = null

    fun generateToken(userId: Int): String {
        return Jwts.builder()
            .setSubject(userId.toString())
            .setExpiration(Date(System.currentTimeMillis() + expiration!!)) // 10 hours expiration
            .signWith(Keys.hmacShaKeyFor(secret?.toByteArray()))
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)
        if (claims.subject == null || Date().after(claims.expiration)) {
            return false
        }
        return true
    }

    private fun getClaims(token: String): Claims {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret?.toByteArray()))
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            throw AuthenticationException(
                Errors.TOKEN_ERROR.message,
                Errors.TOKEN_ERROR.code
            )
        }
    }

    fun getSubject(token: String): String {
        return getClaims(token).subject
    }
}