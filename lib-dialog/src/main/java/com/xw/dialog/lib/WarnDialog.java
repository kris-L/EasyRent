package com.xw.dialog.lib;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WarnDialog {

    /**
     * 确认提交警告
     **/
    public static Dialog submitWarn(final Context context, Spanned wareDesc,
                                    final DialogInterface.OnClickListener onClickListener) {
        BaseDialog.Builder builder = new BaseDialog.Builder(context) {
            @Override
            protected View getCenterView(View centerView, ViewGroup container) {
                return centerView;
            }
        };
        View view = View.inflate(context, R.layout.lib_dialog__warn_dialog, null);
        ((TextView) view.findViewById(R.id.dialog_hint)).setText(wareDesc);
        builder.setCenterView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onClickListener.onClick(dialog, which);
                    }
                })
                .setNegativeButton("取消", null);
        builder.setTitle("温馨提示");
        return builder.create();
    }

    public static Dialog submitWarn(final Context context, String wareDesc,
                                    final DialogInterface.OnClickListener onClickListener) {
        return normal(context, "温馨提示", wareDesc, "确定", "取消", onClickListener);
    }

    /**
     * 提示登录对话框
     **/
    public static Dialog toLogin(final Activity context, final DialogInterface.OnClickListener onClickListener) {
        return normal(context, "温馨提示", "您现在尚未登录，马上登录？", "登录", "暂不登录", onClickListener);
    }

    /**
     * 提示登录对话框
     **/
    public static Dialog toLogin(final Activity context, final DialogInterface.OnClickListener onClickListener,
                                 final DialogInterface.OnClickListener onCancelListener) {
        return normal(context, "温馨提示", "您现在尚未登录，马上登录？", "登录", "暂不登录", onClickListener, onCancelListener);
    }

    /**
     * 确定退出登录警告
     **/
    public static Dialog logout(final Activity context, final DialogInterface.OnClickListener onClickListener) {
        return normal(context, "温馨提示", "确定退出登录？", "确定", "取消", onClickListener);
    }

    /**
     * 登录过期警告
     **/
    public static Dialog loginInvalid(final Activity context, final DialogInterface.OnClickListener onClickListener) {
        return normal(context, "温馨提示", "登录过期，请重新登录？", "确定", "取消", onClickListener);
    }

    /**
     * 网络数据加载失败
     **/
    public static Dialog networkError(final Activity context, String reason, final DialogInterface.OnClickListener onClickListener) {
        return normal(context, "温馨提示", reason, "重新加载", "返回", onClickListener, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.finish();
            }
        });
    }

    /**
     * 未连接设备警告对话框
     **/
    public static Dialog connectDeviceWarn(final Context context, final DialogInterface.OnClickListener onClickListener) {
        return normal(context, "提示", "当前未连接到车载设备，是否设置连接？", "去连接", "取消", onClickListener);
    }

    public static Dialog normal(final Context context, String title, CharSequence msg, String okText,
                                String cancelText, final DialogInterface.OnClickListener onClickListener) {
        return normal(context, title, msg, okText, cancelText, onClickListener, null);
    }

    /**
     * 确认通用警告
     **/
    public static Dialog normal(final Context context, String title, CharSequence msg, String okText,
                                String cancelText, final DialogInterface.OnClickListener onClickListener, final DialogInterface.OnClickListener onCancelListener) {
        BaseDialog.Builder builder = new BaseDialog.Builder(context) {
            @Override
            protected View getCenterView(View centerView, ViewGroup container) {
                return centerView;
            }
        };
        View view = View.inflate(context, R.layout.lib_dialog__warn_dialog, null);
        ((TextView) view.findViewById(R.id.dialog_hint)).setText(msg);
        builder.setCenterView(view);
        if (!TextUtils.isEmpty(okText)) {
            builder.setPositiveButton(okText, onClickListener);
        }
        if (!TextUtils.isEmpty(cancelText)) {
            builder.setNegativeButton(cancelText, onCancelListener);
        }
        builder.setTitle(title);
        return builder.create();
    }

    public static Dialog cancelReserveOrder(Context context, DialogInterface.OnClickListener onClickListener) {
        return normal(context, "温馨提示", "确认取消预约？", "确定", "取消", onClickListener);
    }

    public static Dialog leftTicketTip(Context context){
        return normal(context, "温馨提示", context.getString(R.string.left_ticket_tip), null, "确定", null);
    }

    /**
     * 退出编辑警告
     **/
    public static Dialog exitEdit(final Activity context) {
        return normal(context, "温馨提示", "确认退出编辑？", "确定", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.finish();
            }
        });
    }

    public static Dialog hintDialog(Context context, String hint) {
        return normal(context, "温馨提示", hint, "确定", "取消", null);
    }

    public static Dialog hintDialog(Context context, String hint, final DialogInterface.OnClickListener onClickListener) {
        return normal(context, "温馨提示", hint, "确定", "", onClickListener);
    }

    public static Dialog exitAction(final Activity activity,String hint,String exitBtnHint){
        return normal(activity, "温馨提示", hint, null, exitBtnHint, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();
            }
        });
    }

    public static Dialog withCustomCenterView(Context context, View centerView, String title, String okText,
                                              String cancelText, final DialogInterface.OnClickListener onClickListener,
                                              final DialogInterface.OnClickListener onCancelListener) {
        BaseDialog.Builder builder = new BaseDialog.Builder(context) {
            @Override
            protected View getCenterView(View centerView, ViewGroup container) {
                return centerView;
            }
        };
        builder.setCenterView(centerView);
        if (!TextUtils.isEmpty(okText)) {
            builder.setPositiveButton(okText, onClickListener);
        }
        if (!TextUtils.isEmpty(cancelText)) {
            builder.setNegativeButton(cancelText, onCancelListener);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        return builder.create();
    }

    public static Dialog withCustomCenterView(Context context, View centerView,
                                              String title,
                                              String cancelText,
                                              String neutralText,
                                              int neutralBgResId,
                                              String okText,
                                              int okBgResId,
                                              final DialogInterface.OnClickListener onCancelListener,
                                              final DialogInterface.OnClickListener onNeutralListener,
                                              final DialogInterface.OnClickListener onClickListener) {
        BaseDialog.Builder builder = new BaseDialog.Builder(context) {
            @Override
            protected View getCenterView(View centerView, ViewGroup container) {
                return centerView;
            }
        };
        builder.setCenterView(centerView);
        if (!TextUtils.isEmpty(okText)) {
            builder.setPositiveButton(okText, okBgResId, onClickListener);
        }
        if (!TextUtils.isEmpty(cancelText)) {
            builder.setNegativeButton(cancelText, onCancelListener);
        }
        if (!TextUtils.isEmpty(neutralText)) {
            builder.setNeutralButton(neutralText, neutralBgResId, onNeutralListener);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        return builder.create();
    }
}
