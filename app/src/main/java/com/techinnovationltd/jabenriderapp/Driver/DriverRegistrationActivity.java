package com.techinnovationltd.jabenriderapp.Driver;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.techinnovationltd.jabenriderapp.Model.User;
import com.techinnovationltd.jabenriderapp.ProcessMain;
import com.techinnovationltd.jabenriderapp.R;
import com.techinnovationltd.jabenriderapp.Registration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class DriverRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button btn_agree;

    Spinner spn;
    String name[] = {"NID", "PASSPORT"};
    ArrayAdapter<String> adapter;

    EditText rider_name,address,nid_or_passport,driving_license,fintess,tax_token,referral_number;
    TextView date_birth;

    Pinview pinview;
    ImageView take_photo;

    String Ridername,Rideraddress,Ridernid_or_passport,Riderdateofbirth,Riderdrivinglicense,Riderfitness,RidertaxToken,RiderreferralNumber,RiderUserProfile="Default",Rider_vehicle,Service_type="Driver";

    FirebaseUser firebaseUser,firebaseUserChecking;
    DatabaseReference reference,referenceChecking;

    DatePickerDialog datePickerDialog;


    private int STORAGE_PERMISSION_CODE = 1;


    //For the image upload on database

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);


        //set back bar in aActionbar:1

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        rider_name=findViewById(R.id.edit_name);
        address=findViewById(R.id.edit_address);
        nid_or_passport=findViewById(R.id.edit_nid);
        date_birth=findViewById(R.id.edit_date);
        driving_license=findViewById(R.id.edit_license);
        fintess=findViewById(R.id.edit_fitness);
        tax_token=findViewById(R.id.edit_taxt);
        referral_number=findViewById(R.id.edit_referrel);

        pinview=findViewById(R.id.pin_view);
        take_photo=findViewById(R.id.img_take_photo);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};

                if(ContextCompat.checkSelfPermission(getApplicationContext(),
                        permissions[0]) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(),
                        permissions[1]) == PackageManager.PERMISSION_GRANTED){

                    openImage();

                }

                else{
                    requestStoragePermission();
                }

            }
        });


        date_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar=Calendar.getInstance();

                final int year=calendar.get(Calendar.YEAR);
                final int month=calendar.get(Calendar.MONTH);
                final int date=calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog=new DatePickerDialog(DriverRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {

                        date_birth.setHint("Date of Birth*");
                        date_birth.setText("");

                        Calendar today = Calendar.getInstance();
                        int age = today.get(Calendar.YEAR) - year1;
                        if (age < 18) {

                            Toast.makeText(getApplicationContext(),"You are under 18",Toast.LENGTH_LONG).show();
                            //do something
                        } else {

                            date_birth.setText(day1 + "/" + (month1+ 1) + "/" + year1);

                        }




                        /*Calendar userAge = new GregorianCalendar(year,month,date);
                        Calendar minAdultAge = new GregorianCalendar();
                        minAdultAge.add(Calendar.YEAR, -18);
                        if (minAdultAge.before(userAge)) {

                        }

                        else {

                        }*/

                    }
                },year,month,date);
                datePickerDialog.show();

            }
        });



        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Sign Up for Bike");



        Spinner spn = findViewById(R.id.txt_spinner);
        adapter = new ArrayAdapter<String>(DriverRegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, name);
        spn.setAdapter(adapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {

                    nid_or_passport.setHint("Nid Number");


                }
                else {

                    nid_or_passport.setHint("Passport Number");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        final String phone=getIntent().getExtras().getString("phones");
        final String city=getIntent().getExtras().getString("city");
        final String vehicle=getIntent().getExtras().getString("vehicle");




        //for set spinner

        btn_agree =  findViewById(R.id.btn_agreeSignup);

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Ridername=rider_name.getText().toString();
                Rideraddress=address.getText().toString();
                Ridernid_or_passport=nid_or_passport.getText().toString();
                Riderdateofbirth=date_birth.getText().toString();
                Riderdrivinglicense=driving_license.getText().toString();
                Riderfitness=fintess.getText().toString();
                RidertaxToken=tax_token.getText().toString();
                RiderreferralNumber=referral_number.getText().toString();
                Rider_vehicle=pinview.getValue().toString();


                if (TextUtils.isEmpty(Ridername) || TextUtils.isEmpty(Rideraddress) || TextUtils.isEmpty(Ridernid_or_passport) || TextUtils.isEmpty(Riderdateofbirth) || TextUtils.isEmpty(Riderdrivinglicense)){

                    Toast.makeText(DriverRegistrationActivity.this,"Input All Information",Toast.LENGTH_LONG).show();
                }

                else {
                    RiderRegistraction(Ridername,Rideraddress,Ridernid_or_passport,Riderdateofbirth,
                            phone,city,vehicle,Riderdrivinglicense,Riderfitness,RidertaxToken,RiderreferralNumber,Rider_vehicle,RiderUserProfile,Service_type);
                }

            }
        });

    }

    //Bulid popup for Storagr permission

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to read your Storage")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(DriverRegistrationActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                openImage();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //End code for Stroage Permission


    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }



    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = DriverRegistrationActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }




    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(DriverRegistrationActivity.this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final  StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return  fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        RiderUserProfile = downloadUri.toString();

                        /*reference = FirebaseDatabase.getInstance().getReference("Complain").child(firebaseUser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("Image_url", ""+mUri);
                        reference.updateChildren(map);*/

                        Toast.makeText(DriverRegistrationActivity.this,"Sucessfully Upload Image",Toast.LENGTH_LONG).show();

                        pd.dismiss();
                    } else {
                        Toast.makeText(DriverRegistrationActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DriverRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(DriverRegistrationActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(DriverRegistrationActivity.this, "Upload in preogress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }

    }






    private void RiderRegistraction(String ride_name,String ride_address,String ridernid_or_passport,String riderdateofbirth,
                                    String phone,String city, String vehicle,String riderdrivinglicense, String riderfitness,
                                    String ridertaxtoken,String riderreferralnumber,
                                    String rider_vehicle, String riderUserProfile,String service_type){


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userID=firebaseUser.getUid();

        reference= FirebaseDatabase.getInstance().getReference("Riders").child(userID);

        HashMap<String,Object> hashMap=new HashMap<>();

        hashMap.put("id",userID);
        hashMap.put("Name",ride_name);
        hashMap.put("Address",ride_address);
        hashMap.put("Nid_Passport",ridernid_or_passport);
        hashMap.put("Rider_Date_Birth",riderdateofbirth);
        hashMap.put("Phone",phone);
        hashMap.put("City",city);
        hashMap.put("Vehicle",vehicle);
        hashMap.put("Rider_Driving_License",riderdrivinglicense);
        hashMap.put("Rider_Fitness",riderfitness);
        hashMap.put("Rider_tax_Token",ridertaxtoken);
        hashMap.put("Rider_Referral_Number",riderreferralnumber);
        hashMap.put("Rider_Vehicle",rider_vehicle);
        hashMap.put("Rider_User_Profile",riderUserProfile);
        hashMap.put("Service_Type",service_type);
        hashMap.put("Bikas_Number","NotGiven");
        hashMap.put("Bikas_Transcation_Number","NotGiven");
        hashMap.put("Status","NotGiven");




        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    startActivity(new Intent(DriverRegistrationActivity.this, BikasPayment.class));
                    finish();

                    Toast.makeText(getApplicationContext(),"Sucessfull",Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(getApplicationContext(),"Not Sucessfull",Toast.LENGTH_LONG).show();
                }

            }
        });

    }




    //for set spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //for set spinner


    //set back bar in a Actionbar:2
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //set back bar in a Actionbar:2

}
