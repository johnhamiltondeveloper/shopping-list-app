package uws.mad.assessment1;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;

        import java.util.ArrayList;

public class shopping extends Fragment {

    private DatabaseManager mydManager;
    private ArrayList<item> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pantry, container, false);

        mydManager = new DatabaseManager(rootView.getContext());

        mydManager.openReadable();
        data = mydManager.retrieveRows("shopping");

        ItemAdapter adapter = new ItemAdapter(getContext(),R.layout.product,data);

        ListView list = rootView.findViewById(R.id.PantryList);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                item ItemData = data.get(position);

                Intent intent = new Intent(view.getContext(), DataActivity.class);
                intent.putExtra("table", "shopping");
                intent.putExtra("mode", 1);
                intent.putExtra("id",ItemData.getId());
                intent.putExtra("showmove",0);
                startActivity(intent);

            }

        });

        return rootView;
    }
}