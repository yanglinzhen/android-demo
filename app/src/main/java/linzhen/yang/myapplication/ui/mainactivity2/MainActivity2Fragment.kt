package linzhen.yang.myapplication.ui.mainactivity2

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.flutter.embedding.android.FlutterActivity
import kotlinx.android.synthetic.main.main_activity2_fragment.*
import linzhen.yang.myapplication.BlankFragment
import linzhen.yang.myapplication.R

class MainActivity2Fragment : Fragment() {

    companion object {
        fun newInstance() = MainActivity2Fragment()
    }

    private lateinit var viewModel: MainActivity2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_activity2_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivity2ViewModel::class.java)


        btn_1.setOnClickListener {
            val action = MainActivity2FragmentDirections.actionMainActivity2FragmentToMainActivity()
            findNavController().navigate(action)
        }

        btn_2.setOnClickListener {
            val action =
                MainActivity2FragmentDirections.actionMainActivity2FragmentToBlankFragment()
            findNavController().navigate(action)
        }

        btn_3.setOnClickListener {
            findNavController().navigate(Uri.parse("http://www.ylz.demo/fragments/from-main-activity/arg-activity"))
        }

        btn_4.setOnClickListener {

            context?.let {ctx ->
                startActivity(
                    FlutterActivity
                        .withNewEngine()
                        .initialRoute("/")
                        .build(ctx)
                )
            }
        }
    }
}
