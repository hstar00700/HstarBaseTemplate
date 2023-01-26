package com.hstar.presentation.view

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.hstar.base.presentation.BaseActivity
import com.hstar.domain.sample.entity.UserEntity
import com.hstar.presentation.comon.component.theme.O2oTheme
import com.orhanobut.logger.Logger
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun init() {
        setObserve()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            run {
                O2oTheme {
                    Surface {
                        Modifier.padding(vertical = 25.dp)
                        MainScreen()
                    }
                }
            }
        }
    }

    private fun setObserve() {
        viewModel.userList.observe(this) {
            Logger.d("User List - $it")
            val sb = StringBuilder()
            it.forEach { user ->
                sb.append("${user.firstName} ${user.lastName} \n")
            }
        }
    }

    @Preview
    @Composable
    fun MainScreen() {
        Logger.i("Call MainScreen!!")
        UserDetailsScreen(
            onErrorLoading = {},
        )
    }

    @Composable
    fun UserDetailsScreen(
        onErrorLoading: () -> Unit,
        modifier: Modifier = Modifier,
        viewModel: MainViewModel = hiltViewModel<MainViewModel>()
    ) {
        var scrollState = rememberScrollState()

        // 1. produceState 를 사용하는 방법
//        val uiState by produceState(initialValue = UserUseCaseUiState(isLoading = true)) {
//            val userResult = viewModel.userList
//            //TODO: network error found check!!
//            value = userResult.value?.let {
//                UserUseCaseUiState(userDetails = it, isLoading = false, throwError = false)
//            } ?: UserUseCaseUiState(userDetails = emptyList(), throwError = true)
//        }
//
//        Logger.w("Ui state : $uiState")
//
//        when {
//            !uiState.throwError && uiState.userDetails != null -> {
//
//                Column {
//                    for (user in uiState.userDetails!!) {
//                        key(user.id) {
//                            DetailsContent(user, modifier.fillMaxSize())
//                        }
//                    }
//                }
//            }
//            uiState.isLoading -> {
//                Box(modifier.fillMaxSize()) {
//                    CircularProgressIndicator(
//                        color = MaterialTheme.colors.onSurface,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//            }
//            else -> { onErrorLoading() }
//        }

        // 2. 라이브데이터 직접 스테이트로 변환해서 사용하는 방법
        val userList = viewModel.userList.observeAsState(initial = emptyList())
        Logger.i("User list in Compose - $userList")
        Spacer(modifier = Modifier.padding(0.dp, 25.dp))
        when {
            userList.value.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(500.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(userList.value) { _, item ->
                        UserInfoCard(item)
                    }
                }
            }
            else -> {
                onErrorLoading()
                Box(modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }

    @Composable
    fun UserInfoCard(userData: UserEntity) {
        Card(
            Modifier
                .padding(12.dp)
                //.border(width = 4.dp, color = Color.White)
                .fillMaxWidth()
                .height(450.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(
                    Modifier
                        .padding(2.dp)
                        .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(30.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = userData.firstName + userData.lastName,
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = userData.email,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    GlideImage(
                        imageModel = userData.avatar,
                        // Crop, Fit, Inside, FillHeight, FillWidth, None
                        contentScale = ContentScale.Crop,
                        // shows an image with a circular revealed animation.
                        circularReveal = CircularReveal(duration = 250),
                        // shows a placeholder ImageBitmap when loading.
                        placeHolder = null,//ImageBitmap.imageResource(R.drawable.ic_unchecked),
                        // shows an error ImageBitmap when the request failed.
                        error = null, //ImageBitmap.imageResource(R.drawable.ic_unchecked),
                        modifier = Modifier
                            .width(600.dp)
                            .height(250.dp)
                    )
                }

            }
        }
    }

    @Composable
    fun BackHandler(backDispatcher: OnBackPressedDispatcher, onBack: () -> Unit) {

        // back key 클릭시 처리할 최신 동작을 저장 (onBack이 여러번 변경되어 호출되는 경우 대비)
        val currentOnBack by rememberUpdatedState(onBack)   // remember 변수는 액티비티 재생성시 초기화됨!! --> rememberSavable을 사용하면 해결!!

        // OnBackPressedCallback을 구현하여 remember로 저장
        val backCallback = remember {
            // Always intercept back events. See the SideEffect for
            // a more complete version
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    currentOnBack()
                }
            }
        }

        // `backDispatcher` 변경시 기존 콜백 remove후 재시작
        DisposableEffect(backDispatcher) {
            // Add callback to the backDispatcher
            backDispatcher.addCallback(backCallback)
            // When the effect leaves the Composition, remove the callback
            onDispose {
                backCallback.remove()
            }
        }
    }

}