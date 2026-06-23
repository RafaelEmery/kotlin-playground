package com.mercadolivro.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.util.Date

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
}