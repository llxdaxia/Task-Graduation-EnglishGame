package cn.zhu.cainiao.app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.alien95.util.Utils;
import cn.zhu.cainiao.R;


/**
 * Created by linlongxin on 2016/1/15.
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setToolbarIsBack(boolean isBack) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isBack);
        }
    }

    public void showProgressBar(String title) {
        TextView text = new TextView(this);
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            text.setTextColor(getResources().getColor(R.color.colorPrimary));
            text.setTextSize(16);
            text.setPadding(Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(16));
            ProgressBar progressBar = new ProgressBar(this);
            progressBar.setPadding(0, 0, 0, Utils.dip2px(8));
            linearLayout.addView(text);
            linearLayout.addView(progressBar);
            builder.setView(linearLayout);
            dialog = builder.create();
        }
        if (title.isEmpty()) {
            text.setVisibility(View.GONE);
        } else
            text.setText(title);
        dialog.show();
    }

    public void showDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView text = new TextView(this);
        text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        text.setGravity(Gravity.CENTER);
        text.setTextColor(getResources().getColor(R.color.colorPrimary));
        text.setTextSize(18);
        text.setPadding(Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(16));
        text.setText(title);
        builder.setView(text);
        Dialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissProgressBar() {
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
