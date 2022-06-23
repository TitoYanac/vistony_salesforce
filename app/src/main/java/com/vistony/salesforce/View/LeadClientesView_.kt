package com.vistony.salesforce.View

import android.Manifest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vistony.salesforce.Dao.Retrofit.LeadClienteViewModel
import androidx.activity.result.ActivityResultLauncher
import android.content.Intent
import android.graphics.Bitmap
import com.google.android.gms.maps.MapView
import com.omega_r.libs.OmegaCenterIconButton
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.textfield.TextInputLayout
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vistony.salesforce.R
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.google.android.gms.maps.MapsInitializer
import com.vistony.salesforce.Entity.SesionEntity
import androidx.core.content.ContextCompat
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import java.io.ByteArrayOutputStream
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

class LeadClientesView_ : Fragment() {
    private var v: View? = null
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var floatingButtonTakePhoto: FloatingActionButton? = null
    private var leadClienteViewModel: LeadClienteViewModel? = null
    private var someActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    private var imgBitmap: Bitmap? = null
    private var byteArray: ByteArray
    private var mapView: MapView? = null
    private var btnUpload: OmegaCenterIconButton? = null
    private var latitud: Double? = null
    private var longitud: Double? = null
    private var spinner: Spinner? = null
    private var editTextComentary: EditText? = null
    private var editTextCardCode: EditText? = null
    private var editTextCardName: EditText? = null
    private var editTextCardNameComercial: EditText? = null
    private var editTextPhone: EditText? = null
    private var editTextCellPhone: EditText? = null
    private var editTextContactPerson: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextWeb: EditText? = null
    private var editTextCoordenates: EditText? = null
    private var buttonSendGPS: Button? = null
    private var direccion = "Sin dirección"
    private var referencia = "Sin referencias"
    val message = "Este campo no puede ir vacio"
    private var status = true
    private var myGoogleMap: GoogleMap? = null
    private var dialog: Dialog? = null
    var ti_commercial_name: TextInputLayout? = null
    var ti_card_name: TextInputLayout? = null
    var ti_textphone: TextInputLayout? = null
    var ti_TextContactPerson: TextInputLayout? = null

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(tag: String?, dato: Any?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity!!.title = resources.getString(R.string.lead_cliente)
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        dialog = Dialog(activity!!)
        v = inflater.inflate(R.layout.fragment_lead_cliente_view, container, false)
        floatingButtonTakePhoto = v.findViewById(R.id.floatingButtonTakePhoto)
        imageViewPhoto = v.findViewById(R.id.imageViewPhoto)
        editTextCardCode = v.findViewById(R.id.editTextCardCode)
        editTextCardName = v.findViewById(R.id.editTextCardName)
        editTextCardNameComercial = v.findViewById(R.id.editTextCardNameComercial)
        editTextPhone = v.findViewById(R.id.editTextPhone)
        editTextCellPhone = v.findViewById(R.id.editTextCellPhone)
        editTextContactPerson = v.findViewById(R.id.editTextContactPerson)
        editTextEmail = v.findViewById(R.id.editTextEmail)
        editTextWeb = v.findViewById(R.id.editTextWeb)
        editTextCoordenates = v.findViewById(R.id.editTextCoordenates)
        editTextComentary = v.findViewById(R.id.editTextComent)
        spinner = v.findViewById(R.id.spinner)
        buttonSendGPS = v.findViewById(R.id.buttonSendGPS)
        btnUpload = v.findViewById(R.id.btnSubtmit)
        imageViewPhoto.setBackgroundResource(R.drawable.portail)
        leadClienteViewModel = ViewModelProvider(this).get(
            LeadClienteViewModel::class.java
        )
        ti_commercial_name = v.findViewById(R.id.ti_commercial_name)
        ti_textphone = v.findViewById(R.id.ti_textphone)
        ti_TextContactPerson = v.findViewById(R.id.ti_TextContactPerson)
        ti_commercial_name.setVisibility(View.GONE)
        ti_textphone.setVisibility(View.GONE)
        ti_TextContactPerson.setVisibility(View.GONE)
        editTextCardName.setEnabled(false)
        editTextCardCode.setEnabled(false)
        floatingButtonTakePhoto.setOnClickListener(View.OnClickListener { data: View? ->
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(activity!!.packageManager) != null) {
                someActivityResultLauncher!!.launch(intent)
            }
        })
        someActivityResultLauncher =
            registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val extras = result.data!!.extras
                    imgBitmap = extras!!["data"] as Bitmap?
                    imageViewPhoto.setImageBitmap(imgBitmap)
                }
            }
        btnUpload.setOnClickListener(View.OnClickListener { e: View? -> sendLeadApi() })
        buttonSendGPS.setOnClickListener(View.OnClickListener { e: View? ->
            displayDialogMap()
            MapsInitializer.initialize(activity)
            mapView!!.onCreate(dialog!!.onSaveInstanceState())
            mapView!!.onResume()
        })
        return v
    }

    private fun sendLeadApi() {
        var acount = 0
        if (editTextCardName!!.text.length == 0) {
            editTextCardName!!.error = message
            acount = acount + 1
        } /*else if (editTextCardCode.getText().length() == 0) {
            editTextCardCode.setError(message);
            acount = acount + 1;
        } else if (editTextCardNameComercial.getText().length() == 0) {
            editTextCardNameComercial.setError(message);
            acount = acount + 1;
        } else if (editTextCellPhone.getText().length() == 0) {
            editTextCellPhone.setError(message);
            acount = acount + 1;
        } else if (editTextContactPerson.getText().length() == 0) {
            editTextContactPerson.setError(message);
            acount = acount + 1;
        }*/ else if (editTextCoordenates!!.text.length == 0) {
            editTextCoordenates!!.error = message
            acount = acount + 1
        }
        var encoded: String? = null
        if (imgBitmap != null) {
            val stream = ByteArrayOutputStream()
            imgBitmap!!.compress(Bitmap.CompressFormat.PNG, 80, stream)
            byteArray = stream.toByteArray()
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
        if (acount == 0) {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formattedDate = df.format(c.time)
            val parametros = HashMap<String, String?>()
            parametros["DocumentsOwner"] = SesionEntity.usuario_id
            parametros["SalesPersonCode"] = SesionEntity.fuerzatrabajo_id
            parametros["Comentary"] = editTextComentary!!.text.toString()
            parametros["CardName"] = editTextCardName!!.text.toString()
            parametros["TaxNumber"] = editTextCardCode!!.text.toString()
            parametros["TradeName"] = editTextCardNameComercial!!.text.toString()
            parametros["NumberPhono"] = editTextPhone!!.text.toString()
            parametros["NumberCellPhone"] = editTextCellPhone!!.text.toString()
            parametros["ContactPerson"] = editTextContactPerson!!.text.toString()
            parametros["Email"] = editTextEmail!!.text.toString()
            parametros["Web"] = editTextWeb!!.text.toString()
            parametros["Address"] = direccion
            parametros["Reference"] = referencia
            parametros["Category"] = spinner!!.selectedItem.toString()
            parametros["Latitude"] = "" + latitud
            parametros["Longitude"] = "" + longitud
            parametros["DateTime"] = "" + formattedDate
            parametros["photo"] = encoded
            leadClienteViewModel!!.sendLead(parametros, context)
                .observe(this.activity!!) { data: String ->
                    if (data == "init") {
                        btnUpload!!.isEnabled = false
                        btnUpload!!.isClickable = false
                        btnUpload!!.text = "Enviado Lead..."
                        btnUpload!!.background = ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.custom_border_button_onclick
                        )
                    } else if (data == "successful") {
                        btnUpload!!.isEnabled = true
                        btnUpload!!.isClickable = true
                        btnUpload!!.text = "Enviar Lead"
                        btnUpload!!.background = ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.custom_border_button_red
                        )
                        Toast.makeText(activity, "Lead enviado", Toast.LENGTH_SHORT).show()
                        clearEditText()
                    } else {
                        btnUpload!!.isEnabled = true
                        btnUpload!!.isClickable = true
                        btnUpload!!.text = "Enviar Lead"
                        btnUpload!!.background = ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.custom_border_button_red
                        )
                        Toast.makeText(
                            activity,
                            "Ocurrio un error al enviar el lead...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        leadClienteViewModel!!.sendLeadNotSend(context)
            .observe(this.activity!!) { data: String -> Log.e("JEPICAMEE", "=>$data") }
    }

    private fun clearEditText() {
        editTextCardCode!!.setText("")
        editTextCardName!!.setText("")
        editTextCardNameComercial!!.setText("")
        editTextPhone!!.setText("")
        editTextCellPhone!!.setText("")
        editTextContactPerson!!.setText("")
        editTextEmail!!.setText("")
        editTextWeb!!.setText("")
        editTextCoordenates!!.setText("")
        editTextComentary!!.setText("")
    }

    private fun displayDialogMap() {
        dialog!!.setContentView(R.layout.layout_mapa_dialog)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val editTextAddress = dialog!!.findViewById<EditText>(R.id.editTextAddressDialog)
        val editTextAddressReference =
            dialog!!.findViewById<EditText>(R.id.editTextAddressReferenceDialog)
        editTextAddress.setText(if (direccion == "Sin dirección") "" else direccion)
        editTextAddressReference.setText(if (referencia == "Sin referencias") "" else referencia)
        val image = dialog!!.findViewById<ImageView>(R.id.image)
        image.setImageResource(R.mipmap.logo_circulo)
        image.background = ColorDrawable(Color.TRANSPARENT)
        val dialogButton = dialog!!.findViewById<Button>(R.id.dialogButtonOK)
        val dialogButtonExit = dialog!!.findViewById<Button>(R.id.dialogButtonCancel)
        mapView = dialog!!.findViewById(R.id.mapView)
        dialogButton.text = "Add"
        dialogButtonExit.text = "Cancel"

        ////////////////////////////////////////////////////////////////////////////////////////////
        if (mapView != null) {
            val locationManager =
                activity!!.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mapView!!.getMapAsync { googleMap ->
                    myGoogleMap = googleMap
                    if (ContextCompat.checkSelfPermission(
                            activity!!.applicationContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(
                            activity!!.applicationContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        myGoogleMap!!.isMyLocationEnabled = true
                        myGoogleMap!!.uiSettings.isMyLocationButtonEnabled = true
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f,
                            object : LocationListener {
                                override fun onLocationChanged(location: Location) {
                                    Toast.makeText(
                                        activity,
                                        "onLocationChanged",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    latitud = location.latitude
                                    longitud = location.longitude
                                    Log.e(
                                        "REOS",
                                        "LeadClientesView.displayDialogMap.latitud:$latitud"
                                    )
                                    Log.e(
                                        "REOS",
                                        "LeadClientesView.displayDialogMap.longitud:$longitud"
                                    )
                                    val latLng = LatLng(
                                        latitud!!, longitud!!
                                    )
                                    myGoogleMap!!.clear()
                                    myGoogleMap!!.addMarker(
                                        MarkerOptions().position(latLng).title("Potential client")
                                            .draggable(true)
                                    )
                                    editTextCoordenates!!.setText(latitud.toString() + "," + longitud)
                                    if (status) {
                                        myGoogleMap!!.moveCamera(
                                            CameraUpdateFactory.newLatLngZoom(
                                                latLng,
                                                10f
                                            )
                                        )
                                        status = false
                                    } else {
                                        myGoogleMap!!.moveCamera(
                                            CameraUpdateFactory.newLatLng(
                                                latLng
                                            )
                                        )
                                    }
                                }

                                override fun onStatusChanged(
                                    provider: String,
                                    status: Int,
                                    extras: Bundle
                                ) {
                                    Toast.makeText(activity, "onStatusChanged", Toast.LENGTH_SHORT)
                                        .show()
                                }

                                override fun onProviderEnabled(provider: String) {
                                    Toast.makeText(
                                        activity,
                                        "onProviderEnabled",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onProviderDisabled(provider: String) {
                                    Toast.makeText(
                                        activity,
                                        "onProviderDisabled",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                        dialog!!.show()
                    } else {
                        Toast.makeText(
                            activity!!.applicationContext,
                            "R.string.error_permission_map",
                            Toast.LENGTH_LONG
                        ).show()
                        activity!!.requestPermissions(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ), 100
                        )
                    }

                    ////////////////////////////////////////////////////////////////////////////////////


                    /*
                            if(googleMap!=null) {
            
                                googleMap.setOnMyLocationChangeListener(location -> {
                                    if (location != null) {
            
                                        latitud = location.getLatitude();
                                        longitud = location.getLongitude();
            
                                        LatLng latLng = new LatLng(latitud, longitud);
                                        googleMap.clear();
                                        googleMap.addMarker(new MarkerOptions().position(latLng).title("Potential client").draggable(true));
            
                                        editTextCoordenates.setText(latitud + "," + longitud);
            
                                        if (status) {
                                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                                            status = false;
                                        } else {
                                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                        }
                                    }
                                });
            
                                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                                    @Override
                                    public void onMarkerDragStart(Marker location) {
                                        // TODO Auto-generated method stub
                                    }
            
                                    @Override
                                    public void onMarkerDragEnd(Marker location) {
                                        latitud = location.getPosition().latitude;
                                        longitud = location.getPosition().longitude;
                                        editTextCoordenates.setText(latitud + "," + longitud);
            
                                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(location.getPosition()));
                                    }
            
                                    @Override
                                    public void onMarkerDrag(Marker arg0) {
                                        // TODO Auto-generated method stub
                                    }
                                });
            
                                googleMap.setMyLocationEnabled(true);
                                googleMap.getUiSettings().setZoomControlsEnabled(true);
                            }
                            */
                }
            } else {
                Toast.makeText(activity, "R.string.error_permission_map", Toast.LENGTH_LONG).show()
                activity!!.requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 100
                )
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        dialogButtonExit.setOnClickListener { v: View? ->
            // mapView=null;
            dialog!!.dismiss()
        }
        dialogButton.setOnClickListener { v: View? ->
            direccion = editTextAddress.text.toString()
            referencia = editTextAddressReference.text.toString()

//            mapView=null;
            dialog!!.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mapView != null) {
            mapView!!.onResume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mapView != null) {
            mapView!!.onDestroy()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        if (mapView != null) {
            mapView!!.onLowMemory()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //ListenerBackPress.setCurrentFragment("Webview");
        //ListenerBackPress.setTemporaIdentityFragment("onAttach");
        //mapView.onStart();
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        //
        // mapView.onResume();
        //ListenerBackPress.setTemporaIdentityFragment("onDetach");
        mListener = null
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        var imageViewPhoto: ImageView? = null
        private var mListener: OnFragmentInteractionListener? = null
    }
}