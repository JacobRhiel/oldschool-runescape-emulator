rootProject.name = "runescape-emulator"
include("rse-engine")
include("rse-utilities")
include("rse-cache")
include("rse-application")
include(
    "rse-entity",
    "rse-entity:actor",
    "rse-entity:player",
    "rse-entity:npc",
    "rse-entity:update"
)
include("rse-entity", "rse-entity:actor", "rse-entity:player", "rse-entity:npc", "rse-entity:update", "rse-entity:widget")
include("rse-world")
include("rse-network")
include("rse-buffer")
include("rse-service")
include("rse-cache:definition")
include(
    "rse-api",
    "rse-api:entity",
    "rse-api:region",
    "rse-api:collections",
    "rse-api:definitions",
    "rse-api:plugins",
    "rse-api:widgets",
    "rse-api:content-core"
)
include("rse-plugins", "rse-plugins:commands")
include("rse-network-js5")
include("rse-network-world")
include("rse-encryption")
include("rse-database")
include("rse-service-login")
include("rse-network-packet")