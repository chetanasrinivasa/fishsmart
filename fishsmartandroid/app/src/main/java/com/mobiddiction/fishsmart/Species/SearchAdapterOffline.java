package com.mobiddiction.fishsmart.Species;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mobiddiction.fishsmart.dao.AllSpecies;

import java.util.List;

public class SearchAdapterOffline extends  BaseSearchAdapterOffline{

    List<AllSpecies> allSpecies;
    public SearchAdapterOffline(Context context,List<AllSpecies> allSpecies){
        this.context = context;
        this.allSpecies = allSpecies;
    }

    @Override
    public int getCount() {
        return allSpecies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(convertView, parent);
        AllSpecies allSpeciesNotList = allSpecies.get(position);
        populateSpecies(parent.getContext(), allSpeciesNotList);
        return view;
    }

//    Context mContext;
//    int layoutResourceId;
//    List<AllSpecies> data = new ArrayList<AllSpecies>();
//
//    public SearchAdapterOffline(Context mContext, int layoutResourceId, List<AllSpecies> data) {
//        super(mContext, layoutResourceId);
//        this.layoutResourceId = layoutResourceId;
//        this.mContext = mContext;
//        this.data = data;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        /*
//         * The convertView argument is essentially a "ScrapView" as described is Lucas post
//         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
//         * It will have a non-null value when ListView is asking you recycle the row layout.
//         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
//         */
//        if(convertView==null){
//            // inflate the layout
//            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
//            convertView = inflater.inflate(layoutResourceId, parent, false);
//        }
//
//        // object item based on the position
//        final AllSpecies objectItem = data.get(position);
//
//        TextView searchtext = (TextView) convertView.findViewById(R.id.searchitem);
//        searchtext.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));
//
//        searchtext.setText(objectItem.getTitle());
//
//        LinearLayout mainlayout = (LinearLayout) convertView.findViewById(R.id.mainlayout);
//
//        mainlayout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mContext, SpeciesDetailActivity.class);
//                intent.putExtra("fishid", objectItem.getId());
//
//                ((Activity) mContext).startActivityForResult(intent, 10);
//                ((Activity) mContext).overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);
//
//            }
//        });
//
//        return convertView;
//
//    }
}