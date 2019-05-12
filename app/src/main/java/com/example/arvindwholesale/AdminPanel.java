package com.example.arvindwholesale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
    }

    public void AddItemActivity(View view) {
        Intent AddUpdateItem=new Intent(getApplicationContext(),addupdateitem.class);
        startActivity(AddUpdateItem);
    }

    public void UpdateItem(View view) {

    }
}
