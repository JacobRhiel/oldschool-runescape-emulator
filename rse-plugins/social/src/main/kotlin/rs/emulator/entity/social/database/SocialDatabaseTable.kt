package rs.emulator.entity.social.database

import rs.emulator.database.IDatabaseTable
import rs.emulator.utilities.database.annotations.NoArg
import javax.persistence.*

/**
 *
 * @author Chk
 */
@NoArg
@Entity
@Table(name = "social")
open class SocialDatabaseTable(
    @Id @Column(name = "uuid", unique = true, nullable = false) var uuid: Int
) : IDatabaseTable