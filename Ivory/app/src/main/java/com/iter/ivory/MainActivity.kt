package com.iter.ivory

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


// sign in response id and providers used with AuthUI
private const val RC_SIGN_IN = 420
private val providers = mutableListOf(
        AuthUI.IdpConfig.EmailBuilder().build())
class MainActivity : AppCompatActivity(), VaccineFragment.OnListFragmentInteractionListener{
    var bottomnav : BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        // setup bottom navigation bar
        bottomnav = navigation
        // bottomnav!!.visibility = View.GONE
        bottomnav!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // if user is already signed in get vaccines
            bottomnav!!.selectedItemId = R.id.navigation_personal

        }else{
            // handle sign in
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN)

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Successfully signed in - update vaccines
                if (bottomnav != null){
                    bottomnav!!.selectedItemId = R.id.navigation_personal
                }
            } else {
                // TODO: handle sign in failed
            }
        }
        bottomnav!!.selectedItemId = R.id.navigation_personal

    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
        // TODO: decide on which navigation menus to include
            R.id.navigation_personal -> {
                val vaccineFragment: Fragment = VaccineFragment.newInstance(true)
                supportFragmentManager.beginTransaction().replace(R.id.content_view, vaccineFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
//            R.id.navigation_suggested -> {
//                val vaccineFragment: Fragment = VaccineFragment.newInstance(true)
//                supportFragmentManager.beginTransaction().replace(R.id.content_view, vaccineFragment).commit()
//                return@OnNavigationItemSelectedListener true
//            }
        }
        false
    }

    override fun onListFragmentInteraction(vaccine: String, genName: String, date: Date, remDate: Date, position: Int) {
//        val newAdder = Intent(this, VaccineInfo::class.java)
//        startActivityForResult(newAdder, 5)
        val builder = AlertDialog.Builder(this@MainActivity)
        if (remDate == date){
            builder.setMessage("You recieved a vaccine for " + vaccine + " on:\n\t\t" + date.toString())
        }else{
            builder.setMessage("You recieved a vaccine for " + vaccine + " on:\n\t\t" + date.toString() +
                    "\nWe will remind you on:\n\t\t" + remDate)
        }
        builder.setTitle(genName)
        builder.setPositiveButton("CDC Info", DialogInterface.OnClickListener { dialog, id ->
            var link = "no link found"
            when (vaccine) {
                "Adenovirus" -> {link = resources.getString(R.string.adenovirusLink)}
                "Anthrax" -> {link = resources.getString(R.string.anthraxLink)}
                "Diphtheria" -> {link = resources.getString(R.string.diphtheriaLink)}
                "Hepatitis A" -> {link = resources.getString(R.string.hepatitis_ALink)}
                "Hepatitis B" -> {link = resources.getString(R.string.hepatitis_BLink)}
                "Haemophilus influenzae type b (Hib)" -> {link = resources.getString(R.string.haemophilus_influenzae_type_bLink)}
                "Human Papillomavirus (HPV)" -> {link = resources.getString(R.string.human_PapillomavirusLink)}
                "Seasonal Influenza (Flu)" -> {link = resources.getString(R.string.seasonal_InfluenzaLink)}
                "Japanese Encephalitis" -> {link = resources.getString(R.string.japanese_Encephalitis)}
                "Measles" -> {link = resources.getString(R.string.measlesLink)}
                "Meningococcal" -> {link = resources.getString(R.string.meningococcalLink)}
                "Mumps" -> {link = resources.getString(R.string.mumpsLink)}
                "Pertussis" -> {link = resources.getString(R.string.pertussisLink)}
                "Pneumococcal" -> {link = resources.getString(R.string.pneumococcalLink)}
                "Polio" -> {link = resources.getString(R.string.polioLink)}
                "Rabies" -> {link = resources.getString(R.string.rabiesLink)}
                "Rubella" -> {link = resources.getString(R.string.rubellaLink)}
                "Shingles" -> {link = resources.getString(R.string.shinglesLink)}
                "Smallpox" -> {link = resources.getString(R.string.smallpoxLink)}
                "Tetanus" -> {link = resources.getString(R.string.tetanusLink)}
                "Tuberculosis" -> {link = resources.getString(R.string.tuberculosisLink)}
                "Typhoid Fever" -> {link = resources.getString(R.string.typhoid_FeverLink)}
                "Varicella" -> {link = resources.getString(R.string.varicellaLink)}
                "Yellow Fever" -> {link = resources.getString(R.string.yellow_FeverLink)}
            }
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(link)
            startActivity(i)
        })
        builder.setNegativeButton("Delete", DialogInterface.OnClickListener { dialog, id ->
            val authuser = FirebaseAuth.getInstance()
            val db : FirebaseFirestore = FirebaseFirestore.getInstance()
            val userinfo = db.collection("users").document(authuser.uid!!)
            userinfo.get().addOnSuccessListener(OnSuccessListener<DocumentSnapshot> {
                documentSnapshot ->
                run {
                    val user = documentSnapshot.toObject(User::class.java)
                    val alist : ArrayList<Vaccines> = user.getVaccinations()
                    alist.removeAt(position)
                    user.setVaccinations(alist)
                    userinfo.set(user)
                    bottomnav!!.selectedItemId = R.id.navigation_personal
                }
            })


        })
        builder.setNeutralButton("Close", DialogInterface.OnClickListener { dialog, id ->
            // User cancelled the dialog
        })

        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.add -> {
                val newAdder = Intent(this, AddVaccination::class.java)
                startActivityForResult(newAdder, 5)
            }
            else -> {
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}