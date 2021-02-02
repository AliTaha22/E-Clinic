package com.example.e_clinic

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class PatientADP(ctx: Context, _patName: ArrayList<String>, _patId: ArrayList<String>,_patAge: ArrayList<String>,_patGender: ArrayList<String>,_patContatc: ArrayList<String>, _pid: String): RecyclerView.Adapter<PatientADP.PatViewHolder>() {
    var context=ctx
    var patName=_patName
    var patId=_patId
    var p_id=_pid
    var p_Age=_patAge
    var p_Gender=_patGender
    var p_contact=_patContatc
    class PatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var nameView: TextView =itemView.findViewById(R.id.p_name)
        var msgView: TextView =itemView.findViewById(R.id.patMsg)
        var ageView: TextView =itemView.findViewById(R.id.p_age)
        var genderView: TextView =itemView.findViewById(R.id.p_gender)
        var idView: TextView =itemView.findViewById(R.id.p_id)
        var D_msg:EditText=itemView.findViewById(R.id.d_msg)
        var bt:Button=itemView.findViewById(R.id.postsolution)
        var bt2:Button=itemView.findViewById(R.id.appointment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientADP.PatViewHolder {
        var itemView= LayoutInflater.from(context).inflate(R.layout.patientadapter, parent, false)

        return PatientADP.PatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PatientADP.PatViewHolder, position: Int) {
        holder.nameView.text = patName[position]
        holder.idView.text = patId[position]
        holder.ageView.text = p_Age[position]
        holder.genderView.text = p_Gender[position]
        var database = Firebase.database
        var idRef1:String=p_id.toString()
        var idRef2="P_${patId[position].toString()}".toString()

        var db = database.getReference("Doctor Data/$idRef1/$idRef2/")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var temp = snapshot.getValue<String>()
                holder.msgView.text=temp.toString()
                db.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        holder.bt.setOnClickListener {
            var dmsg=holder.D_msg.text.toString()
            var myRef = database.getReference("Patient Data/")
            myRef.child(patId[position]).child("D_"+p_id.toString()).setValue(dmsg.toString())

            Log.d("myt","hello")
        }
        holder.bt2.setOnClickListener {
            var name=patName[position]
            var aptMsg="Dear Patient $name your kindly visit your doctor on Sunday at 12:00 PM thanks"
            var number=p_contact[position]
            Toast.makeText(context, "Message sent to $name and apt MSG is $aptMsg", Toast.LENGTH_SHORT).show()
            var obj:SmsManager= SmsManager.getDefault()
            obj.sendTextMessage(number.toString(), null,aptMsg.toString(),null, null)
        }
    }

    override fun getItemCount(): Int {
        return patName.size
    }

}