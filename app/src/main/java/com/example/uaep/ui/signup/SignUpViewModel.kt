package com.example.uaep.ui.signup

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.uaep.enums.Position
import com.example.uaep.enums.Province
import com.example.uaep.enums.TownBusan
import com.example.uaep.enums.TownChungBuk
import com.example.uaep.enums.TownChungNam
import com.example.uaep.enums.TownDaegu
import com.example.uaep.enums.TownDaejeon
import com.example.uaep.enums.TownGangwon
import com.example.uaep.enums.TownGwangju
import com.example.uaep.enums.TownGyeonggi
import com.example.uaep.enums.TownGyungBuk
import com.example.uaep.enums.TownGyungNam
import com.example.uaep.enums.TownIncheon
import com.example.uaep.enums.TownJeju
import com.example.uaep.enums.TownJeonBuk
import com.example.uaep.enums.TownJeonNam
import com.example.uaep.enums.TownSejong
import com.example.uaep.enums.TownSeoul
import com.example.uaep.enums.TownUlsan

class SignUpViewModel : ViewModel() {

    private val mName = mutableStateOf("")
    private val mEmail = mutableStateOf("")
    private val mPassword = mutableStateOf("")
    private val mProvince = mutableStateOf("")
    private val mTown = mutableStateOf("")
    private val mMatchingPassword = mutableStateOf("")
    private val mPosition = mutableStateOf("")
    private val mGender = mutableStateOf("")
    private val mLevel = mutableStateOf("")
    private val mGenderEnabled = mutableStateOf(false)
    private val mPosEnabled = mutableStateOf(false)
    private val mProvinceEnabled = mutableStateOf(false)
    private val mLevelEnabled = mutableStateOf(false)
    private val mTownEnabled = mutableStateOf(false)
    val provinceList = listOf(
        Province.SEOUL,
        Province.BUSAN,
        Province.DAEGU,
        Province.INCHEON,
        Province.GWANGJU,
        Province.DAEJEON,
        Province.ULSAN,
        Province.SEJONG,
        Province.GYEONGGI,
        Province.GANGWON,
        Province.CHUNGCHEONGBUK,
        Province.CHUNGCHEONGNAM,
        Province.JEOLLABUK,
        Province.JEOLLANAM,
        Province.GYEONGSANGBUK,
        Province.GYEONGSANGNAM,
        Province.JEJU
    )
    val townSeoulList = listOf(
        TownSeoul.JONGNO_GU,
        TownSeoul.JUNG_GU,
        TownSeoul.YONGSAN_GU,
        TownSeoul.SEONGDONG_GU,
        TownSeoul.GWANGJIN_GU,
        TownSeoul.DONGDAEMUN_GU,
        TownSeoul.JUNGNANG_GU,
        TownSeoul.SEONGBUK_GU,
        TownSeoul.GANGBUK_GU,
        TownSeoul.DOBONG_GU,
        TownSeoul.NOWON_GU,
        TownSeoul.EUNPYEONG_GU,
        TownSeoul.SEODAMUN_GU,
        TownSeoul.MAPO_GU,
        TownSeoul.YANGCHEON_GU,
        TownSeoul.GANGSEO_GU,
        TownSeoul.GURO_GU,
        TownSeoul.GEUMCHEON_GU,
        TownSeoul.YEONGDEUNGPO_GU,
        TownSeoul.DONGJAK_GU,
        TownSeoul.GWANAK_GU,
        TownSeoul.SEOCHO_GU,
        TownSeoul.GANGNAM_GU,
        TownSeoul.SONGPA_GU,
        TownSeoul.GANGDONG_GU
    )
    val townBusanList = listOf(
        TownBusan.JUNG_GU,
        TownBusan.SEO_GU,
        TownBusan.DONG_GU,
        TownBusan.YEONGDO_GU,
        TownBusan.BUSANJIN_GU,
        TownBusan.DONGNAE_GU,
        TownBusan.NAM_GU,
        TownBusan.BUK_GU,
        TownBusan.HAEUNDAE_GU,
        TownBusan.SAHA_GU,
        TownBusan.GEUMJEONG_GU,
        TownBusan.GANGSEO_GU,
        TownBusan.YEONJE_GU,
        TownBusan.SUYEONG_GU,
        TownBusan.SASANG_GU,
        TownBusan.GIJANG_GUN
    )

