package rs.emulator.entity.details

import org.hibernate.annotations.Type
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
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "privilege", referencedColumnName = "privilege_id")
    override val privilege: Privilege,
    @Type(type = "text")
    @Column(name = "inventory", columnDefinition = "text")
    override var inventory: String,
    @Type(type = "text")
    @Column(name = "bank", columnDefinition = "text")
    override var bank: String,
    @Type(type = "text")
    @Column(name = "equipment", columnDefinition = "text")
    override var equipment: String,
    @Type(type = "text")
    @Column(name = "varbits", columnDefinition = "text")
    override var varbits: String,
    @Column(name = "banned")
    override var banned: Boolean,
    @Column(name = "muted")
    override var muted: Boolean,
    @Column(name = "coordinates")
    override var coordinate: Int
) : IPlayerDetails, Entry