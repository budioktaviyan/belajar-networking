package id.kotlin.networking

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private val disposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val datasource = NetworkProvider.providesHttpAdapter().create(Datasource::class.java)
    val mockDatasource = NetworkProvider.providesMockAdapter().create(MockDatasource::class.java)

    val singleResponse = datasource.discoverMovie(BuildConfig.KEY)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())

    val singleMockResponse = mockDatasource.getContacts()
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())

    val responses = Singles.zip(singleResponse, singleMockResponse)
    responses.subscribe { response ->
      val model = MainModel(response.first, response.second)
      rv_main.adapter = MainAdapter(model.response.results)

      val firstName = model.mock.first().firstName
      val lastName = model.mock.first().lastName
      Toast.makeText(this, "$firstName $lastName", Toast.LENGTH_SHORT).show()
    }.addTo(disposable)
  }

  override fun onDestroy() {
    super.onDestroy()
    disposable.clear()
  }
}