    val townDaeguList = listOf(
        TownDaegu.DALSEONG_GUN,
        TownDaegu.BUK_GU,
        TownDaegu.DALSEO_GU,
        TownDaegu.DONG_GU,
        TownDaegu.JUNG_GU,
        TownDaegu.NAM_GU,
        TownDaegu.SEO_GU,
        TownDaegu.SUSEONG_GU
    )

    val townIncheonList = listOf(
        TownIncheon.BUPYEONG_GU,
        TownIncheon.JUNG_GU,
        TownIncheon.DONG_GU,
        TownIncheon.GANGHWA_GUN,
        TownIncheon.GYEYANG_GU,
        TownIncheon.MICHUHOL_GU,
        TownIncheon.NAMDONG_GU,
        TownIncheon.YEONSU_GU,
        TownIncheon.SEO_GU,
        TownIncheon.ONGJIN_GUN
    )

    val townGwangjuList = listOf(
        TownGwangju.GWANGSAN_GU,
        TownGwangju.BUK_GU,
        TownGwangju.SEO_GU,
        TownGwangju.NAM_GU,
        TownGwangju.DONG_GU
    )

    val townDaejeonList = listOf(
        TownDaejeon.DONG_GU,
        TownDaejeon.JUNG_GU,
        TownDaejeon.SEO_GU,
        TownDaejeon.YOOSEONG_GU,
        TownDaejeon.DAEDEOK_GU,
    )

    val townUlsanList = listOf(
        TownUlsan.DONG_GU,
        TownUlsan.BUK_GU,
        TownUlsan.JUNG_GU,
        TownUlsan.NAM_GU,
        TownUlsan.ULJU_GUN
    )

    val townSejongList = listOf(
        TownSejong.SEJONG
    )

    val townGyeonggiList = listOf(
        TownGyeonggi.SUWON,
        TownGyeonggi.SEONGNAM,
        TownGyeonggi.GOYANG,
        TownGyeonggi.YONGIN,
        TownGyeonggi.BUCHEON,
        TownGyeonggi.ANSAN,
        TownGyeonggi.ANYANG,
        TownGyeonggi.NAMYANGJU,
        TownGyeonggi.HWASEONG,
        TownGyeonggi.PYEONGTAEK,
        TownGyeonggi.UIJEONGBU,
        TownGyeonggi.SIHEUNG,
        TownGyeonggi.PAJU,
        TownGyeonggi.GWANGMYEONG,
        TownGyeonggi.GIMPO,
        TownGyeonggi.GUNPO,
        TownGyeonggi.GWANGJU,
        TownGyeonggi.ICHEON,
        TownGyeonggi.YANGJU,
        TownGyeonggi.OSAN,
        TownGyeonggi.GURI,
        TownGyeonggi.ANSEONG,
        TownGyeonggi.POCHEON,
        TownGyeonggi.UIWANG,
        TownGyeonggi.HANAM,
        TownGyeonggi.YEOJU,
        TownGyeonggi.YANGPYEONG,
        TownGyeonggi.DONGDUCHEON,
        TownGyeonggi.GWACHEON,
        TownGyeonggi.GAPYEONG_GUN,
        TownGyeonggi.YEONCHEON_GUN,
    )

    val townGangwonList = listOf(
        TownGangwon.CHUNCHEON,
        TownGangwon.WONJU,
        TownGangwon.GANGNEUNG,
        TownGangwon.DONGHAE,
        TownGangwon.TAEBAEK,
        TownGangwon.SOKCHO,
        TownGangwon.SAMCHEOK,
        TownGangwon.HONGCHEON_GUN,
        TownGangwon.HOENGSEONG_GUN,
        TownGangwon.YEONGWOL_GUN,
        TownGangwon.PYEONGCHANG_GUN,
        TownGangwon.JEONGSEON_GUN,
        TownGangwon.CHERWON_GUN,
        TownGangwon.HWACHEON_GUN,
        TownGangwon.YANGGU_GUN,
        TownGangwon.INJE_GUN,
        TownGangwon.GOSEONG_GUN,
        TownGangwon.YANGYANG_GUN,
    )

