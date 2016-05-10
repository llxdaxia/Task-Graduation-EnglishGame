package cn.zhu.cainiao.module.adaper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.zhu.cainiao.R;
import cn.zhu.cainiao.config.Config;
import cn.zhu.cainiao.model.AccountModel;
import cn.zhu.cainiao.model.bean.User;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> {

    private User user;
    private boolean isPassLevleNum;
    private Context mContext;
    private Class activity;

    public StarAdapter(boolean isPassLevleNum, Context context, Class toActivity) {
        user = AccountModel.getInstance().getAccount();
        this.isPassLevleNum = isPassLevleNum;
        this.mContext = context;
        this.activity = toActivity;
    }

    @Override
    public StarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StarViewHolder(parent, R.layout.item_star);
    }

    @Override
    public void onBindViewHolder(StarViewHolder holder, final int position) {

        //给每个关卡设置一个监听跳转
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, activity);
                intent.putExtra(Config.POSITION, position + 1);
                intent.putExtra(Config.IS_UPDATE_LEVEL,false);
                mContext.startActivity(intent);
            }
        });


        if (isPassLevleNum) {   //闯关的情况
            holder.setData(user.getPassLevelNum(), position + 1, mContext);
        } else {    //学习的情况
            holder.setData(user.getStudyLevelNum(), position + 1, mContext);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class StarViewHolder extends RecyclerView.ViewHolder {

        private ImageView status;
        private TextView levelNum;

        public StarViewHolder(View itemView) {
            super(itemView);
        }

        public StarViewHolder(ViewGroup parent, int resId) {
            super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
            levelNum = (TextView) itemView.findViewById(R.id.level_num);
            status = (ImageView) itemView.findViewById(R.id.status);
        }

        public void setData(int passLevelNum, int num, final Context context) {
            levelNum.setText(num + "");
            if (passLevelNum >= num) {
                status.setImageResource(R.drawable.ic_star_pass);
            } else {
                itemView.setEnabled(false);
            }
        }

    }
}

