/*
 *
 *  ******************************************************************
 *  *  * Copyright (C) 2022
 *  *  * RpcImage.kt is part of Kizzy
 *  *  *  and can not be copied and/or distributed without the express
 *  *  * permission of yzziK(Vaibhav)
 *  *  *****************************************************************
 *
 *
 */

package com.auramusic.kizzy.rpc

import com.auramusic.kizzy.repository.KizzyRepository

/**
 * Modified by Zion Huang
 */
sealed class RpcImage {

    class DiscordImage(val image: String) : RpcImage() {
    }

    class ExternalImage(val image: String) : RpcImage() {
    }
}
