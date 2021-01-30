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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class PatientADP(ctx: Context, _patName: List<String>, _patId: List<String>, _pid: String): RecyclerView.Adapter<PatientADP.PatViewHolder>() {
        var context=ctx
        var patName=_patName
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
        holder.idView.text = patId[position]
        val database = Firebase.database

        var idRef1:String=patId[position]
        var idRef2="D_${p_id.toString()}".toString()
        val db = database.getReference("Patient Data/$idRef1/$idRef2")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var temp = snapshot.getValue<String>()
                Toast.makeText(context,"HLo" +temp.toString(), Toast.LENGTH_SHORT).show()
                holder.msgView.text=temp.toString()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        holder.bt.setOnClickListener {
            msg=holder.D_msg.text.toString()
            val myRef = database.getReference("Patient Data")
            myRef.child(patId[position]).child("D_"+p_id.toString()).setValue(msg.toString())
            val myRef1 = database.getReference("Doctor Data")
            myRef1.child(p_id.toString()).child("D_"+patId[position]).setValue(msg.toString())
        }
    }

    override fun getItemCount(): Int {
        return patName.size
    }

}