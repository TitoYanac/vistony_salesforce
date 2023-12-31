package com.vistony.salesforce.kotlin.View.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun HeaderImage(modifier: Modifier,type:String) {
    var spec:LottieCompositionSpec= LottieCompositionSpec.Url ("")
    if(type.equals("Geolocation"))
    {
        spec = LottieCompositionSpec.Url ("https://assets8.lottiefiles.com/packages/lf20_svy4ivvy.json")
    }
    else if(type.equals("Question"))
    {
        spec = LottieCompositionSpec.Url ("https://assets2.lottiefiles.com/packages/lf20_LIU4vHuu1W.json")
    }
    val composition by rememberLottieComposition(
        //spec = LottieCompositionSpec.RawRes(R.raw.location)
        //spec = LottieCompositionSpec.Url ("https://assets10.lottiefiles.com/packages/lf20_5xqvi8pf.json")
        spec


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