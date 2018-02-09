package com.iter.ivory;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddVaccination extends AppCompatActivity {
    FirebaseAuth authUser = FirebaseAuth.getInstance();
    User u = new User(authUser.getCurrentUser().getDisplayName(),new ArrayList<Vaccines>());
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vaccination);

        DocumentReference userinfo;
        userinfo = db.collection("users").document(authUser.getUid());
        userinfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    u = documentSnapshot.toObject(User.class);
            }
        });
        final Spinner vaccinationList = findViewById(R.id.spinner);

        final Spinner vaccinationsubList = findViewById(R.id.spinnersub);
        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Adenovirus));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vaccinationsubList.setAdapter(adapter);

        Button finishButton = findViewById(R.id.finish);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText yearView = findViewById(R.id.year);
                TextInputEditText monthView = findViewById(R.id.month);
                String yearInput = yearView.getText().toString();
                String monthInput = monthView.getText().toString();
                int yearNumber;
                int monthNumber;
                try {
                    yearNumber = Integer.valueOf(yearInput);
                }catch (NumberFormatException e){
                    yearNumber = 0;
                }
                try {
                    monthNumber = Integer.valueOf(yearInput);
                }catch (NumberFormatException e){
                    monthNumber = 0;
                }
                Vaccines vac = new Vaccines(vaccinationList.getSelectedItem().toString(),  vaccinationsubList.getSelectedItem().toString(), monthNumber, yearNumber);
                u.addVaccinations(vac);
                db.collection("users").document(authUser.getUid()).set(u);
                finish();
            }
        });

        vaccinationList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Adenovirus));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 1:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Anthrax));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 2:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Diphtheria));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 3:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Hepatitis_A));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 4:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Hepatitis_B));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 5:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Haemophilus_influenzae_type_b));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 6:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Human_Papillomavirus));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 7:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Seasonal_Influenza));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 8:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Japanese_Encephalitis));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 9:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Measles));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 10:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Meningococcal));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 11:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Mumps));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 12:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Pertussis));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 13:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Pneumococcal));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 14:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Polio));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 15:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Rabies));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 16:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Rotavirus));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 17:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Rubella));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 18:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Shingles));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 19:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Smallpox));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 20:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Tetanus));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 21:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Tuberculosis));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 22:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Typhoid_Fever));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 23:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Varicella));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;
                    case 24:
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Yellow_Fever));
                        adapter.notifyDataSetChanged();
                        vaccinationsubList.setAdapter(adapter);
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
