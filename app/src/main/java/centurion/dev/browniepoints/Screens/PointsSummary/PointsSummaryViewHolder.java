package centurion.dev.browniepoints.Screens.PointsSummary;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import centurion.dev.browniepoints.R;
import centurion.dev.browniepoints.Util.ClickHandler;

/**
 * Created by rich on 23/12/2017.
 */

//Provides the model representation of a single card on the points summary screen

//public class PointsSummaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
public class PointsSummaryViewHolder extends RecyclerView.ViewHolder {

    TextView pointsAccountNameText;
    TextView pointsAccountPointsText;
    ImageView pointsAccountAvatarImage;

    public PointsSummaryViewHolder(View itemView) {
        super(itemView);

        this.pointsAccountNameText = (TextView) itemView.findViewById(R.id.pointsAccountNameText);
        this.pointsAccountPointsText = (TextView) itemView.findViewById(R.id.pointsAccountPointsText);
        this.pointsAccountAvatarImage = (ImageView) itemView.findViewById(R.id.pointsAccountAvatarImage);

    }

}
