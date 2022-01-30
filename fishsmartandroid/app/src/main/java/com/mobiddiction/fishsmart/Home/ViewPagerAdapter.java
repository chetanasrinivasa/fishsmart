package com.mobiddiction.fishsmart.Home;

/**
 * Created by Archa on 28/06/2016.
 */
public class ViewPagerAdapter {//extends PagerAdapter {
//    // Declare Variables
//    HomeActivity context;
//    private TextView exclaimtext, description, sizelimittext, fishsubname, rulesapply;
//    private LinearLayout exclamlayout, mainlayout, linklayout;
//    private ImageView icon;
//    ArrayList<SpeciesModel> speciesList = new ArrayList<SpeciesModel>();
//    LayoutInflater inflater;
//
//    public ViewPagerAdapter(HomeActivity context, ArrayList<SpeciesModel> speciesList) {
//        this.context = context;
//        this.speciesList = speciesList;
//        inflater=LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getCount() {
//        return speciesList.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == ((View)object);
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//
//        ViewGroup currentView;
//     //   View v = LayoutInflater.from(context).inflate(R.layout.homespecies_item, null);
//        currentView = (ViewGroup) inflater.inflate(R.layout.homespecies_item,container,false);
//
//
//        icon = (ImageView) currentView.findViewById(R.id.fishimg);
//
//        exclaimtext = (TextView) currentView.findViewById(R.id.exclaimtext);
//        exclaimtext.setTypeface(Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf"));
//
//        exclamlayout = (LinearLayout) currentView.findViewById(R.id.exclamlayout);
//        mainlayout = (LinearLayout) currentView.findViewById(R.id.mainlayout);
//        linklayout = (LinearLayout) currentView.findViewById(R.id.linklayout);
//
//        description = (TextView) currentView.findViewById(R.id.fishname);
//        description.setTypeface(Typeface.createFromAsset(context.getAssets(), "bariol_bold.otf"));
//
//        sizelimittext = (TextView) currentView.findViewById(R.id.sizelimittext);
//        sizelimittext.setTypeface(Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf"));
//
//        fishsubname = (TextView) currentView.findViewById(R.id.fishsubname);
//        fishsubname.setTypeface(Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf"));
//
//        rulesapply = (TextView) currentView.findViewById(R.id.rulesapply);
//        rulesapply.setTypeface(Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf"));
//
//        final SpeciesModel item = speciesList.get(position);
//
//        Glide.with(context).load(item.getThumbnail()).diskCacheStrategy(DiskCacheStrategy.ALL).into(icon);
//
//        exclaimtext.setText(item.getGroupName());
//        description.setText(item.getTitle());
//        sizelimittext.setText("Size Limit: " + item.getSizeLimit());
//        fishsubname.setText(item.getDescription());
//
//        if (item.getBagLimitForCardView().equals(""))
//        {
//            rulesapply.setText("Rules Apply");
//            exclamlayout.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            if(!item.getGrouped())
//            {
//
//                exclamlayout.setVisibility(View.INVISIBLE);
//            }
//            else
//            {
//                exclamlayout.setVisibility(View.INVISIBLE);
//            }
//
//            if(item.getBagLimitForCardView().equals("null"))
//            {
//                rulesapply.setText("Bag Limit: N/A");
//            }
//            else
//            {
//                rulesapply.setText("Bag Limit: "+ item.getBagLimitForCardView());
//            }
//        }
//
//        if (item.getGroupName().equals(""))
//        {
//            exclamlayout.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
//            if(!item.getGrouped())
//                exclamlayout.setVisibility(View.INVISIBLE);
//            else
//                exclamlayout.setVisibility(View.VISIBLE);
//        }
//
//        linklayout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, SpeciesDetailActivity.class);
//                intent.putExtra("fishid", item.getId());
//                context.startActivityForResult(intent, 10);
//                context.overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);
//            }
//        });
//
//        container.addView(currentView);
//        return currentView;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        // Remove viewpager_item.xml from ViewPager
//        ((ViewPager) container).removeView((View) object);
//
//    }
}