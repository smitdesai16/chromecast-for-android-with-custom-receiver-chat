package com.smitdesai16.chromecastforandroidwithcustomreceiverchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SessionManager sessionManager;
    private Button btnSend;
    private EditText etText;

    private final CastSessionListener castSessionListener = new CastSessionListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CastContext castContext = CastContext.getSharedInstance(this);
        sessionManager = castContext.getSessionManager();

        btnSend = findViewById(R.id.btnSend);
        etText = findViewById(R.id.etText);

        btnSend.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.media_route_menu_item);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionManager.addSessionManagerListener(castSessionListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sessionManager.removeSessionManagerListener(castSessionListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                try {
                    final JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type", "message");
                    jsonObject.put("text", etText.getText().toString());
                    final String body = jsonObject.toString(1);
                    if (sessionManager.getCurrentCastSession() != null) {
                        sessionManager.getCurrentCastSession().sendMessage("urn:x-cast:com.example.castdata", body).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                if (status.isSuccess()) {
                                    etText.setText("");
                                    Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}