//package com.example.sameapp;
//
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//private class SentMessageHolder extends RecyclerView.ViewHolder {
//    TextView messageText, timeText;
//
//    SentMessageHolder(View itemView) {
//        super(itemView);
//
//        messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
//        timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
//    }
//
//    void bind(UserMessage message) {
//        messageText.setText(message.getMessage());
//
//        // Format the stored timestamp into a readable String using method.
//        timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
//    }
//}
