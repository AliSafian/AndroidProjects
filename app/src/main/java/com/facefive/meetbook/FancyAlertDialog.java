package com.facefive.meetbook;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shashank Singhal on 03/01/2018.
 */

public class FancyAlertDialog {

    private String title,fragmentdate,fragmentstarttime,fragmentendtime,positiveBtnText,negativeBtnText;
    private Activity activity;
    private int icon;
    private Icon visibility;
    private Animation animation;
    private FancyAlertDialogListener pListener,nListener;
    private int pBtnColor,nBtnColor,bgColor;
    private boolean cancel;



    private FancyAlertDialog(Builder builder){
        this.title=builder.title;
        this.fragmentdate=builder.fragmentdate;
        this.fragmentstarttime=builder.fragmentstarttime;
        this.fragmentendtime=builder.fragmentendtime;
        this.activity=builder.activity;
        this.icon=builder.icon;
        this.animation=builder.animation;
        this.visibility=builder.visibility;
        this.pListener=builder.pListener;
        this.nListener=builder.nListener;
        this.positiveBtnText=builder.positiveBtnText;
        this.negativeBtnText=builder.negativeBtnText;
        this.pBtnColor=builder.pBtnColor;
        this.nBtnColor=builder.nBtnColor;
        this.bgColor=builder.bgColor;
        this.cancel=builder.cancel;
    }


    public static class Builder{
        private String title,message,positiveBtnText,negativeBtnText,fragmentdate,fragmentstarttime,fragmentendtime;
        private Activity activity;
        private int icon;
        private Icon visibility;
        private Animation animation;
        private FancyAlertDialogListener pListener,nListener;
        private int pBtnColor,nBtnColor,bgColor;
        private boolean cancel;

        public Builder(Activity activity){
            this.activity=activity;
        }
        public Builder setTitle(String title){
            this.title=title;
            return this;
        }

        public Builder setBackgroundColor(int bgColor){
            this.bgColor=bgColor;
            return this;
        }

        public Builder setDate(String date){
            this.fragmentdate=date;
            return this;
        }
        public Builder setStartTime(String stime){
            this.fragmentstarttime=stime;
            return this;
        }
        public Builder setEndTime(String etime){
            this.fragmentendtime=etime;
            return this;
        }

        public Builder setPositiveBtnText(String positiveBtnText){
            this.positiveBtnText=positiveBtnText;
            return this;
        }

        public Builder setPositiveBtnBackground(int pBtnColor){
            this.pBtnColor=pBtnColor;
            return this;
        }

        public Builder setNegativeBtnText(String negativeBtnText){
            this.negativeBtnText=negativeBtnText;
            return this;
        }

        public Builder setNegativeBtnBackground(int nBtnColor){
            this.nBtnColor=nBtnColor;
            return this;
        }


        //setIcon
        public Builder setIcon(int icon, Icon visibility){
            this.icon=icon;
            this.visibility=visibility;
            return this;
        }

        public Builder setAnimation(Animation animation){
            this.animation=animation;
            return this;
        }

        //set Positive listener
        public Builder OnPositiveClicked(FancyAlertDialogListener pListener){
            this.pListener=pListener;
            return this;
        }

        //set Negative listener
        public Builder OnNegativeClicked(FancyAlertDialogListener nListener){
            this.nListener=nListener;
            return this;
        }

        public Builder isCancellable(boolean cancel){
            this.cancel=cancel;
            return this;
        }

        public FancyAlertDialog build(){
            TextView date1,stime1,etime1,title1;
            ImageView iconImg;
            Button nBtn,pBtn;
            View view;
            final Dialog dialog;
            if(animation==Animation.POP)
            dialog=new Dialog(activity,R.style.PopTheme);
            else if(animation==Animation.SIDE)
            dialog=new Dialog(activity,R.style.SideTheme);
            else if(animation==Animation.SLIDE)
            dialog=new Dialog(activity,R.style.SlideTheme);
            else
            dialog=new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(cancel);
            dialog.setContentView(R.layout.fancyalertdialog);

            //getting resources
            view=(View)dialog.findViewById(R.id.background);
            title1= (TextView) dialog.findViewById(R.id.title);
            date1=(TextView)dialog.findViewById(R.id.fragmentdate);
            stime1=(TextView)dialog.findViewById(R.id.fragmentstarttime);
            etime1=(TextView)dialog.findViewById(R.id.fragment_end_time);
            iconImg=(ImageView)dialog.findViewById(R.id.icon);
            nBtn=(Button)dialog.findViewById(R.id.negativeBtn);
            pBtn=(Button)dialog.findViewById(R.id.positiveBtn);
            title1.setText(title);
            date1.setText(fragmentdate);
            stime1.setText(fragmentstarttime);
            etime1.setText(fragmentendtime);
            if(positiveBtnText!=null)
            pBtn.setText(positiveBtnText);
            if(pBtnColor!=0)
            { GradientDrawable bgShape = (GradientDrawable)pBtn.getBackground();
              bgShape.setColor(pBtnColor);
            }
            if(nBtnColor!=0)
            { GradientDrawable bgShape = (GradientDrawable)nBtn.getBackground();
              bgShape.setColor(nBtnColor);
            }
            if(negativeBtnText!=null)
            nBtn.setText(negativeBtnText);
            iconImg.setImageResource(icon);
            if(visibility==Icon.Visible)
            iconImg.setVisibility(View.VISIBLE);
            else
            iconImg.setVisibility(View.GONE);
            if(bgColor!=0)
            view.setBackgroundColor(bgColor);
            if(pListener!=null) {
                pBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pListener.OnClick();
                        dialog.dismiss();
                    }
                });
            }
            else{
                pBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }

                });
            }

            if(nListener!=null){
                nBtn.setVisibility(View.VISIBLE);
                nBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nListener.OnClick();

                        dialog.dismiss();
                    }
                });
            }


            dialog.show();

            return new FancyAlertDialog(this);

        }
    }

}
