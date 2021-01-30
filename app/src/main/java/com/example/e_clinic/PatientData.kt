package com.example.e_clinic

import android.widget.EditText

class PatientData()
{
    var name: String? = null
    var DOB: String? = null
    var age: String? = null
    var gender: String? = null
    var contact: String? = null
    var ID: String? = null
    var pass: String? = null
    var dep:String?=null
    var msg:String?="Not enter yet"
    fun setData(_name:String?,  _DOB:String?, _age:String?, _gender:String?, _contact:String?, _ID:String?, _pass:String?)
    {
        name=_name
        DOB=_DOB
        age=_age
        gender=_gender
        contact=_contact
        ID=_ID
        pass=_pass
    }
}