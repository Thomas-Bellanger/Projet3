package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {
    public int fav;
    @BindView(R.id.buttonFav)
    ImageButton mImageButton;
    @BindView(R.id.addressDetail)
    TextView mAddress;
    @BindView(R.id.nameHeader)
    TextView mNameHeader;
    @BindView(R.id.imageViewAvatar)
    ImageView mImageAvatar;
    @BindView(R.id.whiteName)
    TextView mWhiteName = mNameHeader;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.net)
    TextView mNet;
    @BindView(R.id.info)
    TextView mInfo;
    Neighbour mNeighbour;
    private NeighbourApiService mApiService;
    public static final String KEY_NEIGHBOUR = "neighbour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();

        Intent intent = getIntent();


/**mise en place des informations*/

        mNeighbour = intent.getParcelableExtra(KEY_NEIGHBOUR);
        mNet.setText(mNeighbour.getAvatarUrl());
        mNameHeader.setText(mNeighbour.getName());
        mWhiteName.setText(mNeighbour.getName());
        mInfo.setText(mNeighbour.getAboutMe());
        mPhone.setText(mNeighbour.getPhoneNumber());
        mAddress.setText(mNeighbour.getAddress());
        Glide.with(mImageAvatar.getContext())
                .load(mNeighbour.getAvatarUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(mImageAvatar);


        if (mApiService.getFavorite().contains(mNeighbour) == true) {
            fav = 1;
            mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_white_24dp));
        } else {
            fav = 0;
            mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_white_24dp));
        }


        /**bouton d'ajout aux favoris*/


        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav == 1) {
                    mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_white_24dp));
                    fav = 0;
                    mApiService.deleteFavorite(mNeighbour);


                } else {
                    mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_white_24dp));
                    fav = 1;
                    mApiService.addFavorite(mNeighbour);
                }
            }
        });
    }
}






