package edu.bjtu.example.sportsdashboard;

//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class LoginActivity extends AppCompatActivity {
//    private static final String TAG = "LoginActivity";
//    private static final int REQUEST_SIGNUP = 0;
//
//    @BindView(R.id.input_email) EditText _emailText;
//    @BindView(R.id.input_password) EditText _passwordText;
//    @BindView(R.id.btn_login) Button _loginButton;
//    @BindView(R.id.link_signup) TextView _signupLink;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);
//
//        _loginButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                //startActivity(intent);
//
//                login();
//
//            }
//        });
//
//        _signupLink.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Start the Signup activity
//                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
//                startActivityForResult(intent, REQUEST_SIGNUP);
//                finish();
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//            }
//        });
//    }
//
//    public void login() {
//        Log.d(TAG, "Login");
//
//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }
//
//        _loginButton.setEnabled(false);
//
//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//                R.style.AppTheme_Dark);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();
//
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        // TODO: Implement your own authentication logic here.
//
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//                this.finish();
//            }
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        // Disable going back to the MainActivity
//        moveTaskToBack(true);
//    }
//
//    public void onLoginSuccess() {
//        _loginButton.setEnabled(true);
//        finish();
//        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//        startActivity(intent);
//    }
//
//    public void onLoginFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//
//        _loginButton.setEnabled(true);
//    }
//
//    public boolean validate() {
//        boolean valid = true;
//
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("enter a valid email address");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("between 4 and 10 alphanumeric characters");
//            valid = false;
//        } else {
//            _passwordText.setError(null);
//        }
//
//        return valid;
//    }
//}

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private String usernameStr;
    private String passwordStr;
    private final int LOGINSUCCESS=0;
    private final int LOGINNOTFOUND=1;
    private final int LOGINEXCEPT=2;
    private final int REGISTERSUCCESS=3;
    private final int REGISTERNOTFOUND=4;
    private final int REGISTEREXCEPT=5;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case LOGINSUCCESS:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case LOGINNOTFOUND:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case LOGINEXCEPT:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case REGISTERSUCCESS:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case REGISTERNOTFOUND:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case REGISTEREXCEPT:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到我们需要的控件
        username = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);


    }
    //登录按钮的点击事件，也可以用set监听器的方法，不过这种方法简单
    public void login(View v){
        //获取编辑框内的内容
        usernameStr = username.getText().toString().trim();
        passwordStr = password.getText().toString().trim();

        //判断是否输入为空（在这里就不再进行正则表达式判断了）
        if(usernameStr.equals("") || passwordStr.equals("")){
            Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }//进行登录操作(联网操作要添加权限)
        else {
            //联网操作要开子线程，在主线程不能更新UI
            new Thread(){

                private HttpURLConnection connection;


                @Override
                public void run() {

                    try {
                        //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                        // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                        String data2= "username="+ URLEncoder.encode(usernameStr,"utf-8")+"&password="+ URLEncoder.encode(passwordStr,"utf-8")+"&sign="+URLEncoder.encode("1","utf-8");
                        connection=HttpConnectionUtils.getConnection(data2);
                        int code = connection.getResponseCode();
                        if(code==200){
                            InputStream inputStream = connection.getInputStream();
                            String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                            Message message = Message.obtain();//更新UI就要向消息机制发送消息
                            message.what=LOGINSUCCESS;//用来标志是哪个消息
                            message.obj=str;//消息主体
                            handler.sendMessage(message);

                        }
                        else {
                            Message message = Message.obtain();
                            message.what=LOGINNOTFOUND;
                            message.obj="注册异常...请稍后再试";
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                        e.printStackTrace();
                        Message message = Message.obtain();
                        message.what=LOGINEXCEPT;
                        message.obj="服务器异常...请稍后再试";
                        handler.sendMessage(message);
                    }
                }
            }.start();//不要忘记开线程
        }
    }
    //注册按钮的点击事件
    public void register(View v){
        usernameStr = username.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
        if(usernameStr.equals("") || passwordStr.equals("")){
            Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }
        else {
            new Thread(){

                HttpURLConnection connection=null;
                @Override
                public void run() {
                    try {
                        String data= "username="+ URLEncoder.encode(usernameStr,"utf-8")+"&password="+ URLEncoder.encode(passwordStr,"utf-8")+"&sign="+URLEncoder.encode("2","utf-8");
                        connection=HttpConnectionUtils.getConnection(data);
                        int code = connection.getResponseCode();
                        if(code==200){
                            InputStream inputStream = connection.getInputStream();
                            String str = StreamChangeStrUtils.toChange(inputStream);
                            Message message = Message.obtain();
                            message.obj=str;
                            message.what=REGISTERSUCCESS;
                            handler.sendMessage(message);
                        }
                        else {
                            Message message = Message.obtain();
                            message.what=REGISTERNOTFOUND;
                            message.obj="注册异常...请稍后再试";
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message message = Message.obtain();
                        message.what=REGISTEREXCEPT;
                        message.obj="服务器异常...请稍后再试";
                        handler.sendMessage(message);
                    }

                }
            }.start();//不要忘记开线程

        }
    }
}

