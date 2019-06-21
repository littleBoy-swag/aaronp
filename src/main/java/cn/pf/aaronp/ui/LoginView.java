package cn.pf.aaronp.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pf.aaronp.R;

/**
 * Created by aaron pan on 2019/6/3.
 */

public class LoginView extends LinearLayout {

    private EditText mEtName, mEtPwd;
    private TextView mTvLogin, mTvRegister;
    private ImageView mIvLogo;
    private OnLoginViewClickListener listener;

    private String name;
    private String pwd;

    public LoginView(Context context) {
        this(context, null);
    }

    public LoginView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_login, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mIvLogo = findViewById(R.id.iv_logo);
        mEtName = findViewById(R.id.et_name);
        mEtPwd = findViewById(R.id.et_pwd);
        mTvLogin = findViewById(R.id.tv_login);
        mTvLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mEtName.getText().toString();
                pwd = mEtPwd.getText().toString();
                listener.onLoginClick(name, pwd);
            }
        });
        mTvRegister = findViewById(R.id.tv_reg);
        mTvRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRegisterClick();
            }
        });
    }

    public void setLogoImg(int imgId){
        mIvLogo.setImageResource(imgId);
    }

    public void setOnLoginViewClickListener(OnLoginViewClickListener listener) {
        this.listener = listener;
    }

    public interface OnLoginViewClickListener {
        void onLoginClick(String name, String pwd);

        void onRegisterClick();
    }

}
