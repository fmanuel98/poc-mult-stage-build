package fmanuel98.com.github.profile.users

import fmanuel98.com.github.profile.users.roles.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = IDENTITY) var id: Long = 0,
    val name: String,
    @Column(nullable = false, unique = true)
    val email: String,
    var password: String="",
    @ManyToMany @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    ) val roles: MutableSet<Role>? = null
) {
    fun removeRole(role: Role) = roles?.remove(role)
    fun addRole(role: Role) = roles?.add(role)
    val isNew get() = id == 0L
}

interface Users : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
}
