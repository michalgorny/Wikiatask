package pl.michalgorny.wikiatask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paging.listview.PagingBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.michalgorny.wikiatask.R;
import pl.michalgorny.wikiatask.services.responses.WikiItemResponse;

/**
 * Adapter populating list of wiki handling
 */
public class WikiPagingAdapter extends PagingBaseAdapter{

    private final List<WikiItemResponse> mWikiList;
    private LayoutInflater mInflater;

    public WikiPagingAdapter(Context context, List<WikiItemResponse> list) {
        super(list);
        mInflater = LayoutInflater.from(context);
        mWikiList = list;
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

        WikiItemResponse wiki = getItem(position);

        viewHolder.title.setText(wiki.getName());
        viewHolder.url.setText(wiki.getDomain());

        return convertView;
    }

    @Override
    public int getCount() {
        return mWikiList.size();
    }

    @Override
    public WikiItemResponse getItem(int position) {
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
