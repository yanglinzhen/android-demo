package linzhen.yang.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var disposable: Disposable
    val observable1 = Observable.intervalRange(0, 27, 0, 1, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    val observable2 = Observable.intervalRange(0, 5, 0, 500, TimeUnit.MILLISECONDS)
//    val observable2 = Observable.create<Long> {
//        it.onNext(2)
//    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startCount(v: View) {
//        observable1.subscribe {
//            textView.text = "Count: ${it.toInt()}"
//        }
        var host = "https://www.secure.hsbcnet.com"
        val observable = Observable.create<String> { out ->
            try {
                val timeout: Long = 50
                val client = OkHttpClient.Builder()
                    .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                    .build()
                val request = Request.Builder()
                    .url("$host/uims/portal/IDV_CAM10_AUTHENTICATION?__nativeApp=true&__respType=JSON")
                    .build()
                val resp = client.newCall(request).execute()
                val result = resp.request().url().toString().replace("/uims/portal/IDV_CAM10_AUTHENTICATION?__nativeApp=true&__respType=JSON", "")
                host = result
                out.onNext(host)
                out.onComplete()
            } catch (e: Exception) {
                out.onError(e)
            }
        }
            .doOnDispose {
                Log.i("ylz", "dispose: ${Thread.currentThread().name}")
            }
            .doOnComplete {
                Log.i("ylz", "complete: ${Thread.currentThread().name}")
            }
            .onErrorReturn {
                it.message
            }


        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i("ylz", it)
            }
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i("ylz", it)
            }
    }

    fun open(v: View) {
//        observable1.subscribe()
//        observable2.subscribe()
        Observable.zip(observable1, observable2,
            BiFunction<Long, Long, Long> { t1, t2 ->
                t1 + t2
            })
            .doOnComplete {
                Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                textView2.text = "Total: ${it.toInt()}"
            }
    }
}
