package com.example.travelmarket.ui.navigation

object Routes {
    const val WELCOME = "welcome"
    const val AUTH_CONTAINER = "auth_container"
    const val HOME = "home"

    const val DESTINATION_LIST = "destination_list"
    const val PACKAGE_LIST = "package_list"
    const val PACKAGE_DETAIL = "package_detail"

    const val FLIGHT_SEARCH = "flight_search"
    const val COUPON_LIST = "coupon_list"
    const val ACTIVITY_LIST = "activity_list"



    fun packageListWithDestId(destId: String): String {
        return "$PACKAGE_LIST/$destId"
    }

    fun packageDetailWithPkgId(pkgId: String): String {
        return "$PACKAGE_DETAIL/$pkgId"
    }


}