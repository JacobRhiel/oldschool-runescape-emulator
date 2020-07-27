package rs.emulator.entity.details

import rs.emulator.database.annotations.NoArg
import rs.emulator.database.entry.Entry
import javax.persistence.*

/**
 *
 * @author javatar
 */
@NoArg
@Entity(name = "Privileges")
@Table(name = "privileges")
open class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "privilege_id", unique = true, nullable = false)
    open val id: Int,
    @Column(name = "name")
    override val name: String,
    @Column(name = "can_drop")
    override val canDrop: Boolean,
    @Column(name = "can_talk")
    override val canTalk: Boolean,
    @Column(name = "can_teleport")
    override val canTeleport: Boolean,
    @Column(name = "can_trade")
    override val canTrade: Boolean,
    @Column(name = "system_admin")
    override val isSystemAdmin: Boolean
) : IPrivilege, Entry