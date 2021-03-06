/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.uaep.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Room(
    @SerializedName("uuid")
    val id: String,
    val date: Date,
    @SerializedName("place")
    val title: String,
    @SerializedName("number_of_users")
    val number: String,
    val gender: String,
    val host: String,
    val status : String? = null,
    val teamA_status : String? = null,
    val teamB_status : String? = null,
    val apply_flag : Boolean? =null,
    val level_limit: String? = null,
    val province: String? = null,
    val town: String? = null
)


enum class Rank(val korean: String) {
    TESTER("테스터"),
    BIGINNER("비기너"),
    AMATEUR("아마추어"),
    SEMI_PRO("세미프로"),
    PRO("프로")
}