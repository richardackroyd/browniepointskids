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
    TextView addPoints;
    TextView removePoints;
    EditText pointsToChange;
    ClickHandler clickHandler;

    public PointsSummaryViewHolder(View itemView) {
        super(itemView);

        this.pointsAccountNameText = (TextView) itemView.findViewById(R.id.pointsAccountNameText);
        this.pointsAccountPointsText = (TextView) itemView.findViewById(R.id.pointsAccountPointsText);
        this.pointsAccountAvatarImage = (ImageView) itemView.findViewById(R.id.pointsAccountAvatarImage);
        this.addPoints = (TextView) itemView.findViewById(R.id.addPoint);
        this.removePoints = (TextView) itemView.findViewById(R.id.removePoint);
        this.pointsToChange = (EditText) itemView.findViewById(R.id.pointsToBeUpdated);

        addPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!pointsToChange.getText().toString().isEmpty()) {
                    //action: 1 = increase 0 = decrease
                    clickHandler.componentClicked(getAdapterPosition(), 1, Integer.parseInt(pointsToChange.getText().toString()));

                    pointsToChange.getText().clear();
                }

            }
        });

        removePoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!pointsToChange.getText().toString().isEmpty()) {
                    //action: 1 = increase 0 = decrease
                    clickHandler.componentClicked(getAdapterPosition(), 0, Integer.parseInt(pointsToChange.getText().toString()));

                    pointsToChange.getText().clear();
                }

            }
        });

    }

}
