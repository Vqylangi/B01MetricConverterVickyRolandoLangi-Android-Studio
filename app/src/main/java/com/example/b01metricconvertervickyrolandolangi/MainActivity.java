package com.example.b01metricconvertervickyrolandolangi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Spinner metricSpinner;
    private Spinner sourceUnitSpinner;
    private Spinner targetUnitSpinner;
    private EditText inputValueEditText;
    private Button convertButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metricSpinner = findViewById(R.id.spinnerMetric);
        sourceUnitSpinner = findViewById(R.id.spinnerSourceUnit);
        targetUnitSpinner = findViewById(R.id.spinnerTargetUnit);
        inputValueEditText = findViewById(R.id.editTextInput);
        convertButton = findViewById(R.id.buttonConvert);
        resultTextView = findViewById(R.id.textViewResult);

        ArrayAdapter<CharSequence> metricAdapter = ArrayAdapter.createFromResource(
                this, R.array.metrics_array, android.R.layout.simple_spinner_item);
        metricAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metricSpinner.setAdapter(metricAdapter);

        metricSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedMetric = adapterView.getItemAtPosition(position).toString();
                setupUnitAdapters(selectedMetric);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the conversion when the convert button is clicked
                String selectedMetric = metricSpinner.getSelectedItem().toString();
                String selectedSourceUnit = sourceUnitSpinner.getSelectedItem().toString();
                String selectedTargetUnit = targetUnitSpinner.getSelectedItem().toString();

                double numericValue = Double.parseDouble(inputValueEditText.getText().toString());
                double result = performConversion(selectedMetric, selectedSourceUnit, selectedTargetUnit, numericValue);
                resultTextView.setText(String.format("%.2f", result));
            }
        });

        setupUnitAdapters("");

    }

    private void setupUnitAdapters(String selectedMetric) {
        // Memuat data ke dalam spinner sesuai dengan metrik yang dipilih
        ArrayAdapter<CharSequence> unitAdapter;
        switch (selectedMetric) {
            case "Length":
                unitAdapter = ArrayAdapter.createFromResource(
                        MainActivity.this, R.array.length_units, android.R.layout.simple_spinner_item);
                break;
            case "Mass":
                unitAdapter = ArrayAdapter.createFromResource(
                        MainActivity.this, R.array.mass_units, android.R.layout.simple_spinner_item);
                break;
            case "Temperature":
                unitAdapter = ArrayAdapter.createFromResource(
                        MainActivity.this, R.array.temperature_units, android.R.layout.simple_spinner_item);
                break;
            case "Time":
                unitAdapter = ArrayAdapter.createFromResource(
                        MainActivity.this, R.array.time_units, android.R.layout.simple_spinner_item);
                break;
            default:
                unitAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, new String[]{});
        }

        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sourceUnitSpinner.setAdapter(unitAdapter);
        sourceUnitSpinner.setEnabled(true);

        targetUnitSpinner.setAdapter(unitAdapter);
        targetUnitSpinner.setEnabled(true);
    }

    private double performConversion(String metric, String sourceUnit, String targetUnit, double value) {
        // Implement the conversion logic for all metrics here
        double result = 0.0;

        if (metric.equals("Length")) {
            result = convertLength(sourceUnit, targetUnit, value);
        } else if (metric.equals("Mass")) {
            result = convertMass(sourceUnit, targetUnit, value);
        } else if (metric.equals("Temperature")) {
            result = convertTemperature(sourceUnit, targetUnit, value);
        } else if (metric.equals("Time")) {
            result = convertTime(sourceUnit, targetUnit, value);
        }

        return result;
    }

    private double convertLength(String sourceUnit, String targetUnit, double value) {
        double result = 0.0;

        sourceUnit = sourceUnit.toLowerCase();
        targetUnit = targetUnit.toLowerCase();

        // Konversi dari km ke m
        if (sourceUnit.equals("km") && targetUnit.equals("m")) {
            result = value * 1000;
        }
        // Konversi dari m ke km
        else if (sourceUnit.equals("m") && targetUnit.equals("km")) {
            result = value / 1000;
        }
        // Konversi dari km ke mm
        else if (sourceUnit.equals("km") && targetUnit.equals("mm")) {
            result = value * 1000000;
        }
        // Konversi dari mm ke m
        else if (sourceUnit.equals("mm") && targetUnit.equals("m")) {
            result = value / 1000;
        }

        return result;
    }

    private double convertMass(String sourceUnit, String targetUnit, double value) {
        double result = 0.0;

        sourceUnit = sourceUnit.toLowerCase();
        targetUnit = targetUnit.toLowerCase();

        // Konversi dari kg ke g
        if (sourceUnit.equals("kg") && targetUnit.equals("g")) {
            result = value * 1000;
        }
        // Konversi dari g ke kg
        else if (sourceUnit.equals("g") && targetUnit.equals("kg")) {
            result = value / 1000;
        }
        // Konversi dari mg ke g
        else if (sourceUnit.equals("mg") && targetUnit.equals("g")) {
            result = value / 1000;
        }
        // Konversi dari g ke mg
        else if (sourceUnit.equals("g") && targetUnit.equals("mg")) {
            result = value * 1000;
        }

        return result;
    }

    private double convertTemperature(String sourceUnit, String targetUnit, double value) {
        double result = 0.0;

        sourceUnit = sourceUnit.toLowerCase();
        targetUnit = targetUnit.toLowerCase();

        // Konversi dari Celsius ke Fahrenheit
        if (sourceUnit.equals("c") && targetUnit.equals("f")) {
            result = (value * 9/5) + 32;
        }
        // Konversi dari Fahrenheit ke Celsius
        else if (sourceUnit.equals("f") && targetUnit.equals("c")) {
            result = (value - 32) * 5/9;
        }

        return result;
    }

    private double convertTime(String sourceUnit, String targetUnit, double value) {
        double result = 0.0;

        sourceUnit = sourceUnit.toLowerCase();
        targetUnit = targetUnit.toLowerCase();

        // Konversi dari detik ke menit
        if (sourceUnit.equals("s") && targetUnit.equals("min")) {
            result = value / 60;
        }
        // Konversi dari menit ke detik
        else if (sourceUnit.equals("min") && targetUnit.equals("s")) {
            result = value * 60;
        }
        // Konversi dari menit ke jam
        else if (sourceUnit.equals("min") && targetUnit.equals("h")) {
            result = value / 60;
        }
        // Konversi dari jam ke menit
        else if (sourceUnit.equals("h") && targetUnit.equals("min")) {
            result = value * 60;
        }

        return result;
    }
}
