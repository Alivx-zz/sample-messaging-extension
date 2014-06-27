package com.pushbullet.android.extension.messaging.sample;

import android.util.Log;
import com.pushbullet.android.extension.MessagingExtension;

public class SampleMessagingExtension extends MessagingExtension {
    private static final String TAG = "SampleMessagingExtension";

    @Override
    protected void onMessageReceived(final String conversationIden, final String message) {
        Log.i(TAG, "Pushbullet MessagingExtension: onMessageReceived(" + conversationIden + ", " + message + ")");
    }

    @Override
    protected void onConversationDismissed(final String conversationIden) {
        Log.i(TAG, "Pushbullet MessagingExtension: onConversationDismissed(" + conversationIden + ")");

        LaunchActivity.sMessages.remove(conversationIden);

        LaunchActivity.updateNotification(this, LaunchActivity.sMessages.values());
    }
}
