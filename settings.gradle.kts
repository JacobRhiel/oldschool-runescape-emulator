rootProject.name = "runescape-emulator"
include("rse-engine")
include("rse-utilities")
include("rse-cache")
include("rse-application")
include("rse-entity", "rse-entity:player")
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
    "rse-api:plugins"
)
include("rse-plugins")
include("rse-network-js5")
include("rse-network-world")
include("rse-encryption")
include("rse-database")
include("rse-service-login")
include("rse-network-packet")