    val townChungBukList = listOf(
        TownChungBuk.CHEONGJU,
        TownChungBuk.CHUNGJU,
        TownChungBuk.JECHEON,
        TownChungBuk.BOEUN_GUN,
        TownChungBuk.OKCHEON_GUN,
        TownChungBuk.YEONGDONG_GUN,
        TownChungBuk.JINCHEON_GUN,
        TownChungBuk.GOESAN_GUN,
        TownChungBuk.EUMSEONG_GUN,
        TownChungBuk.DANYANG_GUN,
        TownChungBuk.JEUNGPYEONG_GUN,
    )

    val townChungNamList = listOf(
        TownChungNam.CHEONAN,
        TownChungNam.GONGJU,
        TownChungNam.BORYEONG,
        TownChungNam.ASAN,
        TownChungNam.SEOSAN,
        TownChungNam.NONSAN,
        TownChungNam.GYERYONG,
        TownChungNam.DANGJIN,
        TownChungNam.GEUMSAN_GUN,
        TownChungNam.BUYEO_GUN,
        TownChungNam.SEOCHEON_GUN,
        TownChungNam.CHEONGYANG_GUN,
        TownChungNam.HONGSEONG_GUN,
        TownChungNam.YESAN_GUN,
        TownChungNam.TAEAN_GUN,
    )

    val townJeonBukList = listOf(
        TownJeonBuk.JEONJU,
        TownJeonBuk.GUNSAN,
        TownJeonBuk.IKSAN,
        TownJeonBuk.JEONGEUP,
        TownJeonBuk.NAMWON,
        TownJeonBuk.GIMJE,
        TownJeonBuk.WANJU_GUN,
        TownJeonBuk.JINAN_GUN,
        TownJeonBuk.MUJU_GUN,
        TownJeonBuk.JANGSU_GUN,
        TownJeonBuk.IMSIL_GUN,
        TownJeonBuk.SUNCHANG_GUN,
        TownJeonBuk.GOCHANG_GUN,
        TownJeonBuk.BUAN_GUN,
    )

    val townJeonNamList = listOf(
        TownJeonNam.MOKPO,
        TownJeonNam.YEOSU,
        TownJeonNam.SUNCHEON,
        TownJeonNam.NAJU,
        TownJeonNam.GWANGYANG,
        TownJeonNam.DAMYANG_GUN,
        TownJeonNam.GOKSEONG_GUN,
        TownJeonNam.GURYE_GUN,
        TownJeonNam.GOHEUNG_GUN,
        TownJeonNam.BOSEONG_GUN,
        TownJeonNam.HWASUN_GUN,
        TownJeonNam.JANGHEUNG_GUN,
        TownJeonNam.GANGJIN_GUN,
        TownJeonNam.HAENAM_GUN,
        TownJeonNam.YEONGAM_GUN,
        TownJeonNam.MUNAN_GUN,
        TownJeonNam.HAMPYEONG_GUN,
        TownJeonNam.YOUNGGWANG_GUN,
        TownJeonNam.JANGSEONG_GUN,
        TownJeonNam.WANDO_GUN,
        TownJeonNam.JINDO_GUN,
        TownJeonNam.SINAN_GUN,
    )

