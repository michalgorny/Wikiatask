package pl.michalgorny.wikiatask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paging.listview.PagingBaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.michalgorny.wikiatask.R;
import pl.michalgorny.wikiatask.pojos.Wiki;
import timber.log.Timber;

/**
 * Adapter populating list of wiki handling
 */
public class WikiPagingAdapter extends PagingBaseAdapter{

    private final Context mContext;
    private final List<Wiki> mWikiList;
    private LayoutInflater mInflater;

    private final int mImageWidth, mImageHeight;

    public WikiPagingAdapter(Context context, List<Wiki> list) {
        super(list);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mWikiList = list;
        mImageWidth = mContext.getResources().getInteger(R.integer.list_image_width);
        mImageHeight = mContext.getResources().getInteger(R.integer.list_image_height);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WikiViewHolderItem viewHolder;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.wiki_list_item, parent, false);
            viewHolder = new WikiViewHolderItem(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (WikiViewHolderItem) convertView.getTag();
        }

        Wiki wiki = getItem(position);

        viewHolder.title.setText(wiki.getTitle());
        viewHolder.url.setText(wiki.getUrl());

        if (wiki.getUrlImage() != null && wiki.getUrlImage().isEmpty()){
            wiki.setUrlImage(null);
        }

        /**
         * TODO: In order to improve loading picture on list it would be be a good feature when api
         * will allow to get preview in form a small resized image. Currently we can request for
         * details with width and height parameters but it return a link to image which is cropped
         * and we lost some area of picture.
         */

        Timber.d("Downloading " + wiki.getUrlImage());
        Picasso.with(mContext)
                .load(wiki.getUrlImage())
                .centerCrop()
                .resize(mImageWidth, mImageHeight)
                .placeholder(R.drawable.photo_placeholder_loading)
                .error(R.drawable.error_image)
                .into(viewHolder.image);

        return convertView;
    }

    @Override
    public int getCount() {
        return mWikiList.size();
    }

    @Override
    public Wiki getItem(int position) {
        return mWikiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mWikiList.get(position).getId();
    }

    static class WikiViewHolderItem {

        @InjectView(R.id.list_item_title) TextView title;
        @InjectView(R.id.list_item_url) TextView url;
        @InjectView(R.id.list_item_image) ImageView image;

        public WikiViewHolderItem(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
