package rs.emulator.entity.details

import rs.emulator.database.annotations.NoArg
import rs.emulator.database.entry.Entry
import javax.persistence.*

/**
 *
 * @author javatar
 */
@NoArg
@Entity(name = "Players")
@Table(name = "players")
open class PlayerDetails(
    @Id
    @Column(name = "username", unique = true, nullable = false)
    override val username: String,
    @Column(name = "display_name")
    override var displayName: String,
    @Column(name = "password")
    open val password: String,
    @OneToOne(cascade = [CascadeType.REFRESH])
    @JoinColumn(name = "privilege", referencedColumnName = "privilege_id")
    override val privilege: Privilege
) : IPlayerDetails, Entry