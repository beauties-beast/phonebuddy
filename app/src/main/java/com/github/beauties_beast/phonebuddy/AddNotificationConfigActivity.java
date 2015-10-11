package com.github.beauties_beast.phonebuddy;

import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddNotificationConfigActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    PackageManagerHelper packageManagerHelper;

    ArrayList<String> packageNames;
    ArrayList<String> packageNamesFormatted;
    Spinner spinnerApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification_config);
        databaseHelper = new DatabaseHelper(getBaseContext());
        packageManagerHelper = new PackageManagerHelper(getBaseContext());

        spinnerApplication = (Spinner) findViewById(R.id.addnotificationconfig_spinner);

        packageNames = packageManagerHelper.getAllPackageNames();
        packageNamesFormatted = packageManagerHelper.getAllAppNamesAndPackageNames();

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, packageNamesFormatted);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerApplication.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.addnotificationconfig_add);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = packageManagerHelper.getPackageNameFromParentheses(spinnerApplication.getSelectedItem().toString());
                databaseHelper.addNotificationConfig(packageName);
                Toast.makeText(getBaseContext(), String.format("Enabled forwarding for %s", packageManagerHelper.getAppName(packageName)), Toast.LENGTH_LONG).show();
                finish();
            }
        };
        button.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_notification_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
