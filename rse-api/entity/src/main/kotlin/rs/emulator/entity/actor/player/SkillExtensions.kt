package rs.emulator.entity.actor.player

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.definitions.factories.ItemMetaDefinitionFactory
import rs.emulator.entity.material.items.Wearable
import rs.emulator.entity.skills.SkillManager
import rs.emulator.skills.Skills
import rs.emulator.skills.wieldRequirementMessage

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
fun SkillManager.hasLevelFor(skill: Skills, req : Int) : Boolean {
    return skills[skill.statId].level >= req
}

@ExperimentalCoroutinesApi
fun SkillManager.hasStaticLevel(skill: Skills, req : Int) : Boolean {
    return skills[skill.statId].staticLevel >= req
}

@ExperimentalCoroutinesApi
fun SkillManager.hasRequirementsFor(wearable: Wearable, onFailure : (String) -> Unit = {}) : Boolean {
    val meta = ItemMetaDefinitionFactory.provide(wearable.id)
    val reqs = meta.equipment.requirements
    var canEquip = true
    if(!hasStaticLevel(Skills.ATTACK, reqs.attack)) {
        onFailure(Skills.ATTACK.wieldRequirementMessage(reqs.attack, meta.wiki_name))
        canEquip = false
    }
    if(!hasStaticLevel(Skills.STRENGTH, reqs.strength)) {
        onFailure(Skills.STRENGTH.wieldRequirementMessage(reqs.strength, meta.wiki_name))
        canEquip = false
    }
    if(!hasStaticLevel(Skills.RANGED, reqs.range)) {
        onFailure(Skills.RANGED.wieldRequirementMessage(reqs.range, meta.wiki_name))
        canEquip = false
    }
    if(!hasStaticLevel(Skills.DEFENCE, reqs.defence)) {
        onFailure(Skills.DEFENCE.wieldRequirementMessage(reqs.defence, meta.wiki_name))
        canEquip = false
    }
    if(!hasStaticLevel(Skills.MAGIC, reqs.magic)) {
        onFailure(Skills.MAGIC.wieldRequirementMessage(reqs.magic, meta.wiki_name))
        canEquip = false
    }
    return canEquip
}

@ExperimentalCoroutinesApi
fun SkillManager.hasRequirements(wearable: Wearable, predicate : () -> Boolean, onFailure: (String) -> Unit = {}) : Boolean {
    return predicate() && hasRequirementsFor(wearable, onFailure)
}