object Deps {
    val rxJavaPaging by lazy {"androidx.paging:paging-rxjava3:${Versions.paging}"}
    val rxJava by lazy {"io.reactivex.rxjava3:rxjava:${Versions.rxJava}"}
    val rxAndroid by lazy {"io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid}"}
    val rxJavaRetrofitAdapter by lazy {"com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"}
    val rxJavaRoom by lazy {"androidx.room:room-rxjava3:${Versions.room}"}
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val lifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" }
    val composeMaterial by lazy { "androidx.compose.material:material:${Versions.compose_ui_version}" }
    val composeTooling by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.compose_ui_version}" }
    val composeUi by lazy { "androidx.compose.ui:ui:${Versions.compose_ui_version}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.compose_activity}" }
    val composeUiTest by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.compose_ui_version}" }
    val uiManifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.compose_ui_version}" }
    val legacy by lazy { "androidx.legacy:legacy-support-v4:${Versions.legacy}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.daggerHilt}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}" }
    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}" }
    val room by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val roomPaging by lazy {"androidx.room:room-paging:${Versions.room}"}
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val coroutineCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val coroutinesPlay by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val moshi by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}" }
    val coroutinesAdapter by lazy { "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitAdapter}" }
    val canary by lazy { "com.squareup.leakcanary:leakcanary-android:${Versions.canary}" }
    val chuckerLib by lazy { "com.github.chuckerteam.chucker:library:${Versions.chucker}" }
    val chuckerNoOp by lazy { "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}" }
    val coil by lazy { "io.coil-kt:coil-compose:${Versions.coil}" }
    val swipeRefresh by lazy { "com.google.accompanist:accompanist-swiperefresh:${Versions.swipeRefresh}" }
    val viewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}" }
    val pagingCompose by lazy { "androidx.paging:paging-compose:${Versions.pagingCompose}" }
    val paging by lazy {"androidx.paging:paging-runtime-ktx:${Versions.paging}"}
    val hiltCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltCompose}" }
    val materialIcons by lazy { "androidx.compose.material:material-icons-extended:${Versions.compose_ui_version}" }
    val flowLayout by lazy { "com.google.accompanist:accompanist-flowlayout:${Versions.flowLayout}" }
    val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okHttp}" }
    val interceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}" }
    val composeNavigation by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }
    val composeLifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-compose:${Versions.compose_lifecycle}" }
    val jsonSerialize by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.json_serialization}" }
    val coilSvg by lazy {"io.coil-kt:coil-svg:${Versions.coil}"}
    val coilAccompanist by lazy {"com.google.accompanist:accompanist-coil:${Versions.accompanist_coil}"}

    //Tests
    val coroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}" }
    val junitTest by lazy { "junit:junit:${Versions.junit}" }
    val junitExt by lazy { "androidx.test.ext:junit:${Versions.junitExt}" }
    val coreTest by lazy { "androidx.arch.core:core-testing:${Versions.core}" }
    val turbine by lazy { "app.cash.turbine:turbine:${Versions.turbine}" }
    val mockk by lazy { "io.mockk:mockk:${Versions.mockk}" }
    val truth by lazy { "com.google.truth:truth:${Versions.truth}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }

}