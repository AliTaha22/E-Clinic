package com.example.e_clinic

class DoctorData()
{
    var name: String? = null
    var qualification: String? = null
    var age: String? = null
    var gender: String? = null
    var contact: String? = null
    var email: String? = null
    var pass: String? = null
    var identification:String? = "Doctor"
    fun setData(_name:String?,  _qualification:String?, _age:String?, _gender:String?, _contact:String?, _email:String?, _pass:String?)
    {
        name=_name
        qualification=_qualification
        age=_age
        gender=_gender
        contact=_contact
        email=_email
        pass=_pass
    }
}