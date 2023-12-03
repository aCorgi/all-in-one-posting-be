package com.corgi.allinonepostingbe.dto

data class FacebookPageAccountsResponse(
    val data: List<FacebookPageAccountsData>,
    val paging: FacebookPageAccountsPaging,
    val error: FacebookPageAccountsError?
)

data class FacebookPageAccountsData(
    val id: String,
    val name: String,
    val accessToken: String,
    val category: String,
    val categoryList: List<FacebookPageAccountsDataCategoryList>,
    val tasks: List<String>
)

data class FacebookPageAccountsDataCategoryList(
    val id: String,
    val name: String
)

data class FacebookPageAccountsPaging(
    val cursors: FacebookPageAccountsPagingCursors
)

data class FacebookPageAccountsPagingCursors(
    val before: String,
    val after: String
)

data class FacebookPageAccountsError(
    val message: String,
    val type: String,
    val code: Int,
    val errorSubcode: String,
    val fbtraceId: String
)