    val townGyungBukList = listOf(
        TownGyungBuk.POHANG,
        TownGyungBuk.GYEONGJU,
        TownGyungBuk.GIMCHEON,
        TownGyungBuk.ANDONG,
        TownGyungBuk.GUMI,
        TownGyungBuk.YEONGJU,
        TownGyungBuk.YEONGCHEON,
        TownGyungBuk.SANGJU,
        TownGyungBuk.MUNGYEONG,
        TownGyungBuk.GYEONGSAN,
        TownGyungBuk.GUNWI_GUN,
        TownGyungBuk.UISEONG_GUN,
        TownGyungBuk.CHEONGSONG_GUN,
        TownGyungBuk.YEONGYANG_GUN,
        TownGyungBuk.YEONGDEOK_GUN,
        TownGyungBuk.CHEONGDO_GUN,
        TownGyungBuk.GORYEONG_GUN,
        TownGyungBuk.SEONGJU_GUN,
        TownGyungBuk.CHILGOK_GUN,
        TownGyungBuk.YECHEON_GUN,
        TownGyungBuk.BONGHWA_GUN,
        TownGyungBuk.ULJIN_GUN,
        TownGyungBuk.ULLEUNG_GUN,
    )

    val townGyungNamList = listOf(
        TownGyungNam.CHANGWON,
        TownGyungNam.JINJU,
        TownGyungNam.TONGYEONG,
        TownGyungNam.GIMHAE,
        TownGyungNam.SACHEON,
        TownGyungNam.MIRYANG,
        TownGyungNam.GEOJE,
        TownGyungNam.YANGSAN,
        TownGyungNam.UIRYEONG_GUN,
        TownGyungNam.HAMAN_GUN,
        TownGyungNam.CHANGNYEONG_GUN,
        TownGyungNam.GOSEONG_GUN,
        TownGyungNam.NAMHAE_GUN,
        TownGyungNam.HADONG_GUN,
        TownGyungNam.SANCHEONG_GUN,
        TownGyungNam.HAMYANG_GUN,
        TownGyungNam.GEOCHANG_GUN,
        TownGyungNam.HAPCHEON_GUN
    )

    val townJejuList = listOf(
        TownJeju.JEJU,
        TownJeju.SEOGWIPO,
    )

    val name: State<String> = mName
    val email: State<String> = mEmail
    val password: State<String> = mPassword
    val province: State<String> = mProvince
    val town: State<String> = mTown
    val position: State<String> = mPosition
    val matchingPassword: State<String> = mMatchingPassword
    val gender: State<String> = mGender
    val level: State<String> = mLevel
    val genderEnabled: State<Boolean> = mGenderEnabled
    val posEnabled: State<Boolean> = mPosEnabled
    val provinceEnabled: State<Boolean> = mProvinceEnabled
    val levelEnabled: State<Boolean> = mLevelEnabled
    val townEnabled: State<Boolean> = mTownEnabled
    val icon1:ImageVector
        @Composable get() = if (mGenderEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon2:ImageVector
        @Composable get() = if (mPosEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon3:ImageVector
        @Composable get() = if (mProvinceEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon4:ImageVector
        @Composable get() = if (mLevelEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon5:ImageVector
        @Composable get() = if (mTownEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    fun updateName(name: String) {
        mName.value = name
    }

    fun updateEmail(email: String) {
        mEmail.value = email
    }

    fun updateProvince(province: String) {
        mProvince.value = province;
    }

    fun updateTown(town: String) {
        mTown.value = town;
    }

    fun updateLevel(level: String) {
        mLevel.value = level;
    }

    fun updatePosition(position: Position) {
        mPosition.value = position.value;
    }

    fun updatePassword(password: String) {
        mPassword.value = password
    }

    fun updateMatchingPassword(matchingPassword: String) {
        mMatchingPassword.value = matchingPassword
    }

    fun updateGender(gender: String) {
        mGender.value = gender
    }

    fun onGenderEnabled(enabled: Boolean) {
        mGenderEnabled.value = enabled
    }

    fun onPosEnabled(enabled: Boolean) {
        mPosEnabled.value = enabled
    }

    fun onProvinceEnabled(enabled: Boolean) {
        mProvinceEnabled.value = enabled
    }

    fun onLevelEnabled(enabled: Boolean) {
        mLevelEnabled.value = enabled
    }

    fun onTownEnabled(enabled: Boolean) {
        mTownEnabled.value = enabled
    }

    fun isSamePassword(password: String, matchingPassword: String): Boolean {
        return password == matchingPassword
    }
}