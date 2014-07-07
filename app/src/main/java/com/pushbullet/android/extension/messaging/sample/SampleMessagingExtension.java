/*
 * Copyright 2014 PushBullet Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
