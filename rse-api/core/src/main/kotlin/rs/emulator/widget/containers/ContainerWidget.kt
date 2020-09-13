package rs.emulator.widget.containers

/**
 *
 * @author javatar
 */

enum class ContainerWidget(val id: Int) {

    INVENTORY(149),
    EQUIPMENT(387);

    companion object {

        val containerMetaData = mutableMapOf(
            93 to INVENTORY,
            94 to EQUIPMENT
        )

    }

}