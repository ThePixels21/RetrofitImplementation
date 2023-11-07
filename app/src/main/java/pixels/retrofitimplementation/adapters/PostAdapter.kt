package pixels.retrofitimplementation.adapters

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import pixels.retrofitimplementation.model.Post

class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    class PostViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val textView = TextView(parent.context)
        return PostViewHolder(textView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postJson = gson.toJson(posts[position])
        holder.textView.text = postJson
    }

    override fun getItemCount() = posts.size
}