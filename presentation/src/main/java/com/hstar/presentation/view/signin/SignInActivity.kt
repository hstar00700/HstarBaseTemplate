package com.hstar.presentation.view.signin

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.hong.presentation.R
import com.hstar.base.presentation.BaseActivity
import com.hstar.base.presentation.util.repeatOnStarted
import com.hstar.domain.sample.entity.UserEntity
import com.hstar.presentation.comon.component.ImageWholeBackground
import com.hstar.presentation.comon.component.theme.BaseTheme
import com.orhanobut.logger.Logger
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity() {
    private val viewModel: SignInViewModel by viewModels()

    override fun init() {
        setObserve()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            run {
                BaseTheme {
                    Surface {
                        ImageWholeBackground(
                            backgroundDrawableResId = R.drawable.bg_sign_in,
                            modifier = Modifier.fillMaxSize()
                        )
                        SignInScreen()
                    }
                }
            }
        }
    }

    private fun setObserve() {
        // 1. 기존 옵저버 패턴을 이용하는 경우 : 플랫폼에 의존성이 생기므로 이벤트 플로우로 대체하기로 함
//        viewModel.singInResult.asLiveData().observe(this) {
//            Logger.d("User List - $it")
//        }

        // 2. 코루틴 스코프 사용
//        lifecycleScope.launch {
//            // repeatOnLifecycle launches the block in a new coroutine every time the
//            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                // Trigger the flow and start listening for values.
//                // Note that this happens when lifecycle is STARTED and stops
//                // collecting when the lifecycle is STOPPED
//                viewModel.singInResult.collect { result ->
//                    // New value received
//                    when (result) {
////                        is LatestNewsUiState.Success -> showFavoriteNews(uiState.news)
////                        is LatestNewsUiState.Error -> showError(uiState.exception)
//                        //TODO: 로그인 플로우 처리!!
//
//                    }
//                }
//            }
//        }

        // 3. 최종버전
        repeatOnStarted {   // repeatOnLifecycle(Lifecycle.State.STARTED)와 같은 코드를 수행
            viewModel.signInEvent.collect { event ->
                when(event) {
                    is SignInViewModel.Event.LoginEvent -> {
                        //TODO: do event
                    }
                    is SignInViewModel.Event.LoadUsersEvent -> {
                        //TODO: do event
                        // UserDetailsScreen 에 해당데이터로 뷰를 리콤포즈하는 코드를 작성!!
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun SignInScreen() {
    Logger.i("Call MainScreen!!")
    LogInView(modifier = Modifier.fillMaxSize(), onErrorLoading = { })
}

@Composable
fun LogInView(
    onErrorLoading: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel<SignInViewModel>()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            Modifier
                .padding(12.dp)
                .background(color = Color.Black)
                .border(width = 3.dp, color = Color.White, shape = RoundedCornerShape(20.dp))
                .width(941.dp)
                .height(588.dp),
            backgroundColor = Color.Black,
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ImageWholeBackground(
                    contentScale = ContentScale.Crop,
                    backgroundDrawableResId = R.drawable.kds_logo_small,
                    modifier = Modifier
                        .height(80.dp)
                        .width(240.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        OutlinedTextField(
                            modifier = Modifier
                                .width(525.dp)
                                .height(68.dp)
                                .padding(2.dp),
                            value = "",
                            onValueChange = {},
                            label = { Text(text = "아이디") }
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .width(525.dp)
                                .height(68.dp)
                                .padding(2.dp),
                            value = "",
                            onValueChange = {},
                            label = { Text(text = "비밀번호") }
                        )
                    }

                    Button(
                        modifier = Modifier
                            .width(160.dp)
                            .height(158.dp)
                            .padding(20.dp, 2.dp),
                        onClick = { /*TODO*/ }) {
                        Text(text = "로그인")
                    }

                }


            }
        }
    }

}

@Composable
fun UserDetailsScreen(
    onErrorLoading: () -> Unit,
    modifier: Modifier = Modifier,
    userList: List<UserEntity>
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

    // 2. 라이브데이터 직접 스테이트로 변환해서 사용하는 방법 : observeAsState 찾아볼것!!!
    //val userList = viewModel.userList.value

    Logger.i("User list in Compose - $userList")
    Spacer(modifier = Modifier.padding(0.dp, 25.dp))
    when {
        userList.isNotEmpty() -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .height(500.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(userList) { _, item ->
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
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(30.dp)
                    ),
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