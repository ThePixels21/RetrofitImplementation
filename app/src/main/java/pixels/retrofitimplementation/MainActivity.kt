package pixels.retrofitimplementation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pixels.retrofitimplementation.adapters.PostAdapter
import pixels.retrofitimplementation.interfaces.JsonPlaceholderService
import pixels.retrofitimplementation.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Crea una instancia de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crea una instancia del servicio
        val service = retrofit.create(JsonPlaceholderService::class.java)

        // Solicitud a la API
        val postsCall = service.getPosts()
        // Ejecuta la solicitud de forma asíncrona
        postsCall.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
                val posts = response.body()

                if (posts != null) {
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerView.adapter = PostAdapter(posts)
                    for (post in posts) {
                        println("ID: " + post.id)
                        println("User ID: " + post.userId)
                        println("Title: " + post.title)
                        println("Text: " + post.body)
                    }
                } else {
                    println("No posts found")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                println(t.message)
            }
        })
    }
}