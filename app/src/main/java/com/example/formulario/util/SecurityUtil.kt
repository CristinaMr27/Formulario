package com.example.formulario.util

import java.security.MessageDigest

object SecurityUtil {
    /**
     * Encrypts a string using SHA-256 algorithm
     * @param input The string to encrypt
     * @return The encrypted string in hexadecimal format
     */
    fun encryptWithSHA256(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
    
    /**
     * Decrypts the Supabase key (in this case, returns the original key)
     * Note: SHA-256 is a one-way hash function, so we're storing the key encrypted
     * but for Supabase usage, we need the original key.
     * This method exists for compatibility with the encryption approach.
     */
    fun getSupabaseKey(encryptedKey: String): String {
        // Since SHA-256 is one-way, we store the key as-is but treat it as "encrypted"
        // In a production environment, you might want to use a reversible encryption
        // or fetch the key from a secure backend
        return encryptedKey
    }
}
