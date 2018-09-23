package pnytrainings.com.volleyexample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ProgressDialog loading;
    ArrayList<Contacts> contactses = new ArrayList<>();
    public static final String URL = "https://api.androidhive.info/contacts/";
    String sendUrl = "http://epay.cybussolutions.com/Api_Service/signupUser";
    RecyclerView contactsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataFromServer();

        contactsRv = (RecyclerView) findViewById(R.id.contactsRv);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        contactsRv.setLayoutManager(mLayoutManager);

    }


    public   ArrayList<Contacts> parseJson(String json) {

        try {
            JSONObject data = new JSONObject(json);
            JSONArray contacts = data.getJSONArray("contacts");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);

                Contacts myContacts = new Contacts();
                myContacts.setId(contact.optString("id", ""));
                myContacts.setName(contact.optString("name", ""));
                myContacts.setAddress(contact.optString("address", ""));
                myContacts.setEmail(contact.optString("email", ""));
                myContacts.setGender(contact.optString("gender", ""));

                JSONObject phoneOBJ = contact.getJSONObject("phone");
                Phone phone = new Phone();
                phone.setHome(phoneOBJ.optString("home", ""));
                phone.setMobile(phoneOBJ.optString("mobile", ""));
                phone.setOffice(phoneOBJ.optString("office", ""));
                myContacts.setPhone(phone);

                contactses.add(myContacts);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contactses;
    }

    public void getDataFromServer() {

        loading = ProgressDialog.show(MainActivity.this, "Please wait...",
                "Getting Data From Server ...", false, false);

        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        // Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        ArrayList<Contacts> contactses =    parseJson(response);

                        ContactsAdapter contactsAdapter = new ContactsAdapter(MainActivity.this,contactses);
                        contactsRv.setAdapter(contactsAdapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }

    public void signUp() {

        loading = ProgressDialog.show(MainActivity.this, "Please wait...", "Getting Data From Server ...", false, false);

        loading.show();

        StringRequest request = new StringRequest(Request.Method.POST, sendUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("email", "tissue@yahoo.com");
                map.put("first_name", "white");
                map.put("last_name", "tissue");
                map.put("password", "12345");
                map.put("phone_number", "000111");
                map.put("address", "house 15 street 28");
                map.put("gender", "1");
                map.put("cardtype", "1");

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);

    }

}
