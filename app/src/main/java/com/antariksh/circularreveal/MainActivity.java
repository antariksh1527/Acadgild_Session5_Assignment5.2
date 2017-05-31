package com.antariksh.circularreveal;

import android.animation.Animator;
        import android.animation.AnimatorListenerAdapter;
        import android.annotation.TargetApi;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Build;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewAnimationUtils;
        import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Layout passing
        Button btn= (Button)findViewById(R.id.click);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });  // Setting onClick Listener
    }
    private void showDialog(){           //   Creating void method for dialog
        final View dialogView = View.inflate(this,R.layout.dailog_file, null);
        final Dialog alert = new Dialog(this);
        alert.setContentView(dialogView);
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Setting Background as transparent
// final AlertDialog dialogsubmitted = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP)
                    alert.show();
                else
                    revealShow(dialogView, true, null);
            }
        });
        dialogView.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP)
                    alert.dismiss();
                else
                    revealShow(dialogView, false, alert);
            }
        });
        alert.show();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void revealShow(View rootView, boolean reveal, final Dialog dialog) {
        final View view = rootView.findViewById(R.id.reveal);
        int w = view.getWidth();
        int h = view.getHeight();
        float maxRadius = (float) Math.sqrt(w * w / 4 + h * h / 4);
        if (reveal) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view,
                    w / 2, h / 2, 0, maxRadius);
            view.setVisibility(View.VISIBLE);
            revealAnimator.start();
        } else {
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, w / 2, h / 2, maxRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        }
    }
}
