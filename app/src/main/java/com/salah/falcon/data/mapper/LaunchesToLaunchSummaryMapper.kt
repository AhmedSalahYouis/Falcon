package com.salah.falcon.data.mapper

import com.salah.falcon.LaunchesQuery
import com.salah.falcon.domain.model.LaunchSummary
import com.salah.falcon.domain.model.Mission

interface IRemoteLaunchToLaunchSummaryMapper : Mapper<LaunchesQuery.Launch, LaunchSummary>
class LaunchesToLaunchSummaryMapper : IRemoteLaunchToLaunchSummaryMapper {

    override fun map(from: LaunchesQuery.Launch) = LaunchSummary(
        id = from.id,
        rocketName = from.rocket?.name ?: "",
        mission = mapMission(from.mission)
    )

    private fun mapMission(from: LaunchesQuery.Mission?) = Mission(
        name = from?.name ?: "",
        missionPatchImageURL = from?.missionPatch ?: ""
    )
}
