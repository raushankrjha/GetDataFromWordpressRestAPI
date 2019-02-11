package in.learncodeonline.worpressposttestapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    public static String TAG="WPAPP";
    public List<Posts> mPosts;
    public Button btnGetPost;
    public RecyclerView recyclerView;
    public PostAdapter postAdapter;
    public int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= (RecyclerView) findViewById(R.id.recyclerHome);
        postAdapter=new PostAdapter(mPosts,this,false,false);

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
                        Log.d(TAG, response.toString() + "Size: "+response.length());
                        for(int i=0;i<response.length();i++){
                            final Posts post=new Posts();
                            try {
                                Log.d(TAG,"Object at " + i+ response.get(i));
                                JSONObject obj=response.getJSONObject(i);
                                post.setId(obj.getInt("id"));
                                post.setCreatedAt(obj.getString("date"));
                                post.setPostURL(obj.getString("link"));
                                JSONObject titleObj=obj.getJSONObject("title");
                                post.setTitle(titleObj.getString("rendered"));

                                //Get excerpt
                                JSONObject exerptObj=obj.getJSONObject("excerpt");
                                post.setExcerpt(exerptObj.getString("rendered"));
                                JSONObject featureImage=obj.getJSONObject("_links");
                                JSONArray featureImageUrl=featureImage.getJSONArray("wp:featuredmedia");
                                JSONObject featureImageObj=featureImageUrl.getJSONObject(0);
                                String fiurl=featureImageObj.getString("href");


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
        recyclerView.setAdapter(postAdapter);
        postAdapter.setData(mPosts);
        postAdapter.notifyDataSetChanged();
    }
}
