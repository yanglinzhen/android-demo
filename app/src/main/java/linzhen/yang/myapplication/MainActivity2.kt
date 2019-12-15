package linzhen.yang.myapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import linzhen.yang.myapplication.ui.mainactivity2.MainActivity2Fragment

class MainActivity2 : AppCompatActivity(), OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity2_activity)
    }

    override fun onFragmentInteraction(uri: Uri) {
        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show()
    }

}
