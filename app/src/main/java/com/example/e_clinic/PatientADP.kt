package com.example.e_clinic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PatientADP (ctx: Context, _patName: List<String>, _patMsg: List<String>, _patId: List<String>, _pid: String): RecyclerView.Adapter<PatientADP.PatViewHolder>() {
        var context=ctx
        var patName=_patName
        var patMsg=_patMsg
        var patId=_patId
        var msg=" "
        var p_id=_pid
    class PatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var nameView: TextView =itemView.findViewById(R.id.p_name)
        var msgView: TextView =itemView.findViewById(R.id.patMsg)
        var idView: TextView =itemView.findViewById(R.id.p_id)
        var D_msg:EditText=itemView.findViewById(R.id.d_msg)
        var bt:Button=itemView.findViewById(R.id.postsolution)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientADP.PatViewHolder {
        var itemView= LayoutInflater.from(context).inflate(R.layout.patientadapter, parent, false)

        return PatientADP.PatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PatientADP.PatViewHolder, position: Int) {
        holder.nameView.text = patName[position]
        holder.msgView.text = patMsg[position].toString()
        holder.idView.text = patId[position]
        holder.bt.setOnClickListener {
            msg=holder.D_msg.text.toString()
            val database = Firebase.database
            val myRef = database.getReference("Patient Data")
            myRef.child(patId[position]).child(""+p_id.toString()).setValue(msg.toString())
            val database1 = Firebase.database
            val myRef1 = database1.getReference("Doctor Data")
            myRef1.child(p_id.toString()).child("msg").setValue(msg.toString())
        }
    }

    override fun getItemCount(): Int {
        return patName.size
    }

}