package com.xw.dialog.lib;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class BaseDialog extends Dialog {

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    public BaseDialog(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private boolean cancelable = false;
        private OnCancelListener cancelListener;
        private int theme = R.style.commonDialog;
        private CharSequence positionText;
        private CharSequence negativeText;
        private CharSequence neutralText;
        private OnClickListener positiveListener;
        private OnClickListener negativeListener;
        private OnClickListener neutralListener;
        private View centerView;
        private CharSequence title;
        private LayoutParams centerLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        private Map<String, View> viewMap;
        private int positionBgResId;
        private int neutralBgResId;

        public Builder(Context context) {
            this.context = context;
            viewMap = new HashMap<String, View>();
        }

        public Context getContext() {
            return context;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCancelListener(OnCancelListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }

        public Builder setTheme(int theme) {
            this.theme = theme;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int textId) {
            this.title = getContext().getString(textId);
            return this;
        }

        public Builder setCenterView(View centerView) {
            this.centerView = centerView;
            return this;
        }

        public Builder setCenterView(int resId) {
            this.centerView = View.inflate(getContext(), resId, null);
            return this;
        }

        public Builder setPositiveButton(int textId, OnClickListener listener) {
            this.positionText = getContext().getString(textId);
            this.positiveListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, OnClickListener listener) {
            this.positionText = text;
            this.positiveListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId, OnClickListener listener) {
            this.negativeText = getContext().getString(textId);
            ;
            this.negativeListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, OnClickListener listener) {
            this.negativeText = text;
            this.negativeListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence text, OnClickListener listener) {
            this.neutralText = text;
            this.neutralListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence text, int neutralBgResId, OnClickListener listener) {
            this.neutralText = text;
            this.neutralBgResId = neutralBgResId;
            this.neutralListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, int bgResId, OnClickListener listener) {
            this.positionText = text;
            this.positiveListener = listener;
            this.positionBgResId = bgResId;
            return this;
        }

        public Dialog create() {
            final Dialog dialog = new Dialog(getContext(), theme);
            dialog.setCancelable(cancelable);
            dialog.setOnCancelListener(cancelListener);
            View view = View.inflate(getContext(), R.layout.lib_dialog__common_base_dialog, null);
            dialog.setContentView(view);
            TextView tv_title = (TextView) view.findViewById(R.id.lib_dialog_tv_title);
            if (!TextUtils.isEmpty(title)) {
                tv_title.setText(title);
            } else {
                view.findViewById(R.id.lay_title).setVisibility(View.GONE);
            }
            ImageView iv_cancel = (ImageView) view.findViewById(R.id.lib_dialog_iv_cancel);
            if (cancelable) {
                iv_cancel.setVisibility(View.VISIBLE);
                iv_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        if (cancelListener != null) {
                            cancelListener.onCancel(dialog);
                        }
                    }
                });
            } else {
                iv_cancel.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(positionText) && TextUtils.isEmpty(negativeText) && TextUtils.isEmpty(neutralText)) {
                view.findViewById(R.id.lay_bottom).setVisibility(View.GONE);
            } else {
                View bottomView = view.findViewById(R.id.lay_bottom);
                bottomView.setVisibility(View.VISIBLE);
                Button btn_negative = (Button) bottomView.findViewById(R.id.btn_negative);
                Button btn_neutral = (Button) bottomView.findViewById(R.id.btn_neutral);
                Button btn_positive = (Button) bottomView.findViewById(R.id.btn_positive);
                if (TextUtils.isEmpty(positionText)) {
                    btn_positive.setVisibility(View.GONE);
                } else {
                    btn_positive.setVisibility(View.VISIBLE);
                    btn_positive.setText(positionText);
                    btn_positive.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            if (positiveListener != null) {
                                positiveListener.onClick(dialog, BUTTON_POSITIVE);
                            }
                        }
                    });
                }

                if (TextUtils.isEmpty(negativeText)) {
                    btn_negative.setVisibility(View.GONE);
                } else {
                    btn_negative.setVisibility(View.VISIBLE);
                    btn_negative.setText(negativeText);
                    btn_negative.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            if (negativeListener != null) {
                                negativeListener.onClick(dialog, BUTTON_NEGATIVE);
                            }
                        }
                    });
                }
                if (TextUtils.isEmpty(neutralText)) {
                    btn_neutral.setVisibility(View.GONE);
                } else {
                    btn_neutral.setVisibility(View.VISIBLE);
                    btn_neutral.setText(neutralText);
                    btn_neutral.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            if (neutralListener != null) {
                                neutralListener.onClick(dialog, BUTTON_NEUTRAL);
                            }
                        }
                    });
                }
                if (!TextUtils.isEmpty(positionText) && !TextUtils.isEmpty(negativeText)) {

                    if (positionBgResId != 0) {
                        btn_positive.setBackgroundResource(positionBgResId);
                    } else {
                        btn_positive.setBackgroundResource(R.drawable.common_selector_dialog_btn_position);
                    }
                    btn_negative.setBackgroundResource(R.drawable.common_selector_dialog_press_white);
                } else {
                    btn_negative.setBackgroundResource(R.drawable.common_selector_dialog_press_one_white);
                    if (positionBgResId != 0) {
                        btn_positive.setBackgroundResource(positionBgResId);
                    } else {
                        btn_positive.setBackgroundResource(R.drawable.common_selector_dialog_btn_position_single);
                    }
                }

                if (neutralBgResId != 0) {
                    btn_neutral.setBackgroundResource(neutralBgResId);
                }
            }
            ViewGroup centerLay = (ViewGroup) view.findViewById(R.id.lay_center);
            centerView = getCenterView(centerView, centerLay);
            if (centerView != null) {
                centerView.setLayoutParams(centerLP);
                centerLay.addView(centerView);
            }
            initDialogSize(dialog);
            return dialog;
        }

        private void initDialogSize(Dialog dialog) {
            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            android.view.WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = (int) (metrics.widthPixels - 10 * 2 * metrics.density);
            params.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        }

        protected View getCenterView(View centerView, ViewGroup container) {
            return centerView;
        }

        public void saveView(String key, View view) {
            viewMap.put(key, view);
        }

        public Map getViewMap() {
            return viewMap;
        }

        public View getView(String key) {
            return viewMap.get(key);
        }
    }
}
