package uws.mad.assessment1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<item> {

    Context mCtx;
    int resoucre;
    List<item> ItemList;


    public ItemAdapter(Context mCtx, int resoucre, List<item> ItemList) {
        super(mCtx,resoucre,ItemList);

        this.mCtx = mCtx;
        this.resoucre = resoucre;
        this.ItemList = ItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(resoucre,null);

        TextView name = view.findViewById(R.id.product_name);
        ImageView productImage = view.findViewById(R.id.product_image);
        TextView productPrice = view.findViewById(R.id.product_price);
        TextView productLocation = view.findViewById(R.id.product_location);
        TextView productQwanity = view.findViewById(R.id.product_qwanity);

        item item = ItemList.get(position);

        name.setText(item.Name);
        productPrice.setText("$"+String.valueOf(item.getPrice()));
        productQwanity.setText(String.valueOf(item.getQuantity()));
        productLocation.setText(item.getLocation());

        try {
            byte[] encodebytes = Base64.decode(item.image, Base64.DEFAULT);
            Bitmap BitImage = BitmapFactory.decodeByteArray(encodebytes, 0, encodebytes.length);

            productImage.setImageBitmap(BitImage);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return view;
    }
}
