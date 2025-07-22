package com.salah.falcon.data.mapper

import com.salah.falcon.LaunchDetailsQuery
import com.salah.falcon.domain.model.LaunchDetails
import com.salah.falcon.domain.model.Mission
import com.salah.falcon.domain.model.Rocket

interface IRemoteLaunchToLaunchDetailsMapper : Mapper<LaunchDetailsQuery.Launch, LaunchDetails>
class LaunchToLaunchDetailsMapper :
    IRemoteLaunchToLaunchDetailsMapper {

    override fun map(from: LaunchDetailsQuery.Launch) = LaunchDetails(
        id = from.id,
        mission = mapMission(from.mission),
        rocket = mapRocket(from.rocket),
        site = from.site ?: "",
    )

    private fun mapMission(from: LaunchDetailsQuery.Mission?) = Mission(
        name = from?.name ?: "",
        missionPatchImageURL = from?.missionPatch ?: ""
    )

    private fun mapRocket(from: LaunchDetailsQuery.Rocket?) = Rocket(
        id = from?.id ?: "",
        name = from?.name ?: "",
        type = from?.type ?: ""
    )
}
