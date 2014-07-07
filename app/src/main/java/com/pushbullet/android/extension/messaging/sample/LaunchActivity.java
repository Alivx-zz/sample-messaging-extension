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

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import com.pushbullet.android.extension.MessagingExtension;

import java.util.*;

public class LaunchActivity extends Activity {

    // Fakes the SMS provider for this sample, using a static like this is actually a bad idea but keeps things simple
    public static final Map<String, TextMessage> sMessages = new HashMap<String, TextMessage>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Simulate the arrival of a couple of text messages

        final TextMessage message0 = new TextMessage("John Doe", "A sample message at " + System.currentTimeMillis());
        final TextMessage message1 = new TextMessage("Jane Person", "Another message at " + System.currentTimeMillis());

        final List<TextMessage> messages = new ArrayList<TextMessage>(2);
        messages.add(message0);
        messages.add(message1);

        updateNotification(this, messages);

        for (int i = 0; i < messages.size(); i++) {
            final TextMessage message = messages.get(i);
            final String conversationIden = message.sender;

            sMessages.put(conversationIden, message);

            MessagingExtension.mirrorMessage(this, conversationIden, message.sender, message.message,
                                             BitmapFactory.decodeResource(this.getResources(), android.R.drawable.ic_dialog_alert), null, 0);
        }
    }

    public static void updateNotification(final Context context, final Collection<TextMessage> messages) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (messages == null || messages.size() == 0) {
            notificationManager.cancel(0);
            return;
        }

        final Notification.Builder builder = new Notification.Builder(context)
            .setSmallIcon(android.R.drawable.ic_delete)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .setContentTitle(messages.size() + " New Messages")
            .setContentText(TextUtils.join(", ", messages));

        final Notification.InboxStyle style = new Notification.InboxStyle();

        for (final TextMessage message : messages) {
            style.addLine(message.sender + " - " + message.message);
        }

        builder.setStyle(style);

        notificationManager.notify(0, builder.build());
    }

    public static class TextMessage {
        public final String sender, message;

        public TextMessage(final String sender, final String message) {
            this.sender = sender;
            this.message = message;
        }

        @Override
        public String toString() {
            return this.sender;
        }
    }
}
