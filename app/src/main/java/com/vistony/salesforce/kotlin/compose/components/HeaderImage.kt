package com.vistony.salesforce.kotlin.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vistony.salesforce.R


@Composable
fun HeaderImage(modifier: Modifier) {
    val composition by rememberLottieComposition(
        //spec = LottieCompositionSpec.RawRes(R.raw.location)
        //spec = LottieCompositionSpec.Url ("https://assets10.lottiefiles.com/packages/lf20_5xqvi8pf.json")
        spec = LottieCompositionSpec.Url ("https://assets8.lottiefiles.com/packages/lf20_svy4ivvy.json")
        //spec = LottieCompositionSpec.Url ("https://www.facebook.com/photo/?fbid=473942281432230&set=a.473942234765568")
    )
    val progress by animateLottieCompositionAsState(composition = composition)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}
/*
* https://accounts.lottiefiles.com/
* */