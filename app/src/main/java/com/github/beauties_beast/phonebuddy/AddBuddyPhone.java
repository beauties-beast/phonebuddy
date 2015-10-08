package com.github.beauties_beast.phonebuddy;

import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class AddBuddyPhone extends AppCompatActivity {

    private String phoneNumber;
    private String confirmationCode;

    DatabaseHelper databaseHelper;

    EditText editTextNumber;
    Button buttonSend;
    TextView confirmation1;
    TextView confirmation2;
    EditText editTextConfirmationCode;
    Button buttonConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buddy_phone);

        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        buttonSend = (Button) findViewById(R.id.buttonAddSend);
        confirmation1 = (TextView) findViewById(R.id.textViewConfirmation1);
        confirmation2 = (TextView) findViewById(R.id.textViewConfirmation2);
        editTextConfirmationCode = (EditText) findViewById(R.id.editTextAddConfirmation);
        buttonConfirmation  = (Button) findViewById(R.id.buttonAddConfirmation);

        buttonSend.setOnClickListener(buttonSendClick);
        buttonConfirmation.setOnClickListener(buttonConfirmationClick);

        databaseHelper = new DatabaseHelper(getBaseContext());
    }

    View.OnClickListener buttonSendClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String number = editTextNumber.getText().toString();
            if (number.equals("")) {
                String toastMessage = "This field cannot be empty.";
                Toast.makeText(getBaseContext(), toastMessage, Toast.LENGTH_SHORT).show();
            } else if (!number.contains("+")) {
                String toastMessage = "This doesn't seem to be a valid number.";
                Toast.makeText(getBaseContext(), toastMessage, Toast.LENGTH_SHORT).show();
            } else {
                editTextNumber.setEnabled(false);
                buttonSend.setEnabled(false);
                confirmation1.setVisibility(View.VISIBLE);
                confirmation2.setVisibility(View.VISIBLE);
                editTextConfirmationCode.setVisibility(View.VISIBLE);
                buttonConfirmation.setVisibility(View.VISIBLE);
                sendRequest();
                String toastMessage = String.format("Confirmation code sent to %s.", phoneNumber);
                Toast.makeText(getBaseContext(), toastMessage, Toast.LENGTH_LONG).show();
            }
        }
    };

    View.OnClickListener buttonConfirmationClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String toastMessage;
            String tryConfirmationCode = editTextConfirmationCode.getText().toString();
            if(!confirmationCode.equals(tryConfirmationCode)) {
                toastMessage = "Incorrect confirmation code. Please try again.";
                Toast.makeText(getBaseContext(), toastMessage, Toast.LENGTH_LONG).show();
            }
            else {
                toastMessage = String.format("Buddy phone %s paired successfully.", phoneNumber);
                Toast.makeText(getBaseContext(), toastMessage, Toast.LENGTH_LONG).show();
                BuddyPhone buddyPhone = new BuddyPhone("Buddy Phone", phoneNumber);
                databaseHelper.addBuddyPhone(buddyPhone);
                finish();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_buddy_phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void sendRequest() {
        phoneNumber = editTextNumber.getText().toString();
        Random r = new Random();
        confirmationCode = String.valueOf(r.nextInt(9999-1000)+10000);
        String message = String.format("Your confirmation code is %s.", confirmationCode);
        SmsHelper.sendSmsWithHeaders(phoneNumber, message);
    }
}
