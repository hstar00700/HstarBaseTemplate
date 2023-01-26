package com.hstar.presentation.view.battery

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.hstar.base.presentation.BaseActivity
import com.hstar.base.presentation.util.repeatOnStarted
import com.hstar.base.util.PowerConnectionReceiver
import com.hstar.base.util.registerBatteryReceiver
import com.hstar.domain.sample.entity.UserEntity
import com.hstar.presentation.comon.component.theme.BaseTheme
import com.hstar.presentation.view.signin.SignInViewModel
import com.orhanobut.logger.Logger
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BatteryInfoActivity : BaseActivity() {
    private val viewModel: BatteryViewModel by viewModels()
    private val batteryReceiver: PowerConnectionReceiver by lazy { PowerConnectionReceiver() }

    override fun init() {
        observeChange()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            run {
                BaseTheme {
                    Surface {
                        Modifier.padding(vertical = 25.dp)
                        MainScreen()
                    }
                }
            }
        }

        init()
    }

    override fun onResume() {
        super.onResume()
        initBatteryReceiver()
    }

    override fun onPause() {
        super.onPause()
        Logger.d("releaseBatteryReceiver")
        releaseBatteryReceiver()
    }

    private fun initBatteryReceiver() {
        val intent = this.registerBatteryReceiver(batteryReceiver)
        intent ?: Logger.e("Error : null intent!!")
    }

    private fun releaseBatteryReceiver() {
        runCatching {
            unregisterReceiver(batteryReceiver)
        }.onFailure(Throwable::printStackTrace)
    }

    private fun observeChange() {
        repeatOnStarted {
            viewModel.eventFlow.collect { event ->
                when(event) {
                    is BatteryViewModel.Event.ChangeCurrentRate -> {
                        //TODO: do event
                        Logger.e("ChangeCurrentRate Event : $event")
                    }
                    is BatteryViewModel.Event.ChangeTemperature -> {
                        //TODO: do event
                        Logger.e("ChangeTemperature Event : $event")
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun MainScreen() {
        UserDetailsScreen(
            onErrorLoading = {},
        )
    }

    @Composable
    fun UserDetailsScreen(
        onErrorLoading: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Text(text = "")
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
}