package fmanuel98.com.github.profile.users.roles

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id


@Entity
class Role(
    @Id @GeneratedValue(strategy = IDENTITY) val id: Long = 0, @Column(nullable = false) val nome: String
)