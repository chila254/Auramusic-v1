/*
 *
 *  ******************************************************************
 *  *  * Copyright (C) 2022
 *  *  * KizzyRepositoryImpl.kt is part of Kizzy
 *  *  *  and can not be copied and/or distributed without the express
 *  *  * permission of yzziK(Vaibhav)
 *  *  *****************************************************************
 *
 *
 */

package com.auramusic.kizzy.repository

import com.auramusic.kizzy.remote.ApiService
import com.auramusic.kizzy.remote.ImageProxyResponse
import io.ktor.client.call.body

/**
 * Modified by Zion Huang
 */
class KizzyRepository(
    userAgent: String,
    superProperties: String?
) {
    private val api = ApiService(userAgent, superProperties)

    suspend fun getImages(urls: List<String>): ImageProxyResponse? {
        return api.getImage(urls).getOrNull()?.body()
    }
}
