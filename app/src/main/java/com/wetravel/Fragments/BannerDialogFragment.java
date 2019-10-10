package com.wetravel.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wetravel.R;
import com.wetravel.Utils.Utility;

public class BannerDialogFragment extends DialogFragment {

	private static BannerDialogFragment fragment;
	private Context context;
	private String path;

	public static BannerDialogFragment getInstance(Context context, String path) {
		if (fragment == null) {
			fragment = new BannerDialogFragment();
		}
		fragment.context = context;
		fragment.path = path;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_banner_show, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Dialog dialog = getDialog();
		Window window = getDialog().getWindow();
		dialog.setContentView(R.layout.dialog_banner_show);
		dialog.getWindow().setBackgroundDrawable(null);

		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		int sWidth = (int) (displayMetrics.widthPixels*1.00);
		int sHeight = (int) (displayMetrics.heightPixels*1.00);

		window.setLayout(sWidth,sHeight);
		ImageView imgBanner = dialog.findViewById(R.id.imgBanner);
		Bitmap bitmap = Utility.drawableToBitmap(context,(Utility.getDrawableFromAssets(context, path)));
		imgBanner.setImageBitmap(bitmap);

		ImageView imgCancel = dialog.findViewById(R.id.imgCancel);
		imgCancel.setPadding(Utility.deviceWidth*10/100, Utility.deviceWidth*11/100, Utility.deviceWidth*10/100, Utility.deviceWidth*10/100);
		imgCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		RelativeLayout rlItem = dialog.findViewById(R.id.rlItem);
		rlItem.setPadding(Utility.deviceWidth*5/100,0, Utility.deviceWidth*5/100, Utility.deviceWidth*10/100);
		dialog.show();
	}
}
