package com.cesarvaliente.androirwearnotifications.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cesarvaliente.androirwearnotifications.R;
import com.cesarvaliente.androirwearnotifications.ui.fragments.notifications.VoiceNotificationFragment;
import com.cesarvaliente.androirwearnotifications.ui.utils.Constants;

/**
 * Created by cesar on 27/10/14.
 */
public class ResultActivity extends Activity {

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

        String voiceMessage = getVoiceMessage(getIntent());
        if (!TextUtils.isEmpty(voiceMessage)) {
            mTextView.setText(voiceMessage);
        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null && bundle.containsKey(Constants.RESULT_TEXT_EXTRA)) {
                String extra = bundle.getString(Constants.RESULT_TEXT_EXTRA);
                mTextView.setText(extra);
            }
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
    }

    /**
     * Gets the voice reply that we have been used in the {@link VoiceNotificationFragment}
     * (Voice notification example)
     * @param intent
     * @return
     */
    private String getVoiceMessage(Intent intent) {

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null && remoteInput.containsKey(Constants.EXTRA_VOICE_REPLY)) {
            return remoteInput.getCharSequence(Constants.EXTRA_VOICE_REPLY).toString();
        }
        return null;
    }
}
