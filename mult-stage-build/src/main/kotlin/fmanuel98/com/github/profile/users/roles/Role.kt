package fmanuel98.com.github.profile.users.roles

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY


@Entity
class Role(
    @Id @GeneratedValue(strategy = IDENTITY) val id: Long = 0, @Column(nullable = false) val nome: String
)