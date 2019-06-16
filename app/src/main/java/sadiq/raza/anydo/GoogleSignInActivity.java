package sadiq.raza.anydo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.InputStream;
import java.net.URL;

public class GoogleSignInActivity extends BaseActivity implements View.OnClickListener{
	private static final String TAG = "GoogleSignInActivity";
	private static final int RC_SIGN_IN = 9001;
	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;
	private GoogleApiClient mGoogleApiClient;
	private ImageView mImageView;
	private TextView mTextViewProfile;
	private ViewPager viewPager;
	private MyViewPagerAdapter myViewPagerAdapter;
	private Button btnSkip, btnNext;
	private int[] layouts;
	PrefManager prefManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= 23) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}

		setContentView(R.layout.activity_welcome);
		prefManager=new PrefManager(this);
		if(!prefManager.isFirstTimeLaunch()) {
			launchHomeScreen();
			finish();
		}

		viewPager = (ViewPager) findViewById(R.id.view_pager);
		//dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
		btnSkip = (Button) findViewById(R.id.btn_skip);
		btnNext = (Button) findViewById(R.id.btn_next);


		// layouts of all welcome sliders
		// add few more layouts if you want
		layouts = new int[]{
				R.layout.welcome_slide_1,
				R.layout.welcome_slide_2,
				R.layout.welcome_slide_3,
		};

		// adding bottom dots
//        addBottomDots(2);

		// making notification bar transparent
		changeStatusBarColor();

		myViewPagerAdapter = new MyViewPagerAdapter();
		viewPager.setAdapter(myViewPagerAdapter);
		viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

		btnSkip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				launchHomeScreen();
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// checking for last page
				// if last page home screen will be launched
				int current = getItem(+1);
				if (current < layouts.length) {
					// move to next screen
					viewPager.setCurrentItem(current);
				} else {
//                    startActivity(new Intent(GoogleSignInActivity.this,GoogleSignInPage.class));
					launchHomeScreen();
					Log.e("abc","delta");
					//signIn();
				}
			}
		});

//
	}

	ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			if (position == layouts.length - 1) {
				// last page. make button text to GOT IT
				btnNext.setText("Start");
				btnSkip.setVisibility(View.GONE);
			} else {
				// still pages are left
				btnNext.setText("Next");
				btnSkip.setVisibility(View.GONE);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private int getItem(int i) {
		return viewPager.getCurrentItem() + i;
	}

	private void launchHomeScreen() {
		prefManager.setFirstTimeLaunch(false);
		startActivity(new Intent(GoogleSignInActivity.this, GoogleSignInPage.class));
		finish();
	}

	private void changeStatusBarColor() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		}
	}

	@Override
	public void onClick(View view) {
//		switch (view.getId()) {
//			case R.id.sign_in_button:
//				signIn();
//				break;
//			case R.id.sign_out_button:
//				signOut();
//				break;
//			case R.id.disconnect_button:
//				revokeAccess();
//				break;
//		}
	}


	public class MyViewPagerAdapter extends PagerAdapter {
		private LayoutInflater layoutInflater;

		public MyViewPagerAdapter() {
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View view = layoutInflater.inflate(layouts[position], container, false);
			container.addView(view);

			return view;
		}

		@Override
		public int getCount() {
			return layouts.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}


		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			container.removeView(view);
		}
	}
}