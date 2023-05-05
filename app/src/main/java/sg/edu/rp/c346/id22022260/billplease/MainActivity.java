// Enhancement done

package sg.edu.rp.c346.id22022260.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText billInput;
    EditText paxInput;
    ToggleButton svsButton;
    ToggleButton gstButton;
    EditText discountInput;
    RadioGroup paymentMethod;
    Button splitButton;
    Button resetButton;
    TextView totalBillDisplay;
    TextView eachPaysDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billInput = findViewById(R.id.billAmountEdit);
        paxInput = findViewById(R.id.paxAmountEdit);
        svsButton = findViewById(R.id.svsToggle);
        gstButton = findViewById(R.id.gstToggle);
        discountInput = findViewById(R.id.discountAmountEdit);
        paymentMethod = findViewById(R.id.paymentRadioGroup);
        splitButton = findViewById(R.id.splitButton);
        resetButton = findViewById(R.id.resetButton);
        totalBillDisplay = findViewById(R.id.totalBillView);
        eachPaysDisplay = findViewById(R.id.paymentView);

        // Button
        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFieldEmpty(billInput)) {
                    Toast.makeText(MainActivity.this, "Bill amount is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isFieldEmpty(paxInput)) {
                    Toast.makeText(MainActivity.this, "Pax amount is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double originalBill = Double.parseDouble(billInput.getText().toString());
                double totalBill = originalBill;
                if (gstButton.isChecked()) {
                    totalBill += originalBill * 0.07;
                }

                if (svsButton.isChecked()) {
                    totalBill += originalBill * 0.1;
                }

                if (!isFieldEmpty(discountInput)) {
                    double discount = Double.parseDouble(discountInput.getText().toString()) / 100;
                    totalBill *= (1 - discount);
                }

                totalBillDisplay.setText(String.format("Total Bill: $%.2f", totalBill));

                int paymentRadioId = paymentMethod.getCheckedRadioButtonId();
                int numOfPax = Integer.parseInt(paxInput.getText().toString());
                double eachPay = totalBill / numOfPax;
                if (paymentRadioId == R.id.cashRadioButton) {
                    eachPaysDisplay.setText(String.format("Each Pays: $%.2f in cash", eachPay));
                } else {
                    eachPaysDisplay.setText(String.format("Each Pays: $%.2f via PayNow to 912345678", eachPay));
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billInput.setText("");
                paxInput.setText("");
                discountInput.setText("");

                svsButton.setChecked(false);
                gstButton.setChecked(false);

                paymentMethod.check(R.id.cashRadioButton);

                totalBillDisplay.setText(R.string.total_bill_format);
                eachPaysDisplay.setText(R.string.each_pays_format);
            }
        });
    }

    private boolean isFieldEmpty(EditText field) {
        return field.getText().toString().isEmpty();
    }
}