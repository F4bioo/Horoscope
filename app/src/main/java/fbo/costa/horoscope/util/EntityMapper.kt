package fbo.costa.horoscope.util

interface EntityMapper<EntityModel, DomainModel> {

    fun mapFromEntityModel(entityModel: EntityModel): DomainModel

}
