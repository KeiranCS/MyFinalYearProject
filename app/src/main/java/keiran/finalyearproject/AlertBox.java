package keiran.finalyearproject;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Keiran on 02/03/2016.
 */
public class AlertBox {

    public void showPopup(Context context, String title, String message)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_box);
        dialog.setTitle(title);

        TextView tv_message = (TextView) dialog.findViewById(R.id.message);
        tv_message.setText(message);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
