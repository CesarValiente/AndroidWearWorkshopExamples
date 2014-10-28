package com.cesarvaliente.androirwearnotifications.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cesarvaliente.androirwearnotifications.R;

/**
 * Created by cesar on 27/10/14.
 */
public class OpenActivity extends Activity {

    public static final String OPEN_TEXT_EXTRA = "open_text_extra";

    @InjectView(R.id.open_text)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.open_activity);

        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(OPEN_TEXT_EXTRA)) {
            String extra = bundle.getString(OPEN_TEXT_EXTRA);
            mTextView.setText(extra);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
    }
}
