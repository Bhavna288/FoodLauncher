package com.launcher.foodlauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.launcher.foodlauncher.model.Userss;

public class SignUpActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference users;

    FirebaseAuth fAuth;

    EditText mFullName, mEmail, mPassword, mPhone;
    Button btnSignUp;
    TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//firebase

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");


        mFullName = (EditText)findViewById(R.id.input_fullname);
        mEmail = (EditText)findViewById(R.id.input_email);
        mPassword = (EditText)findViewById(R.id.input_password);
        mPhone = (EditText)findViewById(R.id.input_phone);
        btnSignUp = (Button)findViewById(R.id.btn_signup);
        login = findViewById(R.id.login_here);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), PermissionsActivity.class));
            finish();
        }


        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();
                final Userss userss = new Userss(mFullName.getText().toString(),
                        mPassword.getText().toString(),
                        mPhone.getText().toString(),
                        mEmail.getText().toString()
                        );

                users.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){

                        if(dataSnapshot.child(userss.getUsername()).exists())
                        Toast.makeText(SignUpActivity.this,"This UserName Already Exists!",Toast.LENGTH_SHORT).show();
else{
                            users.child(userss.getUsername()).setValue(userss);
                            Toast.makeText(SignUpActivity.this,"Successfull SignUp",Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError){

                    }
                });

//Register the user in firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "User created.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                login.setOnClickListener(v1 -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(SignUpActivity.this, PermissionsActivity.class));
        }

    }



 /*   private EditText mFullName, mEmail, mPassword, mPhone;
    private Button btnSignUp;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    private String TAG = "SignUpActivity";
    private String name, email,  phone;
    private String password;
    private User user;
    private FirebaseAuth mAuth;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFullName = findViewById(R.id.input_fullname);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mPhone = findViewById(R.id.input_phone);
        btnSignUp = findViewById(R.id.btn_signup);
        login = findViewById(R.id.login_here);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //insert data into firebase database
                if(mEmail.getText().toString() != null && mPassword.getText().toString() != null) {
                    name = mFullName.getText().toString();
                    email = mEmail.getText().toString();
                    phone = mPhone.getText().toString();
                    password = mPassword.getText().toString();
                    user = new User(name, email, phone);
                    registerUser();
                }
            }
        });

    }

    public void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * adding user information to database and redirect to login screen
     * @param currentUser
     */
 /*
    public void updateUI(FirebaseUser currentUser) {
        String keyid = mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(user); //adding user info to database
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }
}*/


/*
    EditText mFullName, mEmail, mPassword, mPhone;
    Button btnSignUp;
    private ProgressDialog loadingBar;
    FirebaseAuth fAuth;
    TextView login;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFullName = findViewById(R.id.input_fullname);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mPhone = findViewById(R.id.input_phone);
        btnSignUp = findViewById(R.id.btn_signup);
        loadingBar = new ProgressDialog(this);
        login = findViewById(R.id.login_here);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), PermissionsActivity.class));
            finish();
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });


    }



    private void CreateAccount()
    {
        String name = mFullName.getText().toString();
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        String email = mEmail.getText().toString();



        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name,email, phone, password);
        }
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                Toast.makeText(SignUpActivity.this, "User created.", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                Toast.makeText(SignUpActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(v1 -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));



    }
    @Override
    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(SignUpActivity.this, PermissionsActivity.class));
        }

    }



    private void ValidatephoneNumber(final String name, final String email,final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(email).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    userdataMap.put("email", email);

                    RootRef.child("Users").child(email).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(SignUpActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(SignUpActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "This " + email + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SignUpActivity.this, "Please try again using another email.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/

  /*  EditText mFullName, mEmail, mPassword, mPhone;
    Button btnSignUp;
    TextView login;
    FirebaseAuth fAuth;
    //added later
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFullName = findViewById(R.id.input_fullname);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mPhone = findViewById(R.id.input_phone);
        btnSignUp = findViewById(R.id.btn_signup);
        login = findViewById(R.id.login_here);
//added later
        loadingBar = new ProgressDialog(this);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), PermissionsActivity.class));
            finish();
        }

        btnSignUp.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }

             if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.");
                return;
            }

             if (password.length() < 6) {
                mPassword.setError("Password Must be >= 6 Characters");
                return;
            }





            //Register the user in firebase

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "User created.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(SignUpActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            login.setOnClickListener(v1 -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(SignUpActivity.this, PermissionsActivity.class));
        }

    }*/
}
