package mhealth.c4c.ucsfrecyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import mhealth.c4c.R
import kotlinx.android.synthetic.main.expandeable_recyclerview.*
import kotlinx.android.synthetic.main.ucsf_recyclerview.*
import mhealth.c4c.faqrecyclerview.UcsfExpandableCardViewAdapter
import mhealth.c4c.Registrationtable.Regdetails
import mhealth.c4c.getphonenumber.getPhoneNumber
import android.hardware.usb.UsbDevice.getDeviceId
import android.content.Context.TELEPHONY_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View.GONE
import android.widget.*
import mhealth.c4c.Login
import mhealth.c4c.Tables.kmpdu


/**
 * Created by root on 3/19/18.
 */
class UcsfMainActivityRecycler : AppCompatActivity() {
    private var chkyes: CheckBox? = null
    private var pnum: EditText? = null
    private var subb: Button? = null
    private var ucsfl: LinearLayout? = null

    private var reglink: TextView?=null;
    private var portallink: TextView?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ucsf_recyclerview)
        setToolbar()
        initialise()
        addListenerOnChkYes()
        addInputListener()
        submitClicked()


        val cardView = ucsfrecycler_cardview
        val itemList = mutableListOf<UcsfExpandableCardViewAdapter.Item>()

        val qn=UcsfExpandableCardViewAdapter.Item(UcsfExpandableCardViewAdapter.PARENT, Html.fromHtml("Introduction").toString())
        qn.children=listOf(UcsfExpandableCardViewAdapter.Item(UcsfExpandableCardViewAdapter.CHILD, Html.fromHtml("The Ministry of Health (MOH), National AIDS and STI Control Programme (NASCOP) revised the HIV Monitoring & Evaluation tools in 2016 with support from Implementing Partners.\n" +
                "\n" +
                "NASCOP led the development and implementation of a five-day course to update and orient health care providers on the revised national HIV M&E tools. \n" +
                "\n" +
                "NASCOP in partnership with the University of California San Francisco (UCSF), with funding from CDC, converted the HIV M&E tools training course into eLearning format to facilitate reach to a wider audience. The online course is hosted at the University of Nairobi UHIV fellowship online learning portal <a href=\\\"eportal.uonbi.ac.ke\\\">link</a>").toString()))
        itemList.add(qn)

        val qn1=UcsfExpandableCardViewAdapter.Item(UcsfExpandableCardViewAdapter.PARENT, Html.fromHtml("Purpose of the course").toString())
        qn1.children=listOf(UcsfExpandableCardViewAdapter.Item(UcsfExpandableCardViewAdapter.CHILD, Html.fromHtml("The purpose of the eLearning M&E tools training course is to provide the front- line Health Care Workers with the updates on the revised HIV M&E tools and to equip them with skills to correctly complete the tools and for appropriate reporting. This course is accredited by regulatory bodies and carries Continuous Professional Development (CPD) points.").toString()))
        itemList.add(qn1)

        val qn2=UcsfExpandableCardViewAdapter.Item(UcsfExpandableCardViewAdapter.PARENT, Html.fromHtml("How to register for National HIV Programme M&E Tools training course ").toString())
        qn2.children=listOf(UcsfExpandableCardViewAdapter.Item(UcsfExpandableCardViewAdapter.CHILD, Html.fromHtml("Fill in the registration form available on this link to enroll for the online National HIV Programmes M&E tools training course.").toString()))
        itemList.add(qn2)



        cardView.layoutManager = LinearLayoutManager(this)
        cardView.adapter = UcsfExpandableCardViewAdapter(itemList)
        setPortalLinks()
        setRegLinks()
    }

    fun setToolbar(){
        try{



            val toolbar = findViewById(R.id.toolbaruserprof) as Toolbar
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "UCSF"
        }
        catch(e:Exception){

        }
    }

    fun initialise(){
        chkyes=findViewById(R.id.chkyescb) as CheckBox
        pnum=findViewById(R.id.phnnum) as EditText
        subb=findViewById(R.id.btnucsfsub) as Button
        ucsfl=findViewById(R.id.ucsflinear) as LinearLayout

    }


    fun setRegLinks() {
        try {

            reglink = findViewById(R.id.registrationlink) as TextView

            val mytext = "course registration page"
            val ss = SpannableString(mytext)
            val cs = object : ClickableSpan() {
                override fun onClick(widget: View) {

                    val url = "https://docs.google.com/forms/d/e/1FAIpQLScd_ZtamUKNrQ4qN4Raf4qT-5vhdjOf4k3gYpCIsEdcnCzw3g/viewform"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)

                }

                override fun updateDrawState(ds: TextPaint) {

                    super.updateDrawState(ds)
                    ds.color = Color.BLUE
                    ds.isUnderlineText = false
                }
            }

            ss.setSpan(cs, 0, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            reglink!!.setText(ss)

            reglink!!.setMovementMethod(LinkMovementMethod.getInstance())
        } catch (e: Exception) {


        }

    }


    fun setPortalLinks() {
        try {

            portallink = findViewById(R.id.portallink) as TextView

            val mytext = "UHIV fellowship online learning portal"
            val ss = SpannableString(mytext)
            val cs = object : ClickableSpan() {
                override fun onClick(widget: View) {

                    val url = "https://eportal.uonbi.ac.ke/"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)

                }

                override fun updateDrawState(ds: TextPaint) {

                    super.updateDrawState(ds)
                    ds.color = Color.BLUE
                    ds.isUnderlineText = false
                }
            }

            ss.setSpan(cs, 0, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            portallink!!.setText(ss)

            portallink!!.setMovementMethod(LinkMovementMethod.getInstance())
        } catch (e: Exception) {


        }

    }
