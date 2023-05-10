package com.vistony.salesforce.Controller.Adapters;

import static android.app.Activity.RESULT_OK;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ComponentActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.google.android.material.textfield.TextInputLayout;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Controller.Utilitario.VideoRecorder;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintResponseEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintSectionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.CustomerComplaintView;
import com.vistony.salesforce.View.LoginView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import org.w3c.dom.Text;

public class ListCustomerComplaintAdapter extends
        ArrayAdapter<CustomerComplaintSectionEntity>
{
    Context context;
    Activity activity;
    public static String foto_id;
    final FormulasController formulasController=new FormulasController(getContext());
    boolean started=false;
    VideoRecorder recorder = new VideoRecorder();
    TextView tv_questions,tv_start_video;
    SurfaceView surfaceView;
    LifecycleOwner lifecycleOwner;
    int x=0,y=0;
    List<CustomerComplaintSectionEntity> objects;
    String responsespinner="";
    public ListCustomerComplaintAdapter(
            Context context,
            List<CustomerComplaintSectionEntity> objects,
            Activity activity,
            LifecycleOwner lifecycleOwner

    ) {
        super(context, 0, objects);
        this.context=context;
        this.activity=activity;
        this.lifecycleOwner=lifecycleOwner;
        this.objects=objects;
        CustomerComplaintView.RutaFilePhoto.observe(lifecycleOwner, data -> {
            Log.e("REOS","ListCustomerComplaintAdapter.customerComplaintView.Cons.RutaFilePhoto.: "+data);
            //Log.e("REOS","ListCustomerComplaintAdapter.customerComplaintView.observe.position.: "+position);
            //Log.e("REOS", "ListCustomerComplaintAdapter-lead.getSection(): " + lead.getSection());
            AddMedia(this.objects,x,y,data.toString(),"F");
        });
        CustomerComplaintView.RutaFileVideo.observe(lifecycleOwner, data -> {
                Log.e("REOS","ListCustomerComplaintAdapter.customerComplaintView.observe.RutaFileVideo: "+data);
                //AddMedia(lead,y,data.toString(),"V");
            AddMedia(this.objects,x,y,data.toString(),"V");
            });

            CustomerComplaintView.RutaFileAttach.observe(lifecycleOwner, data -> {
                Log.e("REOS","ListCustomerComplaintAdapter.customerComplaintView.observe.RutaFileAttach: "+data);
                //AddMedia(lead,y,data.toString(),"A");
                AddMedia(this.objects,x,y,data.toString(),"A");
            });
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {

            Log.e("REOS", "ListCustomerComplaintAdapter-getView-inicio");
            // Obtener inflater.
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final ListCustomerComplaintAdapter.ViewHolder holder;
            convertView=null;
            // ¿Ya se infló este view?
            if (null == convertView) {
                //Si no existe, entonces inflarlo con image_list_view.xml
                convertView = inflater.inflate(
                        R.layout.layout_list_customer_complaint,
                        parent,
                        false);

                holder = new ListCustomerComplaintAdapter.ViewHolder();
                holder.tv_section = (TextView) convertView.findViewById(R.id.tv_section);
                holder.content = (LinearLayout) convertView.findViewById(R.id.content);
                holder.imv_show_more = (ImageView) convertView.findViewById(R.id.imv_show_more);
                //holder.StateProgressbar = (StateProgressBar) convertView.findViewById(R.id.StateProgressbar);
                holder.horizontalStepView = (HorizontalStepView) convertView.findViewById(R.id.HorizontalStepView);
                convertView.setTag(holder);
            } else {
                holder = (ListCustomerComplaintAdapter.ViewHolder) convertView.getTag();
            }
            final CustomerComplaintSectionEntity lead = getItem(position);

            Log.e("REOS", "ListCustomerComplaintAdapter-getView-inicio");
            Log.e("REOS", "ListCustomerComplaintAdapter-lead.getListCustomerComplaintSection().size()" + lead.getSection());
            holder.tv_section.setText(lead.getSection());

            /*for (int i = 0; i < lead.getSection().size(); i++) {
                holder.tv_section.setText(lead.getListCustomerComplaintSection().get(i).getSection());
                Log.e("REOS", "ListCustomerComplaintAdapter-getView-lead.getListCustomerComplaintSection().get(i).getSection()" + lead.getListCustomerComplaintSection().get(i).getSection());
            }*/
            lead.setSection_id(String.valueOf(position));
            if(holder.content.getChildCount()==0)
            {
                addViews(holder,lead);
            }
            updateStepView(holder,lead);

            /*holder.imv_show_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(holder.content.getChildCount()==0)
                    {
                        addViews(holder,lead);
                    }
                    else {
                        holder.content.removeAllViews();
                        Resources res2 = getContext().getResources(); // need this to fetch the drawable
                        Drawable draw = res2.getDrawable( R.drawable.ic_baseline_expand_more_24_white);
                        holder.imv_show_more.setImageDrawable(draw);
                    }
                }
            });*/




        ;//设置StepsViewIndicator AttentionIcon

            //CustomerComplaintView customerComplaintView=new CustomerComplaintView();
            /*CustomerComplaintView.RutaFilePhoto.observe(lifecycleOwner, data -> {
                Log.e("REOS","ListCustomerComplaintAdapter.customerComplaintView.observe.RutaFilePhoto.: "+data);
                Log.e("REOS","ListCustomerComplaintAdapter.customerComplaintView.observe.position.: "+position);
                Log.e("REOS", "ListCustomerComplaintAdapter-lead.getSection(): " + lead.getSection());
                AddMedia(lead,y,data.toString(),"F");
            });*/

            /*CustomerComplaintView.RutaFileVideo.observe(lifecycleOwner, data -> {
                Log.e("REOS","ListCustomerComplaintAdapter.customerComplaintView.observe.RutaFileVideo: "+data);
                AddMedia(lead,y,data.toString(),"V");
            });

            CustomerComplaintView.RutaFileAttach.observe(lifecycleOwner, data -> {
                Log.e("REOS","ListCustomerComplaintAdapter.customerComplaintView.observe.RutaFileAttach: "+data);
                AddMedia(lead,y,data.toString(),"A");
            });*/




        }catch (Exception e){
            Log.e("REOS", "ListCustomerComplaintAdapter-getView-error:"+e.toString());
        }
        return convertView;
    }

    public void updateStepView(ListCustomerComplaintAdapter.ViewHolder holder, CustomerComplaintSectionEntity lead)
    {
        try {
            List<StepBean> stepsBeanList = new ArrayList<>();
            Log.e("REOS", "ListCustomerComplaintAdapter-updateStepView-lead.getListCustomercomplaintQuestions().size():" + lead.getListCustomercomplaintQuestions().size());
            Log.e("REOS", "ListCustomerComplaintAdapter-updateStepView.getListCustomerComplaintSection().size()" + lead.getSection());

            for (int i = 0; i < lead.getListCustomercomplaintQuestions().size(); i++) {
                Integer status=-1;
                Log.e("REOS", "ListCustomerComplaintAdapter-updateStepView-ingreso bucle");

                if(lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse()!=null)
                {
                    if(lead.getListCustomercomplaintQuestions().get(i).getType().equals("RM"))
                    {

                        if(lead.getListCustomercomplaintQuestions().get(i).getQuestionAnswered()==null)
                        {
                            status = -1;
                        }else {
                            if(lead.getListCustomercomplaintQuestions().get(i).getQuestionAnswered().equals("Y"))
                            {
                                status = 1;
                            }else {
                                status = -1;
                            }
                        }


                    }
                    else if(lead.getListCustomercomplaintQuestions().get(i).getType().equals("RA")){
                        if(lead.getListCustomercomplaintQuestions().get(i).getQuestionAnswered()==null)
                        {
                            status = -1;
                        }else {
                            if(lead.getListCustomercomplaintQuestions().get(i).getQuestionAnswered().equals("Y"))
                            {
                                status = 1;
                            }else {
                                status = -1;
                            }
                        }
                    }
                    else{
                        status = 1;
                    }

                }
                else {
                    status = -1;
                }

                StepBean stepBean = new StepBean("", status);
                //StepBean stepBean1 = new StepBean("",1);
                //StepBean stepBean2 = new StepBean("",1);
                //StepBean stepBean3 = new StepBean("",0);
                //StepBean stepBean4 = new StepBean("",-1);
                stepsBeanList.add(stepBean);
            }

            Log.e("REOS", "ListCustomerComplaintAdapter-updateStepView-stepsBeanList.size(): "+stepsBeanList.size());
            holder.horizontalStepView.clearAnimation();
            holder.horizontalStepView
                    .setStepViewTexts(stepsBeanList)//总步骤
                    .setTextSize(12)//set textSize
                    .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(context, R.color.colorPrimary))//设置StepsViewIndicator完成线的颜色
                    .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(context, R.color.gray))//设置StepsViewIndicator未完成线的颜色
                    .setStepViewComplectedTextColor(ContextCompat.getColor(context, R.color.colorPrimary))//设置StepsView text完成线的颜色
                    .setStepViewUnComplectedTextColor(ContextCompat.getColor(context, R.color.gray))//设置StepsView text未完成线的颜色
                    .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_circle_24_blue))//设置StepsViewIndicator CompleteIcon
                    .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(context, R.drawable.ic_baseline_radio_button_checked_24_gray))//设置StepsViewIndicator DefaultIcon
                    .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(context, R.drawable.attention));
        }catch (Exception e){
            Log.e("REOS", "ListCustomerComplaintAdapter-updateStepView.error:" + e.toString());
        }
    }

    public void addViews(ListCustomerComplaintAdapter.ViewHolder holder, CustomerComplaintSectionEntity lead){
        for(int i=0;i<lead.getListCustomercomplaintQuestions()
                .size();i++)
        {
            //holder.StateProgressbar.setMaxStateNumber(getStatusProgressbarMaxStateNumber(lead.getListCustomercomplaintQuestions().size()));
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            int id = R.layout.layout_list_customer_complaint_questions;
            RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(id, null, false);
            tv_questions = (TextView) relativeLayout.findViewById(R.id.tv_questions);
            LinearLayout content_questions = (LinearLayout) relativeLayout.findViewById(R.id.content_questions);
            tv_questions.setText(lead.getListCustomercomplaintQuestions().get(i).getQuestion());
            int q=0;
            if(lead.getListCustomercomplaintQuestions().get(i).getQuestionAnswered()==null)
            {
                tv_questions.setBackgroundColor(activity.getResources().getColor(R.color.gray));
            }else {
                if(lead.getListCustomercomplaintQuestions().get(i).getQuestionAnswered().equals("Y"))
                {
                    tv_questions.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
                }else {
                    tv_questions.setBackgroundColor(activity.getResources().getColor(R.color.gray));
                }
            }

            holder.content.addView(relativeLayout);
            lead.getListCustomercomplaintQuestions().get(i).setQuestionNumber(String.valueOf(i));

            if(content_questions.getChildCount()==0)
            {
                //for(int j=0;j<lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().size();j++)
                //{
                LayoutInflater layoutInflater1 = LayoutInflater.from(getContext());
                int id1 = R.layout.layout_list_customer_complaint_response;
                RelativeLayout relativeLayout1 = (RelativeLayout) layoutInflater1.inflate(id1, null, false);
                TextView tv_response = (TextView) relativeLayout1.findViewById(R.id.tv_response);
                TextInputLayout ti_response = (TextInputLayout) relativeLayout1.findViewById(R.id.ti_response);
                RadioGroup rg_response = (RadioGroup) relativeLayout1.findViewById(R.id.rg_response);
                LinearLayout ll_checkbox=(LinearLayout) relativeLayout1.findViewById(R.id.ll_checkbox);
                //TableLayout tl_add_adjunt=(TableLayout) relativeLayout1.findViewById(R.id.tl_add);
                EditText et_response=(EditText) relativeLayout1.findViewById(R.id.et_response);
                Spinner sp_response=(Spinner) relativeLayout1.findViewById(R.id.sp_response);
                //surfaceView=(SurfaceView) relativeLayout1.findViewById(R.id.surfaceView);
                tv_start_video=(TextView) relativeLayout1.findViewById(R.id.tv_start_video);
                CheckBox checkBox=new CheckBox(context);
                TableLayout tl_add_attach=(TableLayout) relativeLayout1.findViewById(R.id.tl_add_attach);
                CardView cv_add_attach=(CardView) relativeLayout1.findViewById(R.id.cv_add_attach);
                LinearLayout ll_attach = (LinearLayout) relativeLayout1.findViewById(R.id.ll_attach);

                // Crear una lista de CheckBoxes




                //x=i;
                Log.e("REOS", "ListCustomerComplaintAdapter-updateStepView.lead.getListCustomercomplaintQuestions().x:" + x);
                et_response.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean procesado = false;

                        if (actionId == EditorInfo.IME_ACTION_SEND)
                        {
                            if(v.length()==0)
                            {

                            }
                            else {

                            }
                        }
                        return procesado;
                    }

                });
                if(lead.getListCustomercomplaintQuestions().get(i).getType().equals("RT"))
                {
                    et_response.setHint((lead.getListCustomercomplaintQuestions().get(i).getQuestion()));

                    if(lead.getListCustomercomplaintQuestions().get(i).getQuestionsEdit().equals("RU"))
                    {
                        tv_response.setVisibility(View.GONE);
                        rg_response.setVisibility(View.GONE);
                        ll_checkbox.setVisibility(View.GONE);
                        //tl_add_adjunt.setVisibility(View.GONE);
                        sp_response.setVisibility(View.GONE);
                        //surfaceView.setVisibility(View.GONE);
                        tl_add_attach.setVisibility(View.GONE);
                    }
                    else if(lead.getListCustomercomplaintQuestions().get(i).getQuestionsEdit().equals("RS")){

                        if(lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse()==null)
                        {
                            tv_response.setText("Sin datos que mostrar");
                        }else {
                            lead.getListCustomercomplaintQuestions().get(i).setQuestionAnswered("Y");
                            for(int j=0;j<lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().size();j++)
                            {
                                tv_response.setText(lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse());
                                tv_questions.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
                                lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).setResponseChoisse("Y");
                            }
                        }


                        ti_response.setVisibility(View.GONE);
                        rg_response.setVisibility(View.GONE);
                        ll_checkbox.setVisibility(View.GONE);
                        //tl_add_adjunt.setVisibility(View.GONE);
                        sp_response.setVisibility(View.GONE);
                        //surfaceView.setVisibility(View.GONE);
                        tl_add_attach.setVisibility(View.GONE);
                    }
                }

                else if(lead.getListCustomercomplaintQuestions().get(i).getType().equals("RU"))
                {
                    tv_response.setVisibility(View.GONE);
                    ti_response.setVisibility(View.GONE);
                    ll_checkbox.setVisibility(View.GONE);
                    sp_response.setVisibility(View.GONE);
                    //surfaceView.setVisibility(View.GONE);
                    tl_add_attach.setVisibility(View.GONE);
                    for(int j=0;j<lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().size();j++)
                    {
                        lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse();
                        Log.e("REOS", "ListCustomerComplaintAdapter-getView-lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse()"
                                +lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse());
                        RadioButton radioButton1 = new RadioButton(context);
                        radioButton1.setText(lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse());
                        rg_response.addView(radioButton1);
                    }

                }
                else if(lead.getListCustomercomplaintQuestions().get(i).getType().equals("RM"))
                {
                    tv_response.setVisibility(View.GONE);
                    ti_response.setVisibility(View.GONE);
                    rg_response.setVisibility(View.GONE);
                    //tl_add_adjunt.setVisibility(View.GONE);
                    sp_response.setVisibility(View.GONE);
                    //surfaceView.setVisibility(View.GONE);
                    tl_add_attach.setVisibility(View.GONE);
                    List<CheckBox> checkBoxList = new ArrayList<>();
                    for(int j=0;j<lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().size();j++)
                    {
                        Log.e("REOS", "ListCustomerComplaintAdapter-getView-lead.getListCustomercomplaintQuestions().get(i).getQuestionsEdit()"
                                + lead.getListCustomercomplaintQuestions().get(i).getQuestionsEdit());
                        lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse();
                        Log.e("REOS", "ListCustomerComplaintAdapter-getView-lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse()"
                                + lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse());
                        checkBox = new CheckBox(context);
                        checkBox.setText(lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse());
                        checkBox.setBackgroundColor(Color.WHITE);
                        checkBox.setClickable(true);
                        checkBox.setEnabled(true);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.gravity = Gravity.LEFT;
                        checkBox.setLayoutParams(params);
                        checkBoxList.add(checkBox);
                        ll_checkbox.addView(checkBox);
                    }



                    //x=i;
                    q=i;
                    Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.checkBox1.valor de x"+x);
                    Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.lead.getListCustomercomplaintQuestions().get(i).getQuestionNumber()"+lead.getListCustomercomplaintQuestions().get(i).getQuestionNumber());
                    for (CheckBox checkBox1 : checkBoxList) {
                        checkBox1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               String response="";
                                //Log.e("REOS", "ListCustomerComplaintAdapter-getNumberQuestion.i"+i);
                                Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.ingresoListaCheckbox");
                                Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.Checkbox.getText"+checkBox1.getText());
                                Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.valor y: "+y);
                                int resultado=0;
                                for(int l=0;l<checkBoxList.size();l++)
                                {
                                    if(checkBoxList.get(l).isChecked())
                                    {
                                        //tv_questions.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
                                        resultado++;
                                        //lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse().get(l).setResponseChoisse("Y");
                                    }
                                    else {
                                        //lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse().get(l).setResponseChoisse("N");
                                    }

                                    Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.valor l: "+l);
                                }
                                // Manejar el evento de click del CheckBox aquí
                                if (checkBox1.isChecked()) {
                                    Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.checkBox1.isChecked()"+checkBox1.isChecked());
                                    response=checkBox1.getText().toString();
                                    // CheckBox está marcado
                                } else {
                                    Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.checkBox1.isChecked()"+checkBox1.isChecked());
                                    // CheckBox no está marcado
                                }
                                y=getNumberQuestion(objects,response);
                                Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.checkBox1.resultado"+resultado);
                                Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.checkBox1.valor x"+x);

                                if(resultado>0)
                                {
                                    lead.getListCustomercomplaintQuestions().get(y).setQuestionAnswered("Y");
                                    updateStepView(holder,lead);
                                }else {
                                    lead.getListCustomercomplaintQuestions().get(y).setQuestionAnswered("N");
                                    updateStepView(holder,lead);
                                }

                                if(lead.getListCustomercomplaintQuestions().get(y).getQuestionAnswered().equals("Y"))
                                {
                                    tv_questions.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
                                }else {
                                    tv_questions.setBackgroundColor(activity.getResources().getColor(R.color.gray));
                                }

                                if (checkBox1.isChecked()) {
                                    Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.checkBox1.isChecked()"+checkBox1.isChecked());
                                    for(int j=0;j<lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse().size()
                                            ;j++)
                                    {
                                        if(lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse()
                                                .get(j).getResponse().equals(response)
                                        ){
                                            lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse()
                                                    .get(j).setResponseChoisse("Y");
                                        }
                                    }
                                    // CheckBox está marcado
                                } else {
                                    for(int j=0;j<lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse().size()
                                            ;j++)
                                    {
                                        if(lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse()
                                                .get(j).getResponse().equals(response)
                                        ){
                                            lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse()
                                                    .get(j).setResponseChoisse("N");
                                        }
                                    }
                                    Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.checkBox1.isChecked()"+checkBox1.isChecked());
                                    // CheckBox no está marcado
                                }

                                Log.e("REOS", "ListCustomerComplaintAdapter-checkBoxList.checkBox1.lead.getListCustomercomplaintQuestions().get(y).getQuestionAnswered()"+lead.getListCustomercomplaintQuestions().get(y).getQuestionAnswered());
                            }
                        });
                    }

                }
                else if(lead.getListCustomercomplaintQuestions().get(i).getType().equals("RL"))
                {
                    rg_response.setVisibility(View.GONE);
                    ti_response.setVisibility(View.GONE);
                    ll_checkbox.setVisibility(View.GONE);
                    //tl_add_adjunt.setVisibility(View.GONE);
                    tv_response.setVisibility(View.GONE);
                    //surfaceView.setVisibility(View.GONE);
                    tl_add_attach.setVisibility(View.GONE);
                    ArrayList<String> ListResponse= new  ArrayList<String>();
                    ListResponse.add("--SELECCIONAR--");
                    for(int j=0;j<lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().size();j++)
                    {
                        lead.getListCustomercomplaintQuestions().get(i).setQuestionAnswered("Y");
                        ListResponse.add(lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse()) ;
                        tv_questions.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
                    }
                    ArrayAdapter<String> adapterResponse = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, ListResponse);
                    sp_response.setAdapter(adapterResponse);
                    adapterResponse.notifyDataSetChanged();


                    sp_response.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                {
                                    responsespinner = parent.getItemAtPosition(position).toString();
                                    y=getNumberQuestion(objects,responsespinner);
                                    Log.e("REOS", "ListCustomerComplaintAdapter-lead.getType().equals(RL) responsespinner: " + responsespinner);
                                    Log.e("REOS", "ListCustomerComplaintAdapter-lead.getType().equals(RL) responsespinner-y: " + y);
                                    for(int j=0;j<lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse().size();j++)
                                    {
                                        if(lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse()
                                                .get(j).getResponse().equals(responsespinner)
                                        )
                                        {
                                            lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse()
                                                    .get(j).setResponseChoisse("Y");
                                        }
                                        else {
                                            lead.getListCustomercomplaintQuestions().get(y).getListCustomerComplaintResponse()
                                                    .get(j).setResponseChoisse("N");
                                        }
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            }
                    );
                }
                else if(lead.getListCustomercomplaintQuestions().get(i).getType().equals("RA"))
                {
                    try {
                        Log.e("REOS", "ListCustomerComplaintAdapter-lead.getType().equals(RA) x: " + x);
                        rg_response.setVisibility(View.GONE);
                        ti_response.setVisibility(View.GONE);
                        ll_checkbox.setVisibility(View.GONE);
                        tv_response.setVisibility(View.GONE);
                        sp_response.setVisibility(View.GONE);

                        //y=Integer.parseInt(lead.getListCustomercomplaintQuestions().get(i).getQuestionNumber());
                        String response = "";
                        for (int j = 0; j < lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().size(); j++) {
                            response = lead.getListCustomercomplaintQuestions().get(i).getListCustomerComplaintResponse().get(j).getResponse();
                            lead.getListCustomercomplaintQuestions().get(i).setQuestionAnswered("Y");
                        }

                        y=getNumberQuestion(objects,response);
                        Log.e("REOS", "ListCustomerComplaintAdapter-getType().equals(RA)-response: " + response.toString());
                        Log.e("REOS", "ListCustomerComplaintAdapter-getType().equals(RA)-response-y: " + y);
                        //y = i;
                        x = Integer.parseInt(lead.getSection_id());
                        Log.e("REOS", "ListCustomerComplaintAdapter-getType().equals(RA)-response-x: " + x);
                        Log.e("REOS", "ListCustomerComplaintAdapter-lead.getListCustomercomplaintQuestions().get(i).getType().equals(RA) x: " + x);
                        if (ll_attach.getChildCount() == 0) {


                        }
                        cv_add_attach.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //{
                                lead.getListCustomercomplaintQuestions().get(y).setQuestionAnswered("Y");
                                LayoutInflater layoutInflater2 = LayoutInflater.from(getContext());
                                int id2 = R.layout.layout_list_customer_complaint_response_attach;
                                RelativeLayout relativeLayout2 = (RelativeLayout) layoutInflater2.inflate(id2, null, false);
                                CardView cv_add_photo = (CardView) relativeLayout2.findViewById(R.id.cv_add_photo);
                                CardView cv_add_video = (CardView) relativeLayout2.findViewById(R.id.cv_add_video);
                                CardView cv_add_file = (CardView) relativeLayout2.findViewById(R.id.cv_add_file);
                                ll_attach.addView(relativeLayout2);

                                cv_add_photo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //alertmenuaddphoto(activity,holder,lead,x).show();
                                        Log.e("REOS", "ListCustomerComplaintAdapter-cv_add_photo y: " + y);
                                        lead.getListCustomercomplaintQuestions().get(y).setQuestionAnswered("Y");
                                        cv_add_video.setVisibility(View.GONE);
                                        cv_add_file.setVisibility(View.GONE);
                                        alertmenuaddphoto(activity, holder, lead, y).show();
                                    }
                                });
                                cv_add_video.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.e("REOS", "ListCustomerComplaintAdapter-cv_add_video y: " + y);
                                        lead.getListCustomercomplaintQuestions().get(y).setQuestionAnswered("Y");
                                        cv_add_photo.setVisibility(View.GONE);
                                        cv_add_file.setVisibility(View.GONE);

                                        if (started) {
                                            makevideo(holder, lead, x);
                                        } else {
                                            alertmenuaddvideo(activity, holder, lead, y).show();
                                        }

                                        //alertmenuaddphoto(activity,holder,lead,y).show();
                                    }
                                });

                                cv_add_file.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.e("REOS", "ListCustomerComplaintAdapter-cv_add_file y: " + y);
                                        lead.getListCustomercomplaintQuestions().get(y).setQuestionAnswered("Y");
                                        cv_add_photo.setVisibility(View.GONE);
                                        cv_add_video.setVisibility(View.GONE);
                                        getAttachFile();
                                    }
                                });
                            }
                        });
                    }catch (Exception e)
                    {
                        Log.e("REOS", "ListCustomerComplaintAdapter-lead.getType().equals(RA) error: " + e.toString());
                    }
                }

                content_questions.addView(relativeLayout1);
                //}
            }

            Log.e("REOS", "ListCustomerComplaintAdapter-getView-lead.getListCustomercomplaintQuestions().get(i).getQuestion()"+lead.getListCustomercomplaintQuestions().get(i).getQuestion());
        }
        Resources res2 = getContext().getResources(); // need this to fetch the drawable
        Drawable draw = res2.getDrawable( R.drawable.ic_baseline_expand_less_24_white);
        holder.imv_show_more.setImageDrawable(draw);
    }

    public static class ViewHolder {
        TextView tv_section;
        LinearLayout content;
        ImageView imv_show_more;
        //StateProgressBar StateProgressbar;
        HorizontalStepView horizontalStepView;
    }

    private File createImageFile(String name,Activity activity) throws IOException {

        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = name;
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        CustomerComplaintView.mCurrentPhotoPathG=image.getAbsolutePath();
        /*if(type.equals("G"))
        {
            mCurrentPhotoPathG=image.getAbsolutePath();
        }
        else if(type.equals("L"))
        {
            mCurrentPhotoPathL = image.getAbsolutePath();
        }*/
        return image;
    }

    public AlertDialog alertmenuaddphoto (Activity activity,ListCustomerComplaintAdapter.ViewHolder holder, CustomerComplaintSectionEntity lead,int x) {

        String[] colors = {activity.getResources().getString(R.string.camera)
                , activity.getResources().getString(R.string.galery)};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.choise).toUpperCase());
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        makephoto(holder,lead,x);
                        break;
                    case 1:
                        Toast.makeText(getContext(),
                                activity.getResources().getString(R.string.open_galeries)
                                        +activity.getResources().getString(R.string.point_suspenses)
                                , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        //intent.setType("image/*");
                        //intent.setType("image/*, video/*");
                        String resultado="";
                        //activity.startActivityForResult(intent.createChooser(intent, activity.getResources().getString(R.string.selection_image)), 156);
                        CustomerComplaintView.someActivityResultLauncherPhotoAttach.launch(intent);
                        break;
                    default:
                        //estado = 0;
                        break;
                }
            }
        });
        return builder.create();
    }

    public void makephoto(ListCustomerComplaintAdapter.ViewHolder holder, CustomerComplaintSectionEntity lead,int x)
    {
        Log.e("REOS","statusDispatchRepository-->FotoGuia-->Inicia");
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int permsRequestCode = 255;

        String[] perms = {
                Manifest.permission.CAMERA
        };
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            activity.requestPermissions(perms, permsRequestCode);
        }else{
            try {
                //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType("image/*, video/*");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Crea el File
                File photoFile = null;
                //startActivityForResult(intent,0);
                foto_id=formulasController.ObtenerFechaHoraCadena();
                photoFile = createImageFile(foto_id,activity);

                if (photoFile != null) {
                    //Uri photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                    Uri photoURI=null;
                    switch (BuildConfig.FLAVOR){
                        case "ecuador":
                            photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.ecuador" , photoFile);
                            break;
                        case "chile":
                        case "peru":
                        case "marruecos":
                            photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                            break;
                        case "espania":
                            photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.espania" , photoFile);
                            break;
                        case "perurofalab":
                            photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.perurofalab" , photoFile);
                            break;
                        case "bolivia":
                            photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.bolivia" , photoFile);
                            break;
                        case "paraguay":
                            photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.paraguay" , photoFile);
                            break;
                    }
                    //intent.setType("image/*");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    //startActivityForResult(intent,20);
                    CustomerComplaintView.someActivityResultLauncherPhotomake.launch(intent);
                    lead.getListCustomercomplaintQuestions().get(x).setQuestionAnswered("Y");
                    updateStepView(holder,lead);
                    //Log.e("REOS","ListCustomerComplaintAdapter-makephoto-photoFile: "+photoFile.toString());
                    //for(int i=0;i< lead.getListCustomercomplaintQuestions().get(x).getListCustomerComplaintResponse().)
                    tv_questions.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
                            /*if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                someActivityResultLauncherGuia.launch(intent);
                            }*/
                    AddMedia(this.objects,x,y,photoFile.toString(),"F");

                }
            } catch (IOException ex) {
                Log.e("REOS,","StatusDispatchDialog-onCreateDialog-imageViewPhoto-error:"+ex);
            }
        }
    }

    public void AddMedia(
            //CustomerComplaintSectionEntity lead
            List<CustomerComplaintSectionEntity> listobject
            ,int x,int y,String urlFile,String AttachType
            //,String typeResponse
    )
    {
        Log.e("REOS","ListCustomerComplaintAdapter-AddMedia-x: "+x);
        Log.e("REOS","ListCustomerComplaintAdapter-AddMedia-urlFile: "+urlFile.toString());
        Log.e("REOS","ListCustomerComplaintAdapter-AddMedia-AttachType: "+AttachType.toString());
        Integer response;
        List<CustomerComplaintResponseEntity> listCustomerComplaintResponseEntity=new ArrayList<>();
        CustomerComplaintResponseEntity customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
        for(int i=0;i<listobject.size();i++)
        {
            if(listobject.get(i).getSection_id().equals(String.valueOf(x))){
                for(int j=0;j<listobject.get(i).getListCustomercomplaintQuestions().size();j++)
                {
                    if(listobject.get(i).getListCustomercomplaintQuestions()
                            .get(j).getQuestionNumber().equals(String.valueOf(y)))
                    {
                        if(
                                //lead.getListCustomercomplaintQuestions().get(x).getListCustomerComplaintResponse()
                                 listobject.get(i).getListCustomercomplaintQuestions().get(j).getListCustomerComplaintResponse()
                                !=null)
                        {
                            response=listobject.get(i).getListCustomercomplaintQuestions().get(j).getListCustomerComplaintResponse().size()+1;
                            Log.e("REOS","ListCustomerComplaintAdapter-AddMedia-response: "+response);
                            listCustomerComplaintResponseEntity=listobject.get(i).getListCustomercomplaintQuestions().get(j).getListCustomerComplaintResponse();
                        }
                        else {
                            response=1;
                        }


                        customerComplaintResponseEntity.response_id=response.toString();
                        customerComplaintResponseEntity.reponseRouteFile=urlFile.toString();
                        customerComplaintResponseEntity.reponseAttachType=AttachType;

                        listCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
                        listobject.get(i).getListCustomercomplaintQuestions().get(j).setListCustomerComplaintResponse(listCustomerComplaintResponseEntity);
                    }
                }
            }
        }

    }

    public AlertDialog alertmenuaddvideo (
            Activity activity,
            ListCustomerComplaintAdapter.ViewHolder holder,
            CustomerComplaintSectionEntity lead,
            int x
    ) {

        String[] colors = {activity.getResources().getString(R.string.video)
                , activity.getResources().getString(R.string.galery)};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.choise).toUpperCase());
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        //makephoto(holder,lead,x);

                        makevideo(holder,lead,x);
                        break;
                    case 1:
                        Toast.makeText(getContext(),
                                activity.getResources().getString(R.string.open_galeries)
                                        +activity.getResources().getString(R.string.point_suspenses)
                                , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                //MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        );
                        intent.setType("video/*");
                        //activity.startActivityForResult(intent.createChooser(intent, activity.getResources().getString(R.string.selection_video)), 155);
                        CustomerComplaintView.someActivityResultLauncherVideoAttach.launch(intent);
                        break;
                    default:
                        //estado = 0;
                        break;
                }
            }
        });
        return builder.create();
    }

    public void makevideo(
            ListCustomerComplaintAdapter.ViewHolder holder,
            CustomerComplaintSectionEntity lead,
            int x)
    {
        try {
            int permsRequestCode = 255;
            String filePath="";
            String[] perms = {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            };
            if (
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            ||ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                activity.requestPermissions(perms, permsRequestCode);
            } else {

                Log.e("REOS", "ListCustomerComplaintAdapter-cv_add_video.started: " + started);
                if (started) {
                    started = recorder.stopRecording();
                    tv_start_video.setText(getContext().getResources().getString(R.string.start_video));
                    AddMedia(this.objects,x,y,filePath,"V" );

                } else {
                    filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RECIBOS" + "/video.mp4";
                    SurfaceHolder surfaceHolder = surfaceView.getHolder();

                    started = recorder.startRecording(surfaceHolder, filePath,activity);
                    Log.e("REOS", "ListCustomerComplaintAdapter-cv_add_video.setOnClickListener");
                    if (started) {
                        //holder.btnRecord.setText("Stop Recording");
                        tv_start_video.setText(getContext().getResources().getString(R.string.stop_video));
                    } else {
                        Toast.makeText(context, "Failed to start recording", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        }catch (Exception e){
            Log.e("REOS", "ListCustomerComplaintAdapter-cv_add_video.error: "+e.toString());
        }
    }

    public void getAttachFile()
    {
        Toast.makeText(getContext(),
                activity.getResources().getString(R.string.open_galeries)
                        +activity.getResources().getString(R.string.point_suspenses)
                , Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        /*Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Files.getContentUri("external"));
        //intent.setType("application/pdf");
        String resultado="";*/
        //activity.startActivityForResult(intent.createChooser(intent, activity.getResources().getString(R.string.selection_image)), 156);
        //CustomerComplaintView.someActivityResultLauncherFileAttach.launch(intent);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            CustomerComplaintView.someActivityResultLauncherFileAttach.launch(intent);
        } catch (ActivityNotFoundException e) {
            Log.e("REOS", "ListCustomerComplaintAdapter-getAttachFile.error: "+e.toString());
            // Mostrar un mensaje de error indicando que no hay una aplicación instalada para manejar la selección de archivos de tipo PDF
        }
    }

    public void getCustomerSection()
    {
        try {
            FormulasController formulasController = new FormulasController(getContext());
            formulasController.AddForms(objects);
        }catch (Exception e)
        {
            Log.e("REOS", "ListCustomerComplaintAdapter-getCustomerSection.error: "+e.toString());
        }

    }

    public int getNumberQuestion(List<CustomerComplaintSectionEntity> objects,String response )
    {
        int resultado=0;
        for(int i=0;i<objects.size();i++)
        {
            for(int j=0;j<objects.get(i).getListCustomercomplaintQuestions().size();j++)
            {
                if(objects.get(i).getListCustomercomplaintQuestions().get(j)
                        .getListCustomerComplaintResponse()!=null)
                {
                    for(int k=0;k<objects.get(i).getListCustomercomplaintQuestions().get(j)
                            .getListCustomerComplaintResponse().size();k++)
                    {
                        if(objects.get(i).getListCustomercomplaintQuestions().get(j).getListCustomerComplaintResponse()
                                .get(k).getResponse()
                                .equals(response)
                        ){
                            resultado=Integer.parseInt(objects.get(i).getListCustomercomplaintQuestions().get(j).getQuestionNumber());
                        }
                    }
                }

            }
        }
        Log.e("REOS", "ListCustomerComplaintAdapter-getNumberQuestion.resultado: "+resultado);
        return resultado;
    }

    public int setStatusQuetions(List<CustomerComplaintSectionEntity> objects,int x,int y,String response,String responseChoisse )
    {
        int resultado=0;
        for(int i=0;i<objects.size();i++)
        {
            if(i==x)
            {
                for(int j=0;j<objects.get(i).getListCustomercomplaintQuestions().size();j++)
                {
                    if(j==y)
                    {
                        for(int k=0
                            ;k<objects.get(i).getListCustomercomplaintQuestions().get(j).getListCustomerComplaintResponse().size()
                                ;k++)
                        {
                            if(objects.get(i).getListCustomercomplaintQuestions().get(j).getListCustomerComplaintResponse()
                                    .get(k).getResponse().equals(response)
                            ){
                                objects.get(i).getListCustomercomplaintQuestions().get(j).getListCustomerComplaintResponse()
                                        .get(k).setResponseChoisse(responseChoisse);

                            }
                        }

                    }
                }
            }
        }
        Log.e("REOS", "ListCustomerComplaintAdapter-getNumberQuestion.resultado: "+resultado);
        return resultado;
    }
}
