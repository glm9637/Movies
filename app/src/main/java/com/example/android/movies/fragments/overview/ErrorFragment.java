package com.example.android.movies.fragments.overview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.R;
import com.example.android.movies.utils.Constants;
import com.squareup.picasso.Picasso;

/**
 * Erzeugt von M. Fengels am 19.03.2018.
 */

/**
 * A Fragment to display a error Message
 */
public class ErrorFragment extends Fragment {
	
	private int mErrorType = Constants.ERROR_SOMETHING_WENT_WRONG;
	private Button mRetryButton;
	private View.OnClickListener mRetryClickListener;
	
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (getArguments() != null) {
			mErrorType = getArguments().getInt(Constants.ERROR_TYPE);
		}
		
		View rootView = inflater.inflate(R.layout.error_fragment, container, false);
		TextView titleText = rootView.findViewById(R.id.title_text);
		TextView descriptionText = rootView.findViewById(R.id.description_text);
		ImageView errorIcon = rootView.findViewById(R.id.error_icon);
		mRetryButton = rootView.findViewById(R.id.btn_retry);
		
		switch (mErrorType) {
			case Constants.ERROR_SOMETHING_WENT_WRONG:
				titleText.setText(R.string.error_unknown);
				descriptionText.setText(R.string.error_unknown_description);
				Picasso.with(getContext()).load(R.drawable.ic_error_outline_white_48dp).into(errorIcon);
				break;
			case Constants.ERROR_NO_CONNECTION:
				titleText.setText(R.string.error_no_connection);
				descriptionText.setText(R.string.error_no_connection_description);
				Picasso.with(getContext()).load(R.drawable.ic_cloud_off_white_48dp).into(errorIcon);
				break;
			case Constants.ERROR_NO_FAVORITES:
				titleText.setText("No Favorites");
				titleText.setText("You don't have any favorites yet. Mark some Movies as favorite and return back here");
				Picasso.with(getContext()).load(R.drawable.ic_not_interested_white_48dp).into(errorIcon);
				mRetryButton.setVisibility(View.INVISIBLE);
		}
		
		if (mRetryClickListener != null) {
			mRetryButton.setOnClickListener(mRetryClickListener);
		}
		
		return rootView;
	}
	
	public void setOnRetryClickListener(View.OnClickListener onRetryClickListener) {
		mRetryClickListener = onRetryClickListener;
		if (mRetryButton != null)
			mRetryButton.setOnClickListener(onRetryClickListener);
	}
	
}