//    fun addInputListener(){
//
//        pnum.addTextChangedListener(new T)
//    }


    fun submitClicked(){

        try{

            subb!!.setOnClickListener(View.OnClickListener {

                var pnums= pnum!!.getText().toString();
                if (pnums.isEmpty()){

                    Toast.makeText(this@UcsfMainActivityRecycler, "Phone number is required ", Toast.LENGTH_SHORT).show()


                }
                else{

                    val myl = Regdetails.findWithQuery(Regdetails::class.java, "select * from Regdetails")
                    println("************************************************************************")
                    for (x in myl.indices) {

                        var fname=myl[x].fname;
                        var lname=myl[x].lname;


                        var mymess="ucsf*"+pnums+"*"+fname+"*"+lname;

                        val smsM = SmsManager.getDefault()
                        smsM.sendTextMessage("40145", null, mymess, null, null)
                        UcsfDialog("You have successfully subscribed to our services");

                    }



                }

            })
        }
        catch(e:Exception){


        }
    }


    fun UcsfDialog(message: String) {

        try {

            val adb = AlertDialog.Builder(this)
            adb.setTitle("SUBSCRIPTION SUCCESS")
            adb.setIcon(R.mipmap.success)
            adb.setMessage(message.toUpperCase())
            adb.setCancelable(false)

            adb.setPositiveButton("OK") { dialog, which ->

                pnum!!.setText("")

            }



            val mydialog = adb.create()
            mydialog.show()
        } catch (e: Exception) {


        }

    }





    fun addInputListener() {

        try {

            pnum!!.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {


//                    Toast.makeText(this@UcsfMainActivityRecycler, "entered "+s.toString(), Toast.LENGTH_SHORT).show()



                }
            })
        } catch (e: Exception) {


        }

    }


    fun addListenerOnChkYes() {


        chkyes!!.setOnClickListener(object : View.OnClickListener {

            @SuppressLint("MissingPermission")
            override fun onClick(v: View) {
                //is chkIos checked?
                if ((v as CheckBox).isChecked) {


                    ucsfl!!.setVisibility(View.VISIBLE)




                }
                else{

                    ucsfl!!.setVisibility(View.GONE)
                }

            }
        })

    }
}
