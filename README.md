1)Nom

Fonction Favoris et Détail



2)Description

Cette fonction permet d'ajouter des utilisateurs en favoris et de voir des informations plus détaillées à leur sujet.



3)Usage

Pour voir un utilisateur, il suffit de cliquer sur son nom, une fenetre apparaitra avec toutes les informations à son sujet, il suffit ensuite d'appuyer sur le bouton étoile pour l'ajouter en favoris.
Pour voir les favoris actuels, il suffit d'aller sur l'onglet "favoris" depuis la liste principale.



4)Ajout

-Pour l'ajout de cete fonctionnalité, j'ai ajouté un Listener sur les noms des "Neighbours" afin de lancer l'Intent qui lance la DetailActivity.

-J'ai crée la liste des Favoris dans le NeighbourApiService avec les fonctions d'ajouts et de suppression de favoris.

-J'ai également relié depuis le manifest la classe DetailActivity à la classe ListNeighbourActivity afin de mettre le bouton retour sur la toolbar.

Cette dernière reprend toutes les informations du Neighbour cliqué grâce à un putExtra après l'Intent et à un getParcelableExtra dans la DetailActivity avec pour donné le voisin cliqué.
La classe model "Neighbour" à étée passé en Parcelable pour pouvoir éxécuter cette fonction.

-Dans la DetailActivity, en plus des informations récoltées, nous avons une donné nommé "Fav" qui est un int qui est défini au lancement de l'activité en fonction de si le neighbour sélectionné
est déjà en favoris ou non grace à un contains. Le résultat permettra de changer l'effet du bouton afin d'éviter un ajout succéssifs du même neighbour.

-La liste des favoris quand à elle est affichée dans un fragment nommé "FavoriteFragment". Elle est affichée dans une recyclerview qui a son propre adaptateur nommé
"FavoriteRecyclerViewAdapter" afin de pouvoir supprimer un favoris depuis cette page grâce aux boutons. Il est possible de retourner sur les détails d'un neighbour depuis la page favoris.



5)Execution et compilation

-Le lancement de l'activité DetailActivity se fait via le Listener dans l'adapter MyNeighbourRecyclerViewAdapter, avec le putExtra pour transmettre les informations:

"holder.mNeighbourName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(KEY_NEIGHBOUR, neighbour);
                v.getContext().startActivity(intent);
            }
    });"


-La DetailActivity va ensuite récupérer les informations :

"Intent intent=getIntent();
 mNeighbour=intent.getParcelableExtra(KEY_NEIGHBOUR);"

Pour ensuite les utiliser afin de mettre à jour les informations affichées:

"mNet.setText(mNeighbour.getAvatarUrl());
        mWhiteName=mNameHeader;
        mNameHeader.setText(mNeighbour.getName());
        mInfo.setText(mNeighbour.getAboutMe());
        mPhone.setText(mNeighbour.getPhoneNumber());
        mAddress.setText(mNeighbour.getAddress());
        Glide.with(mImageAvatar.getContext())
                .load(mNeighbour.getAvatarUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(mImageAvatar);"

-Pour le bouton des favoris nous avons la valeur "Fav" qui aura sa valeur attribuée et le bouton aura un état en fonction de la présence(ou non) de ce neighbour
dans la liste des favoris:


"if (mApiService.getFavorite().contains(mNeighbour)==true){

            fav=1;

            mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_white_24dp));
        }

        else {fav=0;
            mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_white_24dp));
            }

  mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
            if (fav==1)

            {
                mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_white_24dp));
                fav=0;
                mApiService.deleteFavorite(mNeighbour);


            }


                else {

            mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_white_24dp));
            fav=1;
            mApiService.addFavorite(mNeighbour);




        }"

-Pour la liste des favoris, elle est appelée dans le NeighbourApiService grâce à la méthode:

"mApiService = DI.getNeighbourApiService();"

Elle est affichée dans le recyclerview via le fragment "FavoriteFragment".

Ce fragment contient les données nécéssaires pour utiliser EventBus:

"    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
"

Elle utilise l'adapter "FavoriteNeighbourRecyclerViewAdapter" qui est très semblable à "MyNeighbourRecyclerViewAdapter" en dehors de ces lignes:

"        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new RemoveNeighbourEvent(neighbour));
            }
        });"

Cette fonction permet de supprimer l'utilisateur des favoris au lieu de le supprimer de la liste principale.
Nous auront ainsi une classe "RemoveNeighbourEvent" qui sera appelée pour cette fonction et qui elle-même redigirera vers le fragment favoris:

"@Subscribe
    public void removeNeighbour(RemoveNeighbourEvent event){

        mApiService.deleteFavorite(event.neighbour);
        initList();

    }"


-Pour terminer, le pager "ListNeighbourPagerAdapter" a sa fonction "switch" pour lancer convenablement les deux fragments en affichage:

"public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NeighbourFragment.newInstance();
            case 1:
                return FavoriteFragment.newInstance();





            default:
                return null;
        }
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }"
