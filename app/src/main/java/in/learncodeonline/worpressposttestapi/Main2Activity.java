package in.learncodeonline.worpressposttestapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.learncodeonline.worpressposttestapi.Adapter.PostAdapter;
import in.learncodeonline.worpressposttestapi.Model.Posts;

public class Main2Activity extends AppCompatActivity {
    public static String TAG="WPAPP";
    public List<Posts> mPosts;
    public RecyclerView recyclerView;
    public PostAdapter postAdapter;
    public int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerHome);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getPost();
    }
    public void getPost() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        mPosts = new ArrayList<Posts>();
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, Config.base_url+"wp/v2/posts/?page="+page, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
// display response
                        for(int i=0;i<response.length();i++){
                            final Posts post=new Posts();
                            try {
                                JSONObject obj=response.getJSONObject(i);
                                post.setId(obj.getInt("id"));
                                post.setCreatedAt(obj.getString("date"));
                                post.setPostURL(obj.getString("link"));
                                JSONObject titleObj=obj.getJSONObject("title");
                                post.setTitle(titleObj.getString("rendered"));

                                //Get excerpt
                                JSONObject exerptObj=obj.getJSONObject("excerpt");
                                post.setExcerpt(exerptObj.getString("rendered"));
                                mPosts.add(post);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setData(mPosts);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }
        ) ;

        requestQueue.add(getRequest);
    }
     public void setData(List<Posts> posts){
         postAdapter=new PostAdapter(mPosts,this);
        recyclerView.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();
    }
}
