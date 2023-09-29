package com.vistony.salesforce.kotlin.View.Atoms

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Utilities.ConvertDateSAPaUserDate
import com.vistony.salesforce.kotlin.Utilities.getDate
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.components.ButtonCircle
import java.util.*

@Composable
fun CalendarApp(
    DateApp:MutableState<String>
){
    // Fetching the Local Context
    val mContext = LocalContext.current
    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()
    val mDate = remember { mutableStateOf(getDate()) }

    if (mDate.value!=DateApp.value)
    {
        DateApp.value= mDate.value!!
    }

    //DateApp.value= getDate()!!
    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            var month:String=""
            var day:String=""
            if(mMonth.toString().length==1){month="0"+(mMonth+1).toString()}else{month=(mMonth+1).toString()}
            if(mDayOfMonth.toString().length==1){day="0"+mDayOfMonth.toString()}else{day=mDayOfMonth.toString()}
            mDate.value = //"$mDayOfMonth/${mMonth+1}/$mYear"
                "$mYear$month$day"
            //DateApp.value= mDate.value!!
        }, mYear, mMonth, mDay
    )

    Column(
        modifier = Modifier
           // .fillMaxSize()
        , verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row(
            Modifier.border(4.dp, BlueVistony).align(Alignment.CenterHorizontally)
            , horizontalArrangement= Arrangement.Center,verticalAlignment = Alignment.CenterVertically,
        ) {
            //Text(text = "${mDate.value}", fontSize = 18.sp, textAlign = TextAlign.Center)
            ButtonCircle(
                OnClick = {mDatePickerDialog.show()}
                , roundedCornerShape = RoundedCornerShape(4.dp)
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_calendar_month_24),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                    //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                )
            }
            TableCell(text = ConvertDateSAPaUserDate(mDate.value)!!
                , weight = 1f
                ,title = true, textAlign = TextAlign.Center)

        }
    }
}