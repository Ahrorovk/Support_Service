package com.example.supportservice.main.presentation.mainScreen

data class MainState(
    val searchState: String = "",
//    val vacanciesRespState: GetVacancyRespState = GetVacancyRespState(),
//    val companiesRespState: GetCompaniesRespState = GetCompaniesRespState(),
//    val companies: List<GetCompaniesResp> = emptyList(),
    val refreshToken: String ="",
    val accessToken: String =""
)